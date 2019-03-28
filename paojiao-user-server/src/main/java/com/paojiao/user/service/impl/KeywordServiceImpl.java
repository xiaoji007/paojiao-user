package com.paojiao.user.service.impl;

import com.fission.datasource.exception.RollbackSourceException;
import com.fission.utils.tool.ArrayUtils;
import com.paojiao.user.data.db.IKeywordReadDao;
import com.paojiao.user.data.db.entity.NickNameAsciiInfoEntity;
import com.paojiao.user.data.db.entity.NickNameKeywordInfoEntity;
import com.paojiao.user.service.IKeywordService;
import com.paojiao.user.service.bean.NickNameAsciiInfo;
import com.paojiao.user.service.bean.NickNameKeyworldInfo;
import com.paojiao.user.util.ConstUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class KeywordServiceImpl implements IKeywordService {

    @Inject
    private IKeywordReadDao keyworldReadDao;

    @Inject
    @Named(ConstUtil.NameUtil.REDIS_USER)
    private RedisTemplate redis;

    public static final String NICK_KEY = "user.nick.keyworld";
    public static final String NICK_ASCII_KEY = "user.nick.ascii.keyworld";

    @Override
    public List<NickNameKeyworldInfo> listAllNickNameKeyworldInfo() {
        return (List<NickNameKeyworldInfo>) this.redis.opsForList().range(KeywordServiceImpl.NICK_KEY, 0, -1);
    }

    @Override
    public void refreshNickNameKeyworldInfo() {
        try {
            List<NickNameKeywordInfoEntity> nickNameKeywordInfoEntities = this.keyworldReadDao.listAllNickNameKeywordInfo();
            if (ArrayUtils.isNullOrEmpty(nickNameKeywordInfoEntities)) {
                this.redis.delete(KeywordServiceImpl.NICK_KEY);
                return;
            }
            List<NickNameKeyworldInfo> list = new ArrayList<>();
            nickNameKeywordInfoEntities.forEach((NickNameKeywordInfoEntity nickNameKeywordInfoEntity) -> {
                if (null == nickNameKeywordInfoEntity) {
                    return;
                }
                NickNameKeyworldInfo nickNameKeyworldInfo = new NickNameKeyworldInfo();
                nickNameKeyworldInfo.setKeyworldId(nickNameKeywordInfoEntity.getKeywordId());
                nickNameKeyworldInfo.setKeyworld(nickNameKeywordInfoEntity.getKeyword().trim());
                nickNameKeyworldInfo.setRuleType(nickNameKeywordInfoEntity.getRuleType());
                long createTime = null == nickNameKeywordInfoEntity.getCreateTime() ? System.currentTimeMillis() : nickNameKeywordInfoEntity.getCreateTime().getTime();
                nickNameKeyworldInfo.setCreateTime(new Date(createTime));
                list.add(nickNameKeyworldInfo);
            });
            this.redis.delete(KeywordServiceImpl.NICK_KEY);
            this.redis.opsForList().leftPushAll(KeywordServiceImpl.NICK_KEY, list);
        } catch (Exception e) {
            throw new RollbackSourceException("listAllNickNameKeyworldInfo error.param", e);
        }
    }

    @Override
    public List<NickNameAsciiInfo> listAllNickNameAsciiInfo() {
        return (List<NickNameAsciiInfo>) this.redis.opsForList().range(KeywordServiceImpl.NICK_ASCII_KEY, 0, -1);
    }

    @Override
    public void refreshNickNameAsciiInfo() {
        try {
            List<NickNameAsciiInfoEntity> nickNameAsciiInfoEntitys = this.keyworldReadDao.listAllNickNameAsciiInfo();
            if (ArrayUtils.isNullOrEmpty(nickNameAsciiInfoEntitys)) {
                this.redis.delete(KeywordServiceImpl.NICK_KEY);
                return;
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
            this.redis.delete(KeywordServiceImpl.NICK_KEY);
            this.redis.opsForList().leftPushAll(KeywordServiceImpl.NICK_KEY, list);
        } catch (Exception e) {
            throw new RollbackSourceException("listAllNickNameKeyworldInfo error.param", e);
        }
    }
}
