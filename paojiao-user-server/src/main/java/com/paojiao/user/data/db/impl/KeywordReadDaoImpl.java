package com.paojiao.user.data.db.impl;

import com.fission.datasource.sql.FissionJdbcTemplate;
import com.fission.datasource.sql.bean.SqlBean;
import com.fission.datasource.sql.util.DBSqlBuildUtil;
import com.fission.datasource.sql.util.DBSqlUtil;
import com.fission.next.common.error.FissionException;
import com.fission.utils.tool.StringUtil;
import com.paojiao.user.data.db.IKeywordReadDao;
import com.paojiao.user.data.db.entity.NickNameAsciiInfoEntity;
import com.paojiao.user.data.db.entity.NickNameKeywordInfoEntity;
import com.paojiao.user.util.ConstUtil;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class KeywordReadDaoImpl implements IKeywordReadDao {
    @Inject
    @Named(ConstUtil.NameUtil.DB_USER)
    private FissionJdbcTemplate jdbcTemplate;

    @Override
    public NickNameKeywordInfoEntity getNickNameKeyworldInfo(int nickNameKeyworldId) throws SQLException {
        SqlBean sqlBean = DBSqlBuildUtil.getEntityById(NickNameKeywordInfoEntity.class, nickNameKeyworldId);
        return this.jdbcTemplate.queryEntity(sqlBean.getSql(), sqlBean.getParam(), NickNameKeywordInfoEntity.class);
    }

    @Override
    public NickNameKeywordInfoEntity getNickNameKeyworldInfo(String nickNameKeyworld, short ruleType) throws SQLException {
        if (StringUtil.isBlank(nickNameKeyworld)) {
            throw new FissionException("getNickNameKeyworldInfo nickNameKeyworld is null");
        }
        Map<String, Map<String, Object>> whereProperties = new HashMap<>();
        Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
        eqWhereProperties.put(NickNameKeywordInfoEntity.KEYWORD, nickNameKeyworld.trim());
        eqWhereProperties.put(NickNameKeywordInfoEntity.RULE_TYPE, ruleType);
        SqlBean sqlBean = DBSqlBuildUtil.getEntityByProperties(NickNameKeywordInfoEntity.class, whereProperties);
        return this.jdbcTemplate.queryEntity(sqlBean.getSql(), sqlBean.getParam(), NickNameKeywordInfoEntity.class);
    }

    @Override
    public List<NickNameKeywordInfoEntity> listAllNickNameKeywordInfo() throws SQLException {
        SqlBean sqlBean = DBSqlBuildUtil.listAllEntity(NickNameKeywordInfoEntity.class, null);
        return this.jdbcTemplate.listQueryEntity(sqlBean.getSql(), sqlBean.getParam(), NickNameKeywordInfoEntity.class);
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
