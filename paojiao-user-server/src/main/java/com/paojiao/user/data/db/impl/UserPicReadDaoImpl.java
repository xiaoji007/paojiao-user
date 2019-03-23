package com.paojiao.user.data.db.impl;

import com.fission.datasource.sql.FissionJdbcTemplate;
import com.fission.datasource.sql.bean.SqlBean;
import com.fission.datasource.sql.util.DBSqlBuildUtil;
import com.fission.datasource.sql.util.DBSqlUtil;
import com.paojiao.user.data.db.IUserPicReadDao;
import com.paojiao.user.data.db.entity.UserPicInfoEntity;
import com.paojiao.user.util.ConstUtil;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userPicReadDao")
public class UserPicReadDaoImpl implements IUserPicReadDao {
    @Inject
    @Named(ConstUtil.NameUtil.DB_USER)
    private FissionJdbcTemplate jdbcTemplate;

    @Override
    public List<UserPicInfoEntity> listAllUserPicInfo(int userId) throws SQLException {
        return this.listAllUserPicInfo2(userId);
    }

    @Override
    public List<UserPicInfoEntity> listResetAllUserPicInfo(int userId) throws SQLException {
        return this.listAllUserPicInfo2(userId);
    }

    private List<UserPicInfoEntity> listAllUserPicInfo2(int userId) throws SQLException {
        Map<String, Map<String, Object>> whereProperties = new HashMap<>();
        Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
        eqWhereProperties.put(UserPicInfoEntity.USER_ID, userId);
        Map<String, List<String>> orderProperties = new HashMap<>();
        List<String> orderList = orderProperties.computeIfAbsent(DBSqlUtil.SqlOrderType.ASC, key -> new ArrayList<>());
        orderList.add(UserPicInfoEntity.INDEX);
        SqlBean sqlBean = DBSqlBuildUtil.listEntityByProperties(UserPicInfoEntity.class, whereProperties, orderProperties);
        List<UserPicInfoEntity> list = this.jdbcTemplate.listQueryEntity(sqlBean.getSql(), sqlBean.getParam(), UserPicInfoEntity.class);
        if (null == list) {
            list = new ArrayList<>();
        }
        return list;
    }
}



