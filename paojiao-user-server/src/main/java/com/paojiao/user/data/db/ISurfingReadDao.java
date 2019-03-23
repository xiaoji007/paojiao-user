package com.paojiao.user.data.db;

import com.paojiao.user.data.db.entity.SurfingRecoverInfoEntity;
import com.paojiao.user.data.db.entity.SurfingRuleInfoEntity;
import com.paojiao.user.data.db.entity.SurfingSpecialInfoEntity;
import com.fission.utils.bean.Page;

import java.sql.SQLException;
import java.util.List;

public interface ISurfingReadDao {

	SurfingSpecialInfoEntity getSurfingSpecialInfo(int surfingId)throws SQLException;

	Page<SurfingSpecialInfoEntity> listSurfingSpecialInfo(short surfingState, int index, int length) throws SQLException;

	Page<SurfingRuleInfoEntity> listSurfingRuleInfo(int surfingNum, int index, int length) throws SQLException;

	List<SurfingRuleInfoEntity> listSurfingRuleInfo() throws SQLException;

	Page<SurfingRecoverInfoEntity> listSurfingRecoverInfo(short surfingRecoverState, int index, int length) throws SQLException;
}


