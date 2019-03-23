package com.paojiao.user.data.db.impl;

import com.fission.datasource.sql.FissionJdbcTemplate;
import com.fission.datasource.sql.bean.SqlBean;
import com.fission.datasource.sql.bean.SqlPageBean;
import com.fission.datasource.sql.util.DBSqlBuildUtil;
import com.fission.datasource.sql.util.DBSqlUtil;
import com.fission.next.common.error.FissionException;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.data.db.ISurfingReadDao;
import com.paojiao.user.data.db.entity.SurfingRecoverInfoEntity;
import com.paojiao.user.data.db.entity.SurfingRuleInfoEntity;
import com.paojiao.user.data.db.entity.SurfingSpecialInfoEntity;
import com.fission.utils.bean.Page;
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

@Repository
public class SurfingReadDaoImpl implements ISurfingReadDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(SurfingReadDaoImpl.class);

	@Inject
	@Named(com.paojiao.user.util.ConstUtil.NameUtil.DB_USER)
	private FissionJdbcTemplate jdbcTemplate;


	@Override
	public SurfingSpecialInfoEntity getSurfingSpecialInfo(int surfingId) throws SQLException {
		SqlBean sqlBean = DBSqlBuildUtil.getEntityById(SurfingSpecialInfoEntity.class, surfingId);
		return this.jdbcTemplate.queryEntity(sqlBean.getSql(), sqlBean.getParam(), SurfingSpecialInfoEntity.class);
	}

	@Override
	public Page<SurfingSpecialInfoEntity> listSurfingSpecialInfo(short surfingState, int index, int length) throws SQLException {
		if (index < 0 || length < 1) {
			throw new FissionException("listSpecialSurfingInfo index < 0 || length < 1");
		}
		SqlPageBean sqlPageBean;
		Map<String, List<String>> orderProperties = new HashMap();
		List<String> list = orderProperties.computeIfAbsent(DBSqlUtil.SqlOrderType.ASC, key -> new ArrayList<>());
		list.add(SurfingSpecialInfoEntity.SURFING_ID);
		if (ConstUtil.SurfingSpecialState.UNDEFIND == surfingState) {
			sqlPageBean = DBSqlBuildUtil.getSqlPageBean(SurfingSpecialInfoEntity.class, orderProperties, index, length);
		} else {
			Map<String, Map<String, Object>> whereProperties = new HashMap<>();
			Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
			eqWhereProperties.put(SurfingSpecialInfoEntity.SURFING_STATE, surfingState);
			sqlPageBean = DBSqlBuildUtil.getSqlPageBean(SurfingSpecialInfoEntity.class, whereProperties, orderProperties, index, length);
		}
		return this.jdbcTemplate.pageQueryEntity(sqlPageBean.getQueryListSqlFunction(), sqlPageBean.getConutListSqlFunction(), index, length, SurfingSpecialInfoEntity.class);
	}

	@Override
	public Page<SurfingRuleInfoEntity> listSurfingRuleInfo(int surfingNum, int index, int length) throws SQLException {
		if (index < 0 || length < 1) {
			throw new FissionException("listSurfingRuleInfo index < 0 || length < 1");
		}
		SqlPageBean sqlPageBean;
		Map<String, List<String>> orderProperties = new HashMap();
		List<String> list = orderProperties.computeIfAbsent(DBSqlUtil.SqlOrderType.ASC, key -> new ArrayList<>());
		list.add(SurfingRuleInfoEntity.SURFING_RULE_ID);
		if (surfingNum < 0) {
			sqlPageBean = DBSqlBuildUtil.getSqlPageBean(SurfingRuleInfoEntity.class, orderProperties, index, length);
		} else {
			Map<String, Map<String, Object>> whereProperties = new HashMap<>();
			Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
			eqWhereProperties.put(SurfingRuleInfoEntity.SURFING_NUM, surfingNum);
			sqlPageBean = DBSqlBuildUtil.getSqlPageBean(SurfingRuleInfoEntity.class, whereProperties, orderProperties, index, length);
		}
		return this.jdbcTemplate.pageQueryEntity(sqlPageBean.getQueryListSqlFunction(), sqlPageBean.getConutListSqlFunction(), index, length, SurfingRuleInfoEntity.class);
	}

	@Override
	public List<SurfingRuleInfoEntity> listSurfingRuleInfo() throws SQLException {
		SqlBean sqlBean = DBSqlBuildUtil.listAllEntity(SurfingRuleInfoEntity.class, null);
		return this.jdbcTemplate.listQueryEntity(sqlBean.getSql(), sqlBean.getParam(), SurfingRuleInfoEntity.class);
	}

	@Override
	public Page<SurfingRecoverInfoEntity> listSurfingRecoverInfo(short surfingRecoverState, int index, int length) throws SQLException {
		if (index < 0 || length < 1) {
			throw new FissionException("listSurfingRecoverInfo index < 0 || length < 1");
		}
		SqlPageBean sqlPageBean;
		Map<String, List<String>> orderProperties = new HashMap();
		List<String> list = orderProperties.computeIfAbsent(DBSqlUtil.SqlOrderType.ASC, key -> new ArrayList<>());
		list.add(SurfingRecoverInfoEntity.SURFING);
		if (ConstUtil.SurfingRecoverState.UNDEFIND == surfingRecoverState) {
			sqlPageBean = DBSqlBuildUtil.getSqlPageBean(SurfingRecoverInfoEntity.class, orderProperties, index, length);
		} else {
			Map<String, Map<String, Object>> whereProperties = new HashMap<>();
			Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
			eqWhereProperties.put(SurfingRecoverInfoEntity.SURFING, surfingRecoverState);
			sqlPageBean = DBSqlBuildUtil.getSqlPageBean(SurfingRecoverInfoEntity.class, whereProperties, orderProperties, index, length);
		}
		return this.jdbcTemplate.pageQueryEntity(sqlPageBean.getQueryListSqlFunction(), sqlPageBean.getConutListSqlFunction(), index, length, SurfingRecoverInfoEntity.class);
	}
}



