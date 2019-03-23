package com.paojiao.user.data.db.impl;

import com.fission.datasource.sql.FissionJdbcTemplate;
import com.fission.datasource.sql.bean.SqlBean;
import com.fission.datasource.sql.util.DBSqlBuildUtil;
import com.fission.datasource.sql.util.DBSqlUtil;
import com.fission.next.common.error.FissionException;
import com.fission.utils.tool.StringUtil;
import com.paojiao.user.data.db.IKeyworldReadDao;
import com.paojiao.user.data.db.entity.NickNameAsciiInfoEntity;
import com.paojiao.user.data.db.entity.NickNameKeyworldInfoEntity;
import com.paojiao.user.util.ConstUtil;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class KeyworldReadDaoImpl implements IKeyworldReadDao {
    @Inject
    @Named(ConstUtil.NameUtil.DB_USER)
    private FissionJdbcTemplate jdbcTemplate;

    @Override
    public NickNameKeyworldInfoEntity getNickNameKeyworldInfo(int nickNameKeyworldId) throws SQLException {
        SqlBean sqlBean = DBSqlBuildUtil.getEntityById(NickNameKeyworldInfoEntity.class, nickNameKeyworldId);
        return this.jdbcTemplate.queryEntity(sqlBean.getSql(), sqlBean.getParam(), NickNameKeyworldInfoEntity.class);
    }

    @Override
    public NickNameKeyworldInfoEntity getNickNameKeyworldInfo(String nickNameKeyworld, short ruleType) throws SQLException {
        if (StringUtil.isBlank(nickNameKeyworld)) {
            throw new FissionException("getNickNameKeyworldInfo nickNameKeyworld is null");
        }
        Map<String, Map<String, Object>> whereProperties = new HashMap<>();
        Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
        eqWhereProperties.put(NickNameKeyworldInfoEntity.KEYWORLD, nickNameKeyworld.trim());
        eqWhereProperties.put(NickNameKeyworldInfoEntity.RULE_TYPE, ruleType);
        SqlBean sqlBean = DBSqlBuildUtil.getEntityByProperties(NickNameKeyworldInfoEntity.class, whereProperties);
        return this.jdbcTemplate.queryEntity(sqlBean.getSql(), sqlBean.getParam(), NickNameKeyworldInfoEntity.class);
    }

    @Override
    public List<NickNameKeyworldInfoEntity> listAllNickNameKeywordInfo() throws SQLException {
        SqlBean sqlBean = DBSqlBuildUtil.listAllEntity(NickNameKeyworldInfoEntity.class, null);
        return this.jdbcTemplate.listQueryEntity(sqlBean.getSql(), sqlBean.getParam(), NickNameKeyworldInfoEntity.class);
    }

    @Override
    public NickNameAsciiInfoEntity getNickNameAsciiInfo(int nickNameAsciiId) throws SQLException {
        SqlBean sqlBean = DBSqlBuildUtil.getEntityById(NickNameAsciiInfoEntity.class, nickNameAsciiId);
        return this.jdbcTemplate.queryEntity(sqlBean.getSql(), sqlBean.getParam(), NickNameAsciiInfoEntity.class);
    }

    @Override
    public List<NickNameAsciiInfoEntity> listAllNickNameAsciiInfo() throws SQLException {
        SqlBean sqlBean = DBSqlBuildUtil.listAllEntity(NickNameAsciiInfoEntity.class, null);
        return this.jdbcTemplate.listQueryEntity(sqlBean.getSql(), sqlBean.getParam(), NickNameAsciiInfoEntity.class);
    }
}
