package com.paojiao.user.data.db;

import com.paojiao.user.data.db.entity.SurfingRecoverInfoEntity;
import com.paojiao.user.data.db.entity.SurfingRuleInfoEntity;
import com.paojiao.user.data.db.entity.SurfingSpecialInfoEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ISurfingWriteDao {

	int addSurfing() throws SQLException;

	void updateSurfing(int surfing) throws SQLException;

	void addSurfingRuleInfo(SurfingRuleInfoEntity surfingRuleInfoEntity) throws SQLException;

	void updateSurfingRuleInfo(int surfingRuleId, Map<String, Object> updateAttr) throws SQLException;

	void addSurfingSpecialInfo(SurfingSpecialInfoEntity surfingSpecialInfoEntity) throws SQLException;

	void updateSurfingSpecialState(int surfingId, short surfingSpecialState) throws SQLException;

	void addSurfingRecoverInfo(List<SurfingRecoverInfoEntity> surfingRecoverInfoEntitys) throws SQLException;

	void updateSurfingRecoverState(int surfing, short surfingRecoverState) throws SQLException;

	boolean useRecoverSurfing(int surfing) throws SQLException;

}


