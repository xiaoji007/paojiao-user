package com.paojiao.user.service.impl;

import com.fission.datasource.exception.RollbackSourceException;
import com.fission.next.common.error.FissionException;
import com.fission.utils.tool.ArrayUtils;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.config.ApplicationConfig;
import com.paojiao.user.data.db.IUserPicReadDao;
import com.paojiao.user.data.db.IUserPicWriteDao;
import com.paojiao.user.data.db.entity.UserPicInfoEntity;
import com.paojiao.user.service.IUserPicService;
import com.paojiao.user.service.bean.UserPicInfo;
import com.paojiao.user.util.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
public class UserPicServiceImpl implements IUserPicService {

    @Inject
    private ApplicationConfig applicationConfig;

    @Inject
    @Named("userPicReadDao")
    private IUserPicReadDao userPicReadDao;

    @Inject
    @Named("userPicWriteDao")
    private IUserPicWriteDao userPicWriteDao;

    @Inject
    @Named(com.paojiao.user.util.ConstUtil.NameUtil.REDIS_USER)
    private RedisTemplate redis;

    @Override
    public void addUserPicInfo(int userId, int index, String pic) {
        try {
            Callable<Integer> callable = () -> {
                UserPicInfoEntity userPicInfoEntity = new UserPicInfoEntity();
                userPicInfoEntity.setUserId(userId);
                userPicInfoEntity.setIndex(index);
                userPicInfoEntity.setUserPicUrl(pic);
                userPicInfoEntity.setVerifyState(this.applicationConfig.isUserPicVerify() ? ConstUtil.UserPicVerifyState.INIT : ConstUtil.UserPicVerifyState.PASS);
                userPicInfoEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                userPicInfoEntity.setCreateTime(userPicInfoEntity.getUpdateTime());
                return this.userPicWriteDao.addUserPicInfo(userPicInfoEntity);
            };
            this.lockUserIndexPic(userId, callable);
        } catch (Exception e) {
            throw new RollbackSourceException(e);
        }
    }


