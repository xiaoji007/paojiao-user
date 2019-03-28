package com.paojiao.user.data.mongo.impl;

import com.fission.utils.tool.ArrayUtils;
import com.mongodb.WriteResult;
import com.paojiao.user.data.mongo.IMGUserLogDao;
import com.paojiao.user.data.mongo.entity.MGUserInviteUserInfoEntity;
import com.paojiao.user.data.mongo.entity.MGUserPicLogInfoEntity;
import com.paojiao.user.util.ConstUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;

@Repository
public class MGUserLogDaoImpl implements IMGUserLogDao {

    @Inject
    @Named(ConstUtil.NameUtil.MONGO_USER)
    private MongoTemplate mongoTemplate;

    @Override
    public void addUserInviteUserInfo(MGUserInviteUserInfoEntity userInviteUserInfoEntity) {
        if (null == userInviteUserInfoEntity) {
            return;
        }
        mongoTemplate.save(userInviteUserInfoEntity);
    }

    @Override
    public List<MGUserInviteUserInfoEntity> listUserInviteUserInfo(int userId, short inviteState, boolean isInvite) {
        Query query = new Query();
        query.addCriteria(Criteria.where(MGUserInviteUserInfoEntity.USER_ID).is(userId)
                .and(MGUserInviteUserInfoEntity.INVITE).is(isInvite)
                .and(MGUserInviteUserInfoEntity.INVITE_STATE).is(inviteState));
        Sort sort = new Sort(Sort.Direction.ASC, MGUserInviteUserInfoEntity.CREATE_TIME);
        query.with(sort);
        return this.mongoTemplate.find(query, MGUserInviteUserInfoEntity.class);
    }


    @Override
    public MGUserInviteUserInfoEntity getUserRegisterInviteInfo(int userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(MGUserInviteUserInfoEntity.INVITE_USER_ID).is(userId)
                .and(MGUserInviteUserInfoEntity.INVITE).is(true));
        Sort sort = new Sort(Sort.Direction.ASC, MGUserInviteUserInfoEntity.CREATE_TIME);
        query.with(sort);
        return this.mongoTemplate.findOne(query, MGUserInviteUserInfoEntity.class);
    }

    @Override
    public boolean manageUserInviteState(String id) {
        Query query = new Query(Criteria.where(MGUserInviteUserInfoEntity.ID).is(id)
                .and(MGUserInviteUserInfoEntity.INVITE_STATE).is(com.paojiao.user.api.util.ConstUtil.InviteState.INIT)
                .and(MGUserInviteUserInfoEntity.INVITE).is(true));

        Update update = new Update();
        update.set(MGUserInviteUserInfoEntity.UPDATE_TIME, new Date());
        update.set(MGUserInviteUserInfoEntity.INVITE_STATE, com.paojiao.user.api.util.ConstUtil.InviteState.MANAGE);
        WriteResult writeResult = mongoTemplate.updateFirst(query, update, MGUserInviteUserInfoEntity.class);
        return writeResult.getN() > 0;
    }

    @Override
    public void addUserPicLogInfo(List<MGUserPicLogInfoEntity> MGUserPicLogInfoEntityList) {
        if (ArrayUtils.isNullOrEmpty(MGUserPicLogInfoEntityList)) {
            return;
        }
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, MGUserPicLogInfoEntity.TABLE_NAME);
        bulkOperations.insert(MGUserPicLogInfoEntityList);
        bulkOperations.execute();
    }
}
