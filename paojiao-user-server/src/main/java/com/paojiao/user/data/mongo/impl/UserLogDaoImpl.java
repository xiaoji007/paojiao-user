package com.paojiao.user.data.mongo.impl;

import com.fission.utils.tool.ArrayUtils;
import com.mongodb.WriteResult;
import com.paojiao.user.data.mongo.IUserLogDao;
import com.paojiao.user.data.mongo.entity.UserInviteUserInfoEntity;
import com.paojiao.user.data.mongo.entity.UserPicLogInfoEntity;
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
public class UserLogDaoImpl implements IUserLogDao {

    @Inject
    @Named(ConstUtil.NameUtil.MONGO_USER)
    private MongoTemplate mongoTemplate;

    @Override
    public void addUserInviteUserInfo(UserInviteUserInfoEntity userInviteUserInfoEntity) {
        if (null == userInviteUserInfoEntity) {
            return;
        }
        mongoTemplate.save(userInviteUserInfoEntity);
    }

    @Override
    public List<UserInviteUserInfoEntity> listUserInviteUserInfo(int userId, short inviteState, boolean isInvite) {
        Query query = new Query();
        query.addCriteria(Criteria.where(UserInviteUserInfoEntity.USER_ID).is(userId)
                .and(UserInviteUserInfoEntity.INVITE).is(isInvite)
                .and(UserInviteUserInfoEntity.INVITE_STATE).is(inviteState));
        Sort sort = new Sort(Sort.Direction.ASC, UserInviteUserInfoEntity.CREATE_TIME);
        query.with(sort);
        return this.mongoTemplate.find(query, UserInviteUserInfoEntity.class);
    }


    @Override
    public UserInviteUserInfoEntity getUserRegisterInviteInfo(int userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(UserInviteUserInfoEntity.INVITE_USER_ID).is(userId)
                .and(UserInviteUserInfoEntity.INVITE).is(true));
        Sort sort = new Sort(Sort.Direction.ASC, UserInviteUserInfoEntity.CREATE_TIME);
        query.with(sort);
        return this.mongoTemplate.findOne(query, UserInviteUserInfoEntity.class);
    }

    @Override
    public boolean manageUserInviteState(String id) {
        Query query = new Query(Criteria.where(UserInviteUserInfoEntity.ID).is(id)
                .and(UserInviteUserInfoEntity.INVITE_STATE).is(com.paojiao.user.api.util.ConstUtil.InviteState.INIT)
                .and(UserInviteUserInfoEntity.INVITE).is(true));

        Update update = new Update();
        update.set(UserInviteUserInfoEntity.UPDATE_TIME, new Date());
        update.set(UserInviteUserInfoEntity.INVITE_STATE, com.paojiao.user.api.util.ConstUtil.InviteState.MANAGE);
        WriteResult writeResult = mongoTemplate.updateFirst(query, update, UserInviteUserInfoEntity.class);
        return writeResult.getN() > 0;
    }

    @Override
    public void addUserPicLogInfo(List<UserPicLogInfoEntity> userPicLogInfoEntityList) {
        if (ArrayUtils.isNullOrEmpty(userPicLogInfoEntityList)) {
            return;
        }
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, UserPicLogInfoEntity.TABLE_NAME);
        bulkOperations.insert(userPicLogInfoEntityList);
        bulkOperations.execute();
    }
}
