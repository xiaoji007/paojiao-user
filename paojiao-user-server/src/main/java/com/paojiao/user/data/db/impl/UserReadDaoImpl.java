package com.paojiao.user.data.db.impl;

import com.fission.datasource.sql.FissionJdbcTemplate;
import com.fission.datasource.sql.bean.SqlBean;
import com.fission.datasource.sql.util.DBSqlBuildUtil;
import com.fission.datasource.sql.util.DBSqlUtil;
import com.fission.utils.tool.ArrayUtils;
import com.paojiao.user.data.db.IUserReadDao;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userReadDao")
public class UserReadDaoImpl implements IUserReadDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserReadDaoImpl.class);

    @Inject
    @Named(ConstUtil.NameUtil.DB_USER)
    private FissionJdbcTemplate jdbcTemplate;

    @Override
    public UserInfoEntity getUserInfo(int userId) throws SQLException {
        SqlBean sqlBean = DBSqlBuildUtil.getEntityById(UserInfoEntity.class, userId);
        UserInfoEntity userInfoEntity = this.jdbcTemplate.queryEntity(sqlBean.getSql(), sqlBean.getParam(), UserInfoEntity.class);
        return userInfoEntity;
    }

    @Override
    public List<UserAttrInfoEntity> listUserAttrInfo(int userId) throws SQLException {
        Map<String, Map<String, Object>> whereProperties = new HashMap<>();
        Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
        eqWhereProperties.put(UserAttrInfoEntity.USER_ID, userId);
        SqlBean sqlBean = DBSqlBuildUtil.listEntityByProperties(UserAttrInfoEntity.class, whereProperties, null);
        List<UserAttrInfoEntity> userAttrInfoEntitys = this.jdbcTemplate.listQueryEntity(sqlBean.getSql(), sqlBean.getParam(), UserAttrInfoEntity.class);
        return userAttrInfoEntitys;
    }

    @Override
    public UserInfoEntity getSelfUserInfo(int userId) throws SQLException {
        return this.getUserInfo(userId);
    }

    @Override
    public List<UserAttrInfoEntity> listSelfUserAttrInfo(int userId) throws SQLException {
        return this.listUserAttrInfo(userId);
    }

    @Override
    public UserInviteInfoEntity getInviteUser(String inviteCode) throws SQLException {
        Map<String, Map<String, Object>> whereProperties = new HashMap<>();
        Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
        eqWhereProperties.put(UserInviteInfoEntity.INVITE_CODE, inviteCode);
        SqlBean sqlBean = DBSqlBuildUtil.listEntityByProperties(UserInviteInfoEntity.class, whereProperties, null);
        List<UserInviteInfoEntity> list = this.jdbcTemplate.listQueryEntity(sqlBean.getSql(), sqlBean.getParam(), UserInviteInfoEntity.class);
        return ArrayUtils.isNullOrEmpty(list) ? null : list.get(0);
    }
}



