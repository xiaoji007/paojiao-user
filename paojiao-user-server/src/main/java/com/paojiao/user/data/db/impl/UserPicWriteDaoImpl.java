package com.paojiao.user.data.db.impl;


import com.fission.datasource.sql.FissionJdbcTemplate;
import com.fission.datasource.sql.bean.SqlBean;
import com.fission.datasource.sql.util.DBSqlBuildUtil;
import com.fission.datasource.sql.util.DBSqlUtil;
import com.fission.next.common.error.FissionException;
import com.fission.utils.tool.ArrayUtils;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.data.db.IUserPicWriteDao;
import com.paojiao.user.data.db.entity.UserPicInfoEntity;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userPicWriteDao")
public class UserPicWriteDaoImpl implements IUserPicWriteDao {

    private static final String PASS_USER_PIC = "UPDATE " + UserPicInfoEntity.TABLE_NAME + " SET " + UserPicInfoEntity.OLD_USER_PIC_URL + "=" + UserPicInfoEntity.USER_PIC_URL + ","
            + UserPicInfoEntity.VERIFY_STATE + " = " + ConstUtil.UserPicVerifyState.PASS
            + " WHERE " + UserPicInfoEntity.USER_ID + " = ? ," + UserPicInfoEntity.USER_PIC_ID + "= ?";

    @Inject
    @Named(com.paojiao.user.util.ConstUtil.NameUtil.DB_USER)
    private FissionJdbcTemplate jdbcTemplate;

    @Override
    public int addUserPicInfo(UserPicInfoEntity userPicInfoEntity) throws SQLException {
        if (null == userPicInfoEntity) {
            throw new FissionException("addUserLikePicInfo userPicInfoEntity is null");
        }
        List<String> replaceProperties = new ArrayList<>();
        if (userPicInfoEntity.getVerifyState() == ConstUtil.UserPicVerifyState.PASS) {
            userPicInfoEntity.setOldUserPicUrl(userPicInfoEntity.getUserPicUrl());
            replaceProperties.add(UserPicInfoEntity.OLD_USER_PIC_URL);
        } else {
            userPicInfoEntity.setUserPicUrl("");
        }
        replaceProperties.add(UserPicInfoEntity.VERIFY_STATE);
        replaceProperties.add(UserPicInfoEntity.USER_PIC_URL);
        replaceProperties.add(UserPicInfoEntity.UPDATE_TIME);
        SqlBean sqlBean = DBSqlBuildUtil.addEntity(userPicInfoEntity, replaceProperties);
        return this.jdbcTemplate.insertDataAutoKey(sqlBean.getSql(), sqlBean.getParam());
    }

    @Override
    public void updateUserPicInfo(List<UserPicInfoEntity> userPicInfoEntitys) throws SQLException {
        if (ArrayUtils.isNullOrEmpty(userPicInfoEntitys)) {
            throw new FissionException("updateUserPicInfo userPicInfoEntitys is null");
        }
        List<Map<String, Map<String, Object>>> wherePropertiesList = new ArrayList<>();
        List<Map<String, Object>> updatePropertiesList = new ArrayList<>();
        userPicInfoEntitys.forEach((UserPicInfoEntity userPicInfoEntity) -> {
            if (null == userPicInfoEntity) {
                return;
            }
            Map<String, Map<String, Object>> whereProperties = new HashMap<>();
            Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
            eqWhereProperties.put(UserPicInfoEntity.INDEX, userPicInfoEntity.getIndex());
            eqWhereProperties.put(UserPicInfoEntity.USER_ID, userPicInfoEntity.getUserId());
            wherePropertiesList.add(whereProperties);
            Map<String, Object> updateProperties = new HashMap<>();
            updateProperties.put(UserPicInfoEntity.OLD_USER_PIC_URL, userPicInfoEntity.getOldUserPicUrl());
            updateProperties.put(UserPicInfoEntity.USER_PIC_URL, userPicInfoEntity.getUserPicUrl());
            updateProperties.put(UserPicInfoEntity.CREATE_TIME, userPicInfoEntity.getCreateTime());
            updatePropertiesList.add(updateProperties);
        });

        SqlBean sqlBean = DBSqlBuildUtil.updateBatchEntity(UserPicInfoEntity.class, updatePropertiesList, wherePropertiesList);
        this.jdbcTemplate.updateBatchData(sqlBean.getSql(), sqlBean.getParamList());
    }

    @Override
    public void refuseUserPicInfo(int userId, int userPicId) throws SQLException {
        Map<String, Map<String, Object>> whereProperties = new HashMap<>();
        Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
        eqWhereProperties.put(UserPicInfoEntity.USER_ID, userId);
        eqWhereProperties.put(UserPicInfoEntity.USER_PIC_ID, userPicId);
        Map<String, Object> updateProperties = new HashMap<>();
        updateProperties.put(UserPicInfoEntity.VERIFY_STATE, ConstUtil.UserPicVerifyState.REFUSE);
        SqlBean sqlBean = DBSqlBuildUtil.updateEntity(UserPicInfoEntity.class, updateProperties, whereProperties);
        this.jdbcTemplate.deleteData(sqlBean.getSql(), sqlBean.getParam());
    }

    @Override
    public void passUserPicInfo(int userId, int userPicId) throws SQLException {
        Object[] param = new Object[2];
        param[0] = userId;
        param[1] = userPicId;
        this.jdbcTemplate.updateData(UserPicWriteDaoImpl.PASS_USER_PIC, param);
    }

    @Override
    public void removeUserPicInfo(int userId, List<Integer> userPicIds) throws SQLException {
        if (ArrayUtils.isNullOrEmpty(userPicIds)) {
            throw new FissionException("removeUserPicInfo userPicIds is null");
        }
        List<Map<String, Map<String, Object>>> wherePropertiesList = new ArrayList<>();
        userPicIds.forEach((Integer userPicId) -> {
            if (null == userPicId) {
                return;
            }
            Map<String, Map<String, Object>> whereProperties = new HashMap<>();
            Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
            eqWhereProperties.put(UserPicInfoEntity.USER_PIC_ID, userPicId);
            eqWhereProperties.put(UserPicInfoEntity.USER_ID, userId);
            wherePropertiesList.add(whereProperties);
        });
        if (wherePropertiesList.isEmpty()) {
            throw new FissionException("removeUserPicInfo wherePropertiesList is null");
        }
        SqlBean sqlBean = DBSqlBuildUtil.deleteBatchEntity(UserPicInfoEntity.class, wherePropertiesList);
        this.jdbcTemplate.deleteBatchData(sqlBean.getSql(), sqlBean.getParamList());
    }

    @Override
    public void removeUserPicInfo(int userId, int minPicNum) throws SQLException {
        if (minPicNum < 0) {
            throw new FissionException("removeUserPicInfo minPicNum < 0");
        }
        Map<String, Map<String, Object>> whereProperties = new HashMap<>();
        Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
        eqWhereProperties.put(UserPicInfoEntity.USER_ID, userId);
        Map<String, Object> gtWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.GT, key -> new HashMap<>());
        gtWhereProperties.put(UserPicInfoEntity.INDEX, minPicNum);
        SqlBean sqlBean = DBSqlBuildUtil.deleteEntity(UserPicInfoEntity.class, whereProperties);
        this.jdbcTemplate.deleteData(sqlBean.getSql(), sqlBean.getParam());
    }
}



