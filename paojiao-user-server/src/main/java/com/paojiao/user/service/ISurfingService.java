package com.paojiao.user.service;

import com.fission.datasource.exception.RollbackSourceException;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.service.bean.SurfingRecoverInfo;
import com.paojiao.user.service.bean.SurfingRuleInfo;
import com.paojiao.user.service.bean.SurfingSpecialInfo;
import com.fission.utils.bean.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ISurfingService {

	@Transactional(rollbackFor = {RollbackSourceException.class})
	int addSurfing();

	@Transactional(rollbackFor = {RollbackSourceException.class})
	void addSurfingSpecialInfo(int surfing, int toSurfing);

	Map<Integer, List<SurfingRuleInfo>> listSurfingRuleInfo();

	List<Integer> listSurfingRecoverSurfingByUserUnuse();

	@Transactional(rollbackFor = {RollbackSourceException.class})
	boolean useSurfingRecover(int surfing);

	@Transactional(rollbackFor = {RollbackSourceException.class})
	void addSurfingRuleInfo(SurfingRuleInfo surfingRuleInfo);

	@Transactional(rollbackFor = {RollbackSourceException.class})
	void updateSurfingRuleInfo(int surfingRuleId, Map<ConstUtil.SurfingRuleAttrName, Object> updateAttr);

	Page<SurfingRuleInfo> listSurfingRuleInfo(int surfingNum, int index, int length);

	Page<SurfingSpecialInfo> listSurfingSpecialInfo(short surfingSpecialState, int index, int length);

	List<Integer> listSurfingSpecialSurfingIdByInit();

	@Transactional(rollbackFor = {RollbackSourceException.class})
	void updateSurfingSpecialState(int surfingId, short surfingSpecialState);

	@Transactional(rollbackFor = {RollbackSourceException.class})
	void surfingSpecialToSurfingRecover(int surfingId);

	Page<SurfingRecoverInfo> listSurfingRecoverInfo(short surfingRecoverState, int index, int length);

	@Transactional(rollbackFor = {RollbackSourceException.class})
	void updateSurfingRecoverState(int surfing, short surfingRecoverState);
}
