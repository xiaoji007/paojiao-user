package com.paojiao.user.service.impl;

import com.fission.datasource.exception.RollbackSourceException;
import com.fission.utils.tool.ArrayUtils;
import com.paojiao.user.data.db.IKeyworldReadDao;
import com.paojiao.user.data.db.entity.NickNameAsciiInfoEntity;
import com.paojiao.user.data.db.entity.NickNameKeyworldInfoEntity;
import com.paojiao.user.service.IKeyworldService;
import com.paojiao.user.service.bean.NickNameAsciiInfo;
import com.paojiao.user.service.bean.NickNameKeyworldInfo;
import com.paojiao.user.util.RedisKeyUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class KeyworldServiceImpl implements IKeyworldService {

    @Inject
    private IKeyworldReadDao keyworldReadDao;

    @Cacheable(key = RedisKeyUtil.NICK, value = RedisKeyUtil.CACHE_NAME)
    @Override
    public List<NickNameKeyworldInfo> listAllNickNameKeyworldInfo() {
        try {
            List<NickNameKeyworldInfoEntity> nickNameKeyworldInfoEntitys = this.keyworldReadDao.listAllNickNameKeywordInfo();
            if (ArrayUtils.isNullOrEmpty(nickNameKeyworldInfoEntitys)) {
                return null;
            }
            List<NickNameKeyworldInfo> list = new ArrayList<>();
            nickNameKeyworldInfoEntitys.forEach((NickNameKeyworldInfoEntity nickNameKeyworldInfoEntity) -> {
                if (null == nickNameKeyworldInfoEntity) {
                    return;
                }
                NickNameKeyworldInfo nickNameKeyworldInfo = new NickNameKeyworldInfo();
                nickNameKeyworldInfo.setKeyworldId(nickNameKeyworldInfoEntity.getKeyworldId());
                nickNameKeyworldInfo.setKeyworld(nickNameKeyworldInfoEntity.getKeyworld().trim());
                nickNameKeyworldInfo.setRuleType(nickNameKeyworldInfoEntity.getRuleType());
                long createTime = null == nickNameKeyworldInfoEntity.getCreateTime() ? System.currentTimeMillis() : nickNameKeyworldInfoEntity.getCreateTime().getTime();
                nickNameKeyworldInfo.setCreateTime(new Date(createTime));
                list.add(nickNameKeyworldInfo);
            });
            return list;
        } catch (Exception e) {
            throw new RollbackSourceException("listAllNickNameKeyworldInfo error.param", e);
        }
    }

    @CacheEvict(key = RedisKeyUtil.NICK, value = RedisKeyUtil.CACHE_NAME)
    @Override
    public void refreshNickNameKeyworldInfo() {

    }

    @Cacheable(key = RedisKeyUtil.NICK_ASCII, value = RedisKeyUtil.CACHE_NAME)
    @Override
    public List<NickNameAsciiInfo> listAllNickNameAsciiInfo() {
        try {
            List<NickNameAsciiInfoEntity> nickNameAsciiInfoEntitys = this.keyworldReadDao.listAllNickNameAsciiInfo();
            if (ArrayUtils.isNullOrEmpty(nickNameAsciiInfoEntitys)) {
                return null;
            }
            List<NickNameAsciiInfo> list = new ArrayList<>();
            nickNameAsciiInfoEntitys.forEach((NickNameAsciiInfoEntity nickNameAsciiInfoEntity) -> {
                if (null == nickNameAsciiInfoEntity) {
                    return;
                }
                NickNameAsciiInfo nickNameAsciiInfo = new NickNameAsciiInfo();
                nickNameAsciiInfo.setAsciiId(nickNameAsciiInfoEntity.getAsciiId());
                nickNameAsciiInfo.setAsciiType(nickNameAsciiInfoEntity.getAsciiType());
                nickNameAsciiInfo.setStartAscii(nickNameAsciiInfoEntity.getStartAscii());
                nickNameAsciiInfo.setEndAscii(nickNameAsciiInfoEntity.getEndAscii());
                long createTime = null == nickNameAsciiInfoEntity.getCreateTime() ? System.currentTimeMillis() : nickNameAsciiInfoEntity.getCreateTime().getTime();
                nickNameAsciiInfo.setCreateTime(new Date(createTime));
                list.add(nickNameAsciiInfo);
            });
            return list;
        } catch (Exception e) {
            throw new RollbackSourceException("listAllNickNameKeyworldInfo error.param", e);
        }
    }

    @CacheEvict(key = RedisKeyUtil.NICK_ASCII, value = RedisKeyUtil.CACHE_NAME)
    @Override
    public void refreshNickNameAsciiInfo() {

    }
}
