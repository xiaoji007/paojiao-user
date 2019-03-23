package com.paojiao.user.data.db.impl;

import com.fission.datasource.sql.FissionJdbcTemplate;
import com.fission.datasource.sql.bean.SqlBean;
import com.fission.datasource.sql.util.DBSqlBuildUtil;
import com.fission.datasource.sql.util.DBSqlUtil;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.data.db.entity.SurfingInfoEntity;
import com.paojiao.user.data.db.entity.SurfingRecoverInfoEntity;
import com.paojiao.user.data.db.entity.SurfingRuleInfoEntity;
import com.paojiao.user.data.db.entity.SurfingSpecialInfoEntity;
import com.paojiao.user.data.db.ISurfingWriteDao;
import org.apache.ibatis.datasource.DataSourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SurfingWriteDaoImpl implements ISurfingWriteDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(SurfingWriteDaoImpl.class);

	private final String UPDATE_SURFING_SQL = "INSERT INTO `surfing_info`(`surfing`,`create_time`) VALUE(?,?) ON DUPLICATE KEY UPDATE `create_time` = ?;";

	@Inject
	@Named(com.paojiao.user.util.ConstUtil.NameUtil.DB_USER)
	private FissionJdbcTemplate jdbcTemplate;

	@Override
	public int addSurfing() throws SQLException {
		SurfingInfoEntity surfingInfoEntity = new SurfingInfoEntity();
		surfingInfoEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		SqlBean sqlBean = DBSqlBuildUtil.addEntity(surfingInfoEntity, (List<String>) null);
		return this.jdbcTemplate.insertDataAutoKey(sqlBean.getSql(), sqlBean.getParam());
	}

	@Override
	public void updateSurfing(int surfing) throws SQLException {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Object[] obj = {surfing, now, now};
		this.jdbcTemplate.insertData(this.UPDATE_SURFING_SQL, obj);
	}

	@Override
	public void addSurfingRuleInfo(SurfingRuleInfoEntity surfingRuleInfoEntity) throws SQLException {
		if (null == surfingRuleInfoEntity) {
			throw new DataSourceException("addSurfingRuleInfo surfingRuleInfoEntity is null ");
		}
		SqlBean sqlBean = DBSqlBuildUtil.addEntity(surfingRuleInfoEntity, (List<String>) null);
		this.jdbcTemplate.insertData(sqlBean.getSql(), sqlBean.getParam());
	}

	@Override
	public void updateSurfingRuleInfo(int surfingRuleId, Map<String, Object> updateAttr) throws SQLException {
		if (null == updateAttr || updateAttr.isEmpty()) {
			throw new DataSourceException("addSurfingRuleInfo updateAttr is null || updateAttr.isEmpty()");
		}
		SqlBean sqlBean = DBSqlBuildUtil.updateEntityById(SurfingRuleInfoEntity.class, surfingRuleId, updateAttr);
		this.jdbcTemplate.updateData(sqlBean.getSql(), sqlBean.getParam());
	}

	@Override
	public void addSurfingSpecialInfo(SurfingSpecialInfoEntity surfingSpecialInfoEntity) throws SQLException {
		if (null == surfingSpecialInfoEntity) {
			throw new DataSourceException("addSurfingSpecialInfo surfingSpecialInfoEntity is null ");
		}
		SqlBean sqlBean = DBSqlBuildUtil.addEntity(surfingSpecialInfoEntity, (List<String>) null);
		this.jdbcTemplate.insertData(sqlBean.getSql(), sqlBean.getParam());
	}

	@Override
	public void updateSurfingSpecialState(int surfingId, short surfingSpecialState) throws SQLException {
		Map<String, Map<String, Object>> whereProperties = new HashMap<>();
		Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
		eqWhereProperties.put(SurfingSpecialInfoEntity.SURFING_ID, surfingId);

		Map<String, Object> updateProperties = new HashMap<>();
		updateProperties.put(SurfingSpecialInfoEntity.SURFING_STATE, surfingSpecialState);
		SqlBean sqlBean = DBSqlBuildUtil.updateEntity(SurfingSpecialInfoEntity.class, updateProperties, whereProperties);
		this.jdbcTemplate.update(sqlBean.getSql(), sqlBean.getParam());
	}

	@Override
	public void addSurfingRecoverInfo(List<SurfingRecoverInfoEntity> surfingRecoverInfoEntitys) throws SQLException {
		if (null == surfingRecoverInfoEntitys || surfingRecoverInfoEntitys.isEmpty()) {
			throw new DataSourceException("addSurfingRecoverInfo surfingRecoverInfoEntitys is null ");
		}
		List<String> replaceProperties = new ArrayList<>();
		replaceProperties.add(SurfingRecoverInfoEntity.UPDATE_TIME);
		SqlBean sqlBean = DBSqlBuildUtil.addEntity(surfingRecoverInfoEntitys, replaceProperties);
		this.jdbcTemplate.insertBatchData(sqlBean.getSql(), sqlBean.getParamList());
	}

	@Override
	public void updateSurfingRecoverState(int surfing, short surfingRecoverState) throws SQLException {
		Map<String, Map<String, Object>> whereProperties = new HashMap<>();
		Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
		eqWhereProperties.put(SurfingRecoverInfoEntity.SURFING, surfing);

		Map<String, Object> updateProperties = new HashMap<>();
		updateProperties.put(SurfingRecoverInfoEntity.SURFING_STATE, surfingRecoverState);
		updateProperties.put(SurfingRecoverInfoEntity.UPDATE_TIME, new Timestamp(System.currentTimeMillis()));
		SqlBean sqlBean = DBSqlBuildUtil.updateEntity(SurfingRecoverInfoEntity.class, updateProperties, whereProperties);
		this.jdbcTemplate.update(sqlBean.getSql(), sqlBean.getParam());
	}

	@Override
	public boolean useRecoverSurfing(int surfing) throws SQLException {
		Map<String, Map<String, Object>> whereProperties = new HashMap<>();
		Map<String, Object> eqWhereProperties = whereProperties.computeIfAbsent(DBSqlUtil.SqlWhereType.EQ, key -> new HashMap<>());
		eqWhereProperties.put(SurfingRecoverInfoEntity.SURFING, surfing);
		eqWhereProperties.put(SurfingRecoverInfoEntity.SURFING_STATE, ConstUtil.SurfingRecoverState.USER_USR);

		Map<String, Object> updateProperties = new HashMap<>();
		updateProperties.put(SurfingRecoverInfoEntity.SURFING_STATE, ConstUtil.SurfingRecoverState.USER_ALREADY_USR);
		updateProperties.put(SurfingRecoverInfoEntity.UPDATE_TIME, new Timestamp(System.currentTimeMillis()));
		SqlBean sqlBean = DBSqlBuildUtil.updateEntity(SurfingRecoverInfoEntity.class, updateProperties, whereProperties);
		return this.jdbcTemplate.update(sqlBean.getSql(), sqlBean.getParam()) > 0;
	}
}



