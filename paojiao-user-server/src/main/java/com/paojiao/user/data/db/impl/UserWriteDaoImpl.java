package com.paojiao.user.data.db.impl;

import com.fission.datasource.exception.DataSourceException;
import com.fission.datasource.sql.FissionJdbcTemplate;
import com.fission.datasource.sql.bean.SqlBean;
import com.fission.datasource.sql.util.DBSqlBuildUtil;
import com.fission.datasource.sql.util.DBSqlUtil;
import com.paojiao.user.data.db.IUserWriteDao;
import com.paojiao.user.data.db.entity.UserAttrInfoEntity;
import com.paojiao.user.data.db.entity.UserInfoEntity;
import com.paojiao.user.data.db.entity.UserInviteInfoEntity;
import com.paojiao.user.util.ConstUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userWriteDao")
public class UserWriteDaoImpl implements IUserWriteDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserWriteDaoImpl.class);

    @Inject
    @Named(ConstUtil.NameUtil.DB_USER)
    private FissionJdbcTemplate jdbcTemplate;

    @Override
    public int addUserInfo(UserInfoEntity userInfoEntity) throws SQLException {
        if (null == userInfoEntity) {
            throw new DataSourceException("addUserInfo userInfoEntity is null");
        }
        List<String> replaceProperties = new ArrayList<>();
        replaceProperties.add(UserInfoEntity.CREATE_TIME);
        replaceProperties.add(UserInfoEntity.LAST_UPDATE_TIME);
        SqlBean sqlBean = DBSqlBuildUtil.addEntity(userInfoEntity, replaceProperties);
        return this.jdbcTemplate.insertDataAutoKey(sqlBean.getSql(), sqlBean.getParam());
    }

    @Override
    public void updateUserInfo(int userId, Map<String, Object> updateProperties) throws SQLException {
        if (null == updateProperties || updateProperties.isEmpty()) {
            throw new DataSourceException("updateUserInfo  updateProperties is null");
        }
        //更新原始数据
        {
            SqlBean sqlBean = DBSqlBuildUtil.updateEntityById(UserInfoEntity.class, userId, updateProperties);
            this.jdbcTemplate.updateData(sqlBean.getSql(), sqlBean.getParam());
        }
    }

    @Override
    public void addUserAttrInfo(int userId, List<UserAttrInfoEntity> userAttrInfoEntitys) throws SQLException {
        if (null == userAttrInfoEntitys || userAttrInfoEntitys.isEmpty()) {
            throw new DataSourceException("addUserAttrInfo userAttrInfoEntitys is null");
        }
        //更新扩展数据
        {
            List<String> replaceProperties = new ArrayList<>();
            replaceProperties.add(UserAttrInfoEntity.USER_ATTR);
            replaceProperties.add(UserAttrInfoEntity.LAST_UPDATE_TIME);
            SqlBean sqlBean = DBSqlBuildUtil.addEntity(userAttrInfoEntitys, replaceProperties);
            this.jdbcTemplate.updateBatchData(sqlBean.getSql(), sqlBean.getParamList());
        }
    }

    @Override
    public void addUserInviteInfo(UserInviteInfoEntity userInviteInfoEntity) throws SQLException {
        if (null == userInviteInfoEntity) {
            throw new DataSourceException("UserWriteDaoImpl addUserInviteInfo userInviteInfoEntity is null");
        }
        SqlBean sqlBean = DBSqlBuildUtil.addEntity(userInviteInfoEntity, (List<String>) null);
        this.jdbcTemplate.insertData(sqlBean.getSql(), sqlBean.getParam());
    }

    @Override
    public boolean subUserInviteNum(String inviteCode) throws SQLException {
        Map<String, Map<String, Object>> whereProperties = new HashMap<>();
        Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
        eqWhereProperties.put(UserInviteInfoEntity.INVITE_CODE, inviteCode);

        Map<String, Object> gtWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.GT, key -> new HashMap<>());
        gtWhereProperties.put(UserInviteInfoEntity.INVITE_NUM, 0);

        Map<Short, Map<String, Object>> updateColumnListMap = new HashMap<>();
        Map<String, Object> updateProperties = updateColumnListMap.computeIfAbsent(DBSqlUtil.UpdateType.ADD, key -> new HashMap<>());
        updateProperties.put(UserInviteInfoEntity.INVITE_NUM, -1);
        SqlBean sqlBean = DBSqlBuildUtil.updateEntity2(UserInviteInfoEntity.class, updateColumnListMap, whereProperties);
        return this.jdbcTemplate.update(sqlBean.getSql(), sqlBean.getParam()) > 0;
    }
}