    @Override
    public void moveUserPic(int userId, int index, int toIndex) {
        try {
            if (index == toIndex && index > 0) {
                return;
            }

            Callable<Void> callable = () -> {
                List<UserPicInfoEntity> list = this.userPicReadDao.listAllUserPicInfo(userId);
                if (null == list || list.isEmpty()) {
                    return null;
                }
                list.sort((UserPicInfoEntity o1, UserPicInfoEntity o2) -> o1.getIndex() - o2.getIndex());
                int size = list.size();
                int removeSite = 0;

                //要更新的图片位置信息
                List<UserPicInfoEntity> updateUserPicInfoEntitys = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    UserPicInfoEntity userPicInfoEntity = list.get(i);
                    if (null == userPicInfoEntity) {
                        continue;
                    }
                    if (toIndex < 0) { // 图片重新排序
                        removeSite++;
                        userPicInfoEntity.setIndex(removeSite);
                        updateUserPicInfoEntitys.add(userPicInfoEntity);
                    } else if (toIndex == 0) { //删除图片
                        if (userPicInfoEntity.getIndex() >= index) {
                            removeSite = userPicInfoEntity.getIndex() - 1;
                            if (userPicInfoEntity.getIndex() > index) {
                                userPicInfoEntity.setIndex(userPicInfoEntity.getIndex() - 1);
                                updateUserPicInfoEntitys.add(userPicInfoEntity);
                            }
                        }
                    } else { //图片移动
                        if (userPicInfoEntity.getIndex() == index) {
                            userPicInfoEntity.setIndex(toIndex);
                            updateUserPicInfoEntitys.add(userPicInfoEntity);
                            continue;
                        }
                        if (index < toIndex) {
                            if (userPicInfoEntity.getIndex() > index && userPicInfoEntity.getIndex() <= toIndex) {
                                userPicInfoEntity.setIndex(userPicInfoEntity.getIndex() - 1);
                                updateUserPicInfoEntitys.add(userPicInfoEntity);
                            }
                        } else {
                            if (userPicInfoEntity.getIndex() >= toIndex && userPicInfoEntity.getIndex() < index) {
                                userPicInfoEntity.setIndex(userPicInfoEntity.getIndex() + 1);
                                updateUserPicInfoEntitys.add(userPicInfoEntity);
                            }
                        }
                    }
                }

                //删除图片
                if (removeSite > 0) {
                    this.userPicWriteDao.removeUserPicInfo(userId, removeSite);
                }

                if (!updateUserPicInfoEntitys.isEmpty()) {
                    //更新所有要更新的图片
                    this.userPicWriteDao.updateUserPicInfo(updateUserPicInfoEntitys);
                }
                return null;
            };
            this.lockUserIndexPic(userId, callable);
        } catch (Exception e) {
            throw new RollbackSourceException(e);
        }
    }

    @Override
    public void subUserIndexPicInfo(int userId, List<Integer> userPicIds) {
        try {
            if (ArrayUtils.isNullOrEmpty(userPicIds)) {
                return;
            }
            Callable<Void> callable = () -> {
                this.userPicWriteDao.removeUserPicInfo(userId, userPicIds);
                return null;
            };
            this.lockUserIndexPic(userId, callable);
        } catch (Exception e) {
            throw new RollbackSourceException(e);
        }
    }

    @Override
    public List<UserPicInfo> listUserAllPicInfo(int userId, boolean reset) {
        try {
            List<UserPicInfoEntity> userPicInfoEntityList = null;
            if (reset) {
                userPicInfoEntityList = this.userPicReadDao.listResetAllUserPicInfo(userId);
            } else {
                userPicInfoEntityList = this.userPicReadDao.listAllUserPicInfo(userId);
            }
            if (ArrayUtils.isNullOrEmpty(userPicInfoEntityList)) {
                return null;
            }
            List<UserPicInfo> userPicInfoList = new ArrayList<>();
            userPicInfoEntityList.forEach((UserPicInfoEntity userPicInfoEntity) -> {
                if (null == userPicInfoEntity) {
                    return;
                }
                UserPicInfo userPicInfo = new UserPicInfo();
                userPicInfo.setUserPicId(userPicInfoEntity.getUserPicId());
                userPicInfo.setUserId(userPicInfoEntity.getUserId());
                userPicInfo.setIndex(userPicInfoEntity.getIndex());
                userPicInfo.setOldPicUrl(userPicInfoEntity.getOldUserPicUrl());
                userPicInfo.setVerifyState(userPicInfoEntity.getVerifyState());
                long time = null == userPicInfoEntity.getCreateTime() ? System.currentTimeMillis() : userPicInfoEntity.getCreateTime().getTime();
                userPicInfo.setCreateTime(new Date(time));
                userPicInfoList.add(userPicInfo);
            });
            userPicInfoList.sort((UserPicInfo o1, UserPicInfo o2) -> o1.getIndex() - o2.getIndex());
            return userPicInfoList;
        } catch (Exception e) {
            throw new FissionException(e);
        }
    }

    @Override
    public UserPicInfo getUserHeadPicInfo(int userId) {
        List<UserPicInfo> userPicInfoList = this.listUserAllPicInfo(userId, false);
        if (ArrayUtils.isNullOrEmpty(userPicInfoList)) {
            return null;
        }
        userPicInfoList = userPicInfoList.stream().filter((UserPicInfo userPicInfo) -> null != userPicInfo && 1 == userPicInfo.getIndex()).collect(Collectors.toList());
        return ArrayUtils.isNullOrEmpty(userPicInfoList) ? null : userPicInfoList.get(0);
    }

    private <T> T lockUserIndexPic(int userId, Callable<T> callable) {
        String lockKey = UserPicInfoEntity.class.getName() + "_verify_" + userId;
        return RedisUtil.redisValueLocks(this.redis, lockKey, callable);
    }

}
