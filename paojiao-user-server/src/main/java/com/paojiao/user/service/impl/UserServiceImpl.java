package com.paojiao.user.service.impl;

import com.fission.datasource.exception.RollbackSourceException;
import com.fission.next.common.error.FissionException;
import com.fission.utils.tool.ArrayUtils;
import com.fission.utils.tool.JsonUtil;
import com.fission.utils.tool.RdEncrypt;
import com.fission.utils.tool.StringUtil;
import com.paojiao.user.api.bean.UserInviteCodeBean;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.config.ApplicationConfig;
import com.paojiao.user.data.db.IUserReadDao;
import com.paojiao.user.data.db.IUserWriteDao;
import com.paojiao.user.data.db.entity.UserAttrInfoEntity;
import com.paojiao.user.data.db.entity.UserInfoEntity;
import com.paojiao.user.data.db.entity.UserInviteInfoEntity;
import com.paojiao.user.data.mongo.IMGUserLogDao;
import com.paojiao.user.data.mongo.entity.MGUserInviteUserInfoEntity;
import com.paojiao.user.service.IUserPicService;
import com.paojiao.user.service.IUserService;
import com.paojiao.user.service.bean.UserInfo;
import com.paojiao.user.service.bean.UserInviteInfo;
import com.paojiao.user.service.bean.UserPicInfo;
import com.paojiao.user.util.RedisUtil;
import com.paojiao.user.util.SurfingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    @Named("userReadDao")
    private IUserReadDao userReadDao;

    @Inject
    @Named("userWriteDao")
    private IUserWriteDao userWriteDao;

    @Inject
    private IMGUserLogDao mgUserLogDao;

    @Inject
    private ApplicationConfig applicationConfig;

    @Inject
    @Named(com.paojiao.user.util.ConstUtil.NameUtil.REDIS_USER)
    private RedisTemplate redis;

    @Inject
    private IUserPicService userPicService;

    private String getUserInviteLockKey(int userId) {
        return UserInviteInfoEntity.class.getCanonicalName() + "_lock_" + userId;
    }

    private String getUserInviteUserLockKey(int userId, int inviteUserId) {
        return UserInviteInfoEntity.class.getCanonicalName() + "_invite_lock_" + userId + "_" + inviteUserId;
    }

    @Override
    public int initUserInfo(short userType, int loginId) {
        try {
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setUserType(userType);
            if (loginId > 0) {
                userInfoEntity.setLoginId(loginId);
            }
            int surfing = SurfingUtil.getSurfing();
            userInfoEntity.setDefaultSurfing(surfing);
            userInfoEntity.setNickName(surfing + "");
            userInfoEntity.setSex(ConstUtil.Sex.UNDEFIND);
            userInfoEntity.setHeadPic("");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            userInfoEntity.setLastUpdateTime(now);
            userInfoEntity.setCreateTime(now);
            int userId = this.userWriteDao.addUserInfo(userInfoEntity);
            return userId;
        } catch (Exception e) {
            throw new RollbackSourceException(e);
        }
    }

    @Override
    public UserInfo getUserInfo(int userId) {
        try {
            UserInfoEntity userInfoEntity = this.userReadDao.getUserInfo(userId);
            return this.initUserInfo(userInfoEntity, false);
        } catch (Exception e) {
            throw new RollbackSourceException(e);
        }
    }

    @Override
    public UserInfo getSelfInfo(int userId) {
        try {
            UserInfoEntity userInfoEntity = this.userReadDao.getSelfUserInfo(userId);
            return this.initUserInfo(userInfoEntity, true);
        } catch (Exception e) {
            throw new RollbackSourceException(e);
        }
    }

    @Override
    public int resetUserIntegrity(int userId, int oldIntegrity, boolean toDb) {
        try {
            int integrity = this.initIntegrity(userId, null);
            if (toDb) {
                this.resetUserIntegrity(userId, oldIntegrity, integrity);
            }
            return integrity;
        } catch (Exception e) {
            throw new RollbackSourceException(e);
        }
    }

    private UserInfo initUserInfo(UserInfoEntity userInfoEntity, boolean resetIntegrity) throws SQLException {
        if (null == userInfoEntity) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        Date date = null == userInfoEntity.getBirthday() ? null : new Date(userInfoEntity.getBirthday().getTime());
        userInfo.setBirthday(date);
        userInfo.setLoginId(null == userInfoEntity.getLoginId() ? 0 : userInfoEntity.getLoginId());
        userInfo.setUserId(userInfoEntity.getUserId());
        userInfo.setUserType(userInfoEntity.getUserType());
        userInfo.setNickName(userInfoEntity.getNickName());
        userInfo.setSex(userInfoEntity.getSex());
        userInfo.setHeadPic(userInfoEntity.getHeadPic());
        userInfo.setBirthday(userInfoEntity.getBirthday());
        userInfo.setSurfing(userInfoEntity.getSurfing());
        if (userInfo.getSurfing() < 1) {
            userInfo.setSurfing(userInfoEntity.getDefaultSurfing());
        }
        userInfo.setIntegrity(userInfoEntity.getIntegrity());

        //用户属性设置
        Map<Short, String> userAttr = this.initUserAttr(userInfoEntity.getUserId());
        if (ArrayUtils.isNotEmpty(userAttr)) {
            if (StringUtil.isNotBlank(userAttr.get(ConstUtil.UserAttrId.GIS))) {
                String[] gis = userAttr.get(ConstUtil.UserAttrId.GIS).split(",");
                userInfo.setGisX(new BigDecimal(gis[0]));
                userInfo.setGisY(new BigDecimal(gis[1]));
            }

            userInfo.setUserDesc(userAttr.get(ConstUtil.UserAttrId.USER_DESC));
            userInfo.setProfessional(userAttr.get(ConstUtil.UserAttrId.PROFESSIONAL));
            if (StringUtil.isNotBlank(userAttr.get(ConstUtil.UserAttrId.LAST_ACTIV_TIME))) {
                userInfo.setLastActiveTime(new Date(Long.parseLong(userAttr.get(ConstUtil.UserAttrId.LAST_ACTIV_TIME))));
            }
        }

        if (resetIntegrity) {
            int integrity = this.initIntegrity(userInfoEntity.getUserId(), userInfoEntity, userAttr, null);
            this.resetUserIntegrity(userInfo.getUserId(), userInfo.getIntegrity(), integrity);
            userInfo.setIntegrity(integrity);
        }
        long time = null == userInfoEntity.getCreateTime() ? System.currentTimeMillis() : userInfoEntity.getCreateTime().getTime();
        userInfo.setCreateTime(new Date(time));
        return userInfo;
    }

    @Override
    public void updateUserAttr(int userId, Map<Short, Object> updateUserAttr) {
        try {
            if (null == updateUserAttr || updateUserAttr.isEmpty()) {
                return;
            }
            Map<String, Object> updateBaseAttr = new HashMap<>();
            List<UserAttrInfoEntity> userAttrInfoEntitys = new ArrayList<>();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            for (Entry<Short, Object> entry : updateUserAttr.entrySet()) {
                switch (entry.getKey()) {
                    //用户基本信息
                    case ConstUtil.UserAttrId.LOGIN_ID:
                        int loginId = (int) entry.getValue();
                        updateBaseAttr.put(UserInfoEntity.LOGIN_ID, loginId > 0 ? loginId : null);
                        break;
                    case ConstUtil.UserAttrId.SURFING:
                        updateBaseAttr.put(UserInfoEntity.SURFING, entry.getValue());
                        break;
                    case ConstUtil.UserAttrId.BIRTHDAY:
                        Date date = (Date) entry.getValue();
                        java.sql.Date birthday = null != date ? new java.sql.Date(date.getTime()) : null;
                        updateBaseAttr.put(UserInfoEntity.BIRTHDAY, birthday);
                        break;
                    case ConstUtil.UserAttrId.HEAD_PIC:
                        updateBaseAttr.put(UserInfoEntity.HEAD_PIC, entry.getValue());
                        break;
                    case ConstUtil.UserAttrId.NICK_NAME:
                        updateBaseAttr.put(UserInfoEntity.NICK_NAME, entry.getValue());
                        break;
                    case ConstUtil.UserAttrId.SEX:
                        updateBaseAttr.put(UserInfoEntity.SEX, entry.getValue());
                        break;
                    case ConstUtil.UserAttrId.USER_TYPE:
                        updateBaseAttr.put(UserInfoEntity.USER_TYPE, entry.getValue());
                        break;
                    //用户扩信息
                    case ConstUtil.UserAttrId.USER_DESC:
                    case ConstUtil.UserAttrId.PROFESSIONAL:
                    case ConstUtil.UserAttrId.CITY:
                    case ConstUtil.UserAttrId.GIS:
                    case ConstUtil.UserAttrId.LAST_ACTIV_TIME:
                        UserAttrInfoEntity userAttrInfoEntity = new UserAttrInfoEntity();
                        userAttrInfoEntity.setCreateTime(now);
                        userAttrInfoEntity.setLastUpdateTime(now);
                        userAttrInfoEntity.setUserId(userId);
                        userAttrInfoEntity.setUserAttrType(entry.getKey());
                        userAttrInfoEntity.setUserAttr(StringUtil.isBlank(entry.getValue().toString()) ? "" : entry.getValue().toString().trim());
                        userAttrInfoEntitys.add(userAttrInfoEntity);
                        break;
                    default:
                        break;
                }
            }
            if (ArrayUtils.isNotEmpty(updateBaseAttr)) {
                updateBaseAttr.put(UserInfoEntity.LAST_UPDATE_TIME, new Timestamp(System.currentTimeMillis()));
            }
            if (updateUserAttr.keySet().stream().filter(key -> key != ConstUtil.UserAttrId.GIS).count() > 1) {
                int integrity = this.initIntegrity(userId, updateUserAttr);
                updateBaseAttr.put(UserInfoEntity.INTEGRITY, integrity);
            }
            if (ArrayUtils.isNotEmpty(updateBaseAttr)) {
                this.userWriteDao.updateUserInfo(userId, updateBaseAttr);
            }
            if (ArrayUtils.isNotEmpty(userAttrInfoEntitys)) {
                this.userWriteDao.addUserAttrInfo(userId, userAttrInfoEntitys);
            }
        } catch (Exception e) {
            throw new RollbackSourceException(e);
        }
    }

    @Override
    public void clearUserCacheInfo(int userId) {
        try {
            this.userReadDao.getSelfUserInfo(userId);
        } catch (Exception e) {
            throw new FissionException(e);
        }
    }

    @Override
    public String addUserInviteInfo(int userId, int shareType) {
        try {
            String inviteCode = userId + "_" + RdEncrypt.MD5(UUID.randomUUID().toString());
            UserInviteInfoEntity userInviteInfoEntity = new UserInviteInfoEntity();
            userInviteInfoEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            userInviteInfoEntity.setUserId(userId);
            userInviteInfoEntity.setInviteCode(inviteCode);
            userInviteInfoEntity.setShareType(shareType);
            userInviteInfoEntity.setInviteNum(this.applicationConfig.getUserInviteNum());
            this.userWriteDao.addUserInviteInfo(userInviteInfoEntity);
            return inviteCode;
        } catch (Exception e) {
            throw new RollbackSourceException(e);
        }
    }

    @Override
    public UserInviteCodeBean getUserRegisterInviteInfo(int userId) {
        try {
            MGUserInviteUserInfoEntity userInviteUsernfoEntity = this.mgUserLogDao.getUserRegisterInviteInfo(userId);
            if (userInviteUsernfoEntity == null) {
                return null;
            }
            UserInviteCodeBean userInviteCodeBean = new UserInviteCodeBean();
            userInviteCodeBean.setCode(userInviteUsernfoEntity.getInviteCode());
            userInviteCodeBean.setInviteUserId(userInviteUsernfoEntity.getUserId());
            userInviteCodeBean.setShareType(userInviteUsernfoEntity.getShareType());
            return userInviteCodeBean;
        } catch (Exception e) {
            throw new FissionException(e);
        }
    }

    @Override
    public void updateUserInviteInfo(int userId, short inviteType, String inviteCode) {
        try {
            UserInviteInfoEntity userInviteInfoEntity = this.userReadDao.getInviteUser(inviteCode);
            if (null == userInviteInfoEntity) {
                return;
            }
            RedisUtil.redisLock(this.redis, this.getUserInviteUserLockKey(userInviteInfoEntity.getUserId(), userId), (Void v) -> {
                try {
                    boolean invite = this.userWriteDao.subUserInviteNum(inviteCode);
                    Date now = new Date(System.currentTimeMillis());
                    MGUserInviteUserInfoEntity mgUserInviteUserInfoEntity = new MGUserInviteUserInfoEntity();
                    mgUserInviteUserInfoEntity.setId(userInviteInfoEntity.getUserId() + "_" + userId);
                    mgUserInviteUserInfoEntity.setInviteState(ConstUtil.InviteState.INIT);
                    mgUserInviteUserInfoEntity.setInvite(invite);
                    mgUserInviteUserInfoEntity.setInviteCode(userInviteInfoEntity.getInviteCode());
                    mgUserInviteUserInfoEntity.setInviteUserId(userId);
                    mgUserInviteUserInfoEntity.setShareType(userInviteInfoEntity.getShareType());
                    mgUserInviteUserInfoEntity.setUserId(userInviteInfoEntity.getUserId());
                    mgUserInviteUserInfoEntity.setUpdateTime(now);
                    mgUserInviteUserInfoEntity.setCreateTime(now);
                    this.mgUserLogDao.addUserInviteUserInfo(mgUserInviteUserInfoEntity);
                } catch (Exception e) {
                    throw new RollbackSourceException(e);
                }
            });
        } catch (RollbackSourceException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackSourceException(e);
        }
    }

    @Override
    public List<UserInviteInfo> listUserInviteInfo(int userId) {
        List<UserInviteInfo> list = new ArrayList<>();
        RedisUtil.redisLock(this.redis, this.getUserInviteLockKey(userId), (Void v) -> {
            try {
                List<MGUserInviteUserInfoEntity> userInviteLogInfoEntitys = this.mgUserLogDao.listUserInviteUserInfo(userId, ConstUtil.InviteState.INIT, true);
                if (ArrayUtils.isNullOrEmpty(userInviteLogInfoEntitys)) {
                    return;
                }
                userInviteLogInfoEntitys.forEach((MGUserInviteUserInfoEntity userInviteUserInfoEntity) -> {
                    if (null != userInviteUserInfoEntity) {
                        try {
                            if (this.mgUserLogDao.manageUserInviteState(userInviteUserInfoEntity.getId())) {
                                UserInviteInfo userInviteInfo = new UserInviteInfo();
                                long time = Optional.ofNullable(userInviteUserInfoEntity.getCreateTime()).orElse(new Date()).getTime();
                                userInviteInfo.setCreateTime(new Date(time));
                                userInviteInfo.setInviteCode(userInviteUserInfoEntity.getInviteCode());
                                userInviteInfo.setInviterState(userInviteUserInfoEntity.getInviteState());
                                userInviteInfo.setInviteType(userInviteUserInfoEntity.getInviteType());
                                userInviteInfo.setInviteUserId(userInviteUserInfoEntity.getInviteUserId());
                                userInviteInfo.setUserId(userInviteUserInfoEntity.getUserId());
                                userInviteInfo.setShareType(userInviteUserInfoEntity.getShareType());
                                userInviteInfo.setUserInviteId(userInviteUserInfoEntity.getId());
                                userInviteInfo.setInvite(userInviteUserInfoEntity.isInvite());
                                list.add(userInviteInfo);
                            }
                        } catch (Exception e) {
                            String message = String.format("listUserInviteInfo manageUserInviteState error.param(userInviteUserInfoEntity:%s)", JsonUtil.objToJsonString(userInviteUserInfoEntity));
                            UserServiceImpl.LOGGER.error(message, e);
                        }
                    }
                });
            } catch (Exception e) {
                throw new RollbackSourceException(e);
            }
        });
        return list;
    }

    /**
     * 重置完善度
     *
     * @param userId
     * @param oldIntegrity
     * @param integrity
     */
    private void resetUserIntegrity(int userId, int oldIntegrity, int integrity) {
        try {
            if (oldIntegrity != integrity) {
                Map<String, Object> updateBaseAttr = new HashMap<>();
                updateBaseAttr.put(UserInfoEntity.INTEGRITY, integrity);
                updateBaseAttr.put(UserInfoEntity.LAST_UPDATE_TIME, new Timestamp(System.currentTimeMillis()));
                this.userWriteDao.updateUserInfo(userId, updateBaseAttr);
            }
        } catch (Exception e) {
            throw new RollbackSourceException(e);
        }
    }

    /**
     * 计算完善度
     *
     * @param userId
     * @param updateUserAttr
     * @return
     */
    private int initIntegrity(int userId, Map<Short, Object> updateUserAttr) {
        try {
            UserInfoEntity userInfoEntity = this.userReadDao.getUserInfo(userId);
            if (userInfoEntity != null) {
                return this.initIntegrity(userId, userInfoEntity, null, updateUserAttr);
            }
            return 0;
        } catch (Exception e) {
            String message = String.format("initIntegrity  error.param(userId:%s)", userId);
            UserServiceImpl.LOGGER.error(message, e);
            return 0;
        }
    }

    private int initIntegrity(int userId, UserInfoEntity userInfoEntity, Map<Short, String> userAttr, Map<Short, Object> updateUserAttr) {
        try {
            Set<Short> userAttrIdSet = new HashSet<>();
            if (null == userInfoEntity) {
                userInfoEntity = this.userReadDao.getUserInfo(userId);
            }

            if (userInfoEntity != null) {
                if (StringUtil.isNotBlank(userInfoEntity.getNickName())) {
                    userAttrIdSet.add(ConstUtil.UserAttrId.NICK_NAME);
                }
                if (userInfoEntity.getSex() != ConstUtil.Sex.UNDEFIND) {
                    userAttrIdSet.add(ConstUtil.UserAttrId.SEX);
                }
                if (null != userInfoEntity.getBirthday()) {
                    userAttrIdSet.add(ConstUtil.UserAttrId.BIRTHDAY);
                }
                if (StringUtil.isNotBlank(userInfoEntity.getHeadPic())) {
                    userAttrIdSet.add(ConstUtil.UserAttrId.HEAD_PIC);
                }
            }

            if (null == userAttr) {
                userAttr = this.initUserAttr(userId);
            }
            if (ArrayUtils.isNotEmpty(userAttr)) {
                userAttrIdSet.addAll(userAttr.keySet());
            }

            if (ArrayUtils.isNotEmpty(updateUserAttr)) {
                userAttrIdSet.addAll(updateUserAttr.keySet());
            }

            AtomicInteger integrity = new AtomicInteger(0);
            userAttrIdSet.stream().forEach((Short userAttrId) -> {
                integrity.addAndGet(this.applicationConfig.getUserAttrIntegrity(userAttrId));
            });

            List<UserPicInfo> userPicInfoList = this.userPicService.listUserAllPicInfo(userId, true);
            //图片数
            if (ArrayUtils.isNotEmpty(userPicInfoList) && userPicInfoList.size() > 1) {
                integrity.addAndGet((userPicInfoList.size() - 1) * this.applicationConfig.getUserAttrIntegrity(ConstUtil.UserAttrId.INDEX_PIC));
            }
            return integrity.get();
        } catch (Exception e) {
            String message = String.format("initIntegrity  error.param(userId:%s)", userId);
            UserServiceImpl.LOGGER.error(message, e);
            return 0;
        }
    }

    private Map<Short, String> initUserAttr(int userId) throws SQLException {
        List<UserAttrInfoEntity> userAttrInfoEntityList = this.userReadDao.listUserAttrInfo(userId);
        if (ArrayUtils.isNullOrEmpty(userAttrInfoEntityList)) {
            return null;
        }
        Map<Short, String> userAttr = new HashMap<>();
        userAttrInfoEntityList.stream().filter((UserAttrInfoEntity userAttrInfoEntity) -> null != userAttrInfoEntity).forEach((UserAttrInfoEntity userAttrInfoEntity) -> {
            if (StringUtil.isNotBlank(userAttrInfoEntity.getUserAttr())) {
                userAttr.put(userAttrInfoEntity.getUserAttrType(), userAttrInfoEntity.getUserAttr());
            }
        });
        return userAttr;
    }
}