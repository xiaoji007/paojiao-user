package com.paojiao.user.service.impl;

import com.fission.datasource.exception.RollbackSourceException;
import com.fission.next.common.error.FissionException;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.data.db.ISurfingReadDao;
import com.paojiao.user.data.db.ISurfingWriteDao;
import com.paojiao.user.data.db.entity.SurfingRecoverInfoEntity;
import com.paojiao.user.data.db.entity.SurfingRuleInfoEntity;
import com.paojiao.user.data.db.entity.SurfingSpecialInfoEntity;
import com.paojiao.user.service.ISurfingService;
import com.paojiao.user.service.bean.SurfingRecoverInfo;
import com.paojiao.user.service.bean.SurfingRuleInfo;
import com.paojiao.user.service.bean.SurfingSpecialInfo;
import com.fission.utils.bean.Page;
import com.fission.utils.tool.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;

@Service
public class SurfingServiceImpl implements ISurfingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SurfingServiceImpl.class);

	@Inject
	private ISurfingReadDao surfingReadDao;

	@Inject
	private ISurfingWriteDao surfingWriteDao;

	@Override
	public int addSurfing() {
		try {
			return this.surfingWriteDao.addSurfing();
		} catch (Exception e) {
			throw new RollbackSourceException(e);
		}
	}

	@Override
	public void addSurfingSpecialInfo(int surfing, int toSurfing) {
		try {
			SurfingSpecialInfoEntity surfingSpecialInfoEntity = new SurfingSpecialInfoEntity();
			surfingSpecialInfoEntity.setSurfing(surfing);
			surfingSpecialInfoEntity.setToSurfing(toSurfing);
			surfingSpecialInfoEntity.setSurfingState(ConstUtil.SurfingSpecialState.INIT);
			surfingSpecialInfoEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			this.surfingWriteDao.addSurfingSpecialInfo(surfingSpecialInfoEntity);
			if (toSurfing > surfing) {
				this.surfingWriteDao.updateSurfing(toSurfing);
			}
		} catch (Exception e) {
			throw new RollbackSourceException(e);
		}
	}

	private SurfingRuleInfo initSurfingRuleInfo(SurfingRuleInfoEntity surfingRuleInfoEntity) {
		if (null == surfingRuleInfoEntity) {
			return null;
		}
		SurfingRuleInfo surfingRuleInfo = new SurfingRuleInfo();
		long time = null == surfingRuleInfoEntity.getCreateTime() ? System.currentTimeMillis() : surfingRuleInfoEntity.getCreateTime().getTime();
		surfingRuleInfo.setCreateTime(new Date(time));
		surfingRuleInfo.setSurfingIncrement(surfingRuleInfoEntity.getSurfingIncrement());
		surfingRuleInfo.setSurfingNum(surfingRuleInfoEntity.getSurfingNum());
		surfingRuleInfo.setSurfingRule(surfingRuleInfoEntity.getSurfingRule());
		surfingRuleInfo.setSurfingRuleDesc(surfingRuleInfoEntity.getSurfingRuleDesc());
		surfingRuleInfo.setSurfingRuleId(surfingRuleInfoEntity.getSurfingRuleId());
		return surfingRuleInfo;
	}

	private SurfingRuleInfoEntity initSurfingRuleInfoEntity(SurfingRuleInfo surfingRuleInfo) {
		if (null == surfingRuleInfo) {
			return null;
		}
		SurfingRuleInfoEntity surfingRuleInfoEntity = new SurfingRuleInfoEntity();
		long time = null == surfingRuleInfo.getCreateTime() ? System.currentTimeMillis() : surfingRuleInfo.getCreateTime().getTime();
		surfingRuleInfoEntity.setCreateTime(new Timestamp(time));
		surfingRuleInfoEntity.setSurfingIncrement(surfingRuleInfoEntity.getSurfingIncrement());
		surfingRuleInfoEntity.setSurfingNum(surfingRuleInfoEntity.getSurfingNum());
		surfingRuleInfoEntity.setSurfingRule(surfingRuleInfoEntity.getSurfingRule());
		surfingRuleInfoEntity.setSurfingRuleDesc(surfingRuleInfoEntity.getSurfingRuleDesc());
		surfingRuleInfoEntity.setSurfingRuleId(surfingRuleInfoEntity.getSurfingRuleId());
		return surfingRuleInfoEntity;
	}

	@Override
	public Map<Integer, List<SurfingRuleInfo>> listSurfingRuleInfo() {
		try {
			List<SurfingRuleInfoEntity> surfingRuleInfoEntitys = this.surfingReadDao.listSurfingRuleInfo();
			if (null == surfingRuleInfoEntitys || surfingRuleInfoEntitys.isEmpty()) {
				return null;
			}
			Map<Integer, List<SurfingRuleInfo>> map = new HashMap<>();
			surfingRuleInfoEntitys.forEach((SurfingRuleInfoEntity surfingRuleInfoEntity) -> {
				SurfingRuleInfo SurfingRuleInfo = this.initSurfingRuleInfo(surfingRuleInfoEntity);
				if (null != SurfingRuleInfo) {
					List<SurfingRuleInfo> list = map.computeIfAbsent(SurfingRuleInfo.getSurfingNum(), key -> new ArrayList<>());
					list.add(SurfingRuleInfo);
					list.sort((SurfingRuleInfo surfingRuleInfo1, SurfingRuleInfo surfingRuleInfo2) -> Integer.compare(surfingRuleInfo1.getSurfingNum(), surfingRuleInfo2.getSurfingNum()));
				}
			});
			return map;
		} catch (Exception e) {
			throw new FissionException(e);
		}
	}

	@Override
	public List<Integer> listSurfingRecoverSurfingByUserUnuse() {
		try {
			Page<SurfingRecoverInfoEntity> page = this.surfingReadDao.listSurfingRecoverInfo(ConstUtil.SurfingRecoverState.USER_USR, 0, 10000);
			if (ArrayUtils.isNullOrEmpty(page.getList())) {
				return null;
			}
			List<Integer> list = new ArrayList<>();
			page.getList().forEach((SurfingRecoverInfoEntity surfingRecoverInfoEntity) -> {
				if (null != surfingRecoverInfoEntity) {
					list.add(surfingRecoverInfoEntity.getSurfing());
				}
			});
			return list;
		} catch (Exception e) {
			throw new FissionException(e);
		}
	}

	@Override
	public boolean useSurfingRecover(int surfing) {
		try {
			return this.surfingWriteDao.useRecoverSurfing(surfing);
		} catch (Exception e) {
			throw new RollbackSourceException(e);
		}
	}

	@Override
	public void addSurfingRuleInfo(SurfingRuleInfo surfingRuleInfo) {
		SurfingRuleInfoEntity surfingRuleInfoEntity = this.initSurfingRuleInfoEntity(surfingRuleInfo);
		if (null == surfingRuleInfoEntity) {
			throw new FissionException("addSurfingRuleInfo surfingRuleInfoEntity is null");
		}
		try {
			this.surfingWriteDao.addSurfingRuleInfo(surfingRuleInfoEntity);
		} catch (Exception e) {
			throw new FissionException(e);
		}
	}

	@Override
	public void updateSurfingRuleInfo(int surfingRuleId, Map<ConstUtil.SurfingRuleAttrName, Object> updateAttr) {
		if (null == updateAttr || updateAttr.isEmpty()) {
			throw new FissionException("updateSurfingRuleInfo updateAttr is null");
		}
		Map<String, Object> map = new HashMap<>();
		updateAttr.forEach((ConstUtil.SurfingRuleAttrName attrName, Object obj) -> {
			if (null != attrName) {
				map.put(attrName.getAttrName(), obj);
			}
		});
		if (updateAttr.isEmpty()) {
			throw new FissionException("updateSurfingRuleInfo map is null");
		}
		try {
			this.surfingWriteDao.updateSurfingRuleInfo(surfingRuleId, map);
		} catch (Exception e) {
			throw new FissionException(e);
		}
	}

	@Override
	public Page<SurfingRuleInfo> listSurfingRuleInfo(int surfingNum, int index, int length) {
		try {
			Page<SurfingRuleInfoEntity> page = this.surfingReadDao.listSurfingRuleInfo(surfingNum, index, length);
			Function<SurfingRuleInfoEntity, SurfingRuleInfo> function = ((SurfingRuleInfoEntity surfingRuleInfoEntity) -> {
				return this.initSurfingRuleInfo(surfingRuleInfoEntity);
			});
			return this.initPageInfo(page, function);
		} catch (Exception e) {
			throw new FissionException(e);
		}
	}

	private <T, R> Page<T> initPageInfo(Page<R> initPage, Function<R, T> function) {
		if (null == initPage || ArrayUtils.isNullOrEmpty(initPage.getList()) || null == function) {
			return null;
		}
		Page<T> page = new Page<>();
		page.setIndex(page.getIndex());
		page.setLength(page.getLength());
		page.setTotal(page.getTotal());
		List<T> list = new ArrayList<>();
		page.setList(list);

		initPage.getList().forEach((R entity) -> {
			T t = function.apply(entity);
			if (null != t) {
				list.add(t);
			}
		});
		return page;
	}

	private SurfingSpecialInfo initSurfingSpecialInfo(SurfingSpecialInfoEntity surfingSpecialInfoEntity) {
		if (null == surfingSpecialInfoEntity) {
			return null;
		}
		SurfingSpecialInfo surfingSpecialInfo = new SurfingSpecialInfo();
		long time = null == surfingSpecialInfoEntity.getCreateTime() ? System.currentTimeMillis() : surfingSpecialInfoEntity.getCreateTime().getTime();
		surfingSpecialInfo.setCreateTime(new Date(time));
		surfingSpecialInfo.setSurfing(surfingSpecialInfoEntity.getSurfing());
		surfingSpecialInfo.setSurfingId(surfingSpecialInfoEntity.getSurfingId());
		surfingSpecialInfo.setSurfingState(surfingSpecialInfoEntity.getSurfingState());
		surfingSpecialInfo.setToSurfing(surfingSpecialInfoEntity.getToSurfing());
		return surfingSpecialInfo;
	}

	@Override
	public Page<SurfingSpecialInfo> listSurfingSpecialInfo(short surfingSpecialState, int index, int length) {
		try {
			Page<SurfingSpecialInfoEntity> page = this.surfingReadDao.listSurfingSpecialInfo(surfingSpecialState, index, length);
			Function<SurfingSpecialInfoEntity, SurfingSpecialInfo> function = ((SurfingSpecialInfoEntity surfingSpecialInfoEntity) -> {
				return this.initSurfingSpecialInfo(surfingSpecialInfoEntity);
			});
			return this.initPageInfo(page, function);
		} catch (Exception e) {
			throw new FissionException(e);
		}
	}

	@Override
	public List<Integer> listSurfingSpecialSurfingIdByInit() {
		try {
			Page<SurfingSpecialInfoEntity> page = this.surfingReadDao.listSurfingSpecialInfo(ConstUtil.SurfingSpecialState.INIT, 0, 10000);
			if (ArrayUtils.isNullOrEmpty(page.getList())) {
				return null;
			}
			List<Integer> list = new ArrayList<>();
			page.getList().forEach((SurfingSpecialInfoEntity surfingSpecialInfoEntity) -> {
				if (null != surfingSpecialInfoEntity) {
					list.add(surfingSpecialInfoEntity.getSurfingId());
				}
			});
			return list;
		} catch (Exception e) {
			throw new FissionException(e);
		}
	}

	@Override
	public void updateSurfingSpecialState(int surfingId, short surfingSpecialState) {
		try {
			this.surfingWriteDao.updateSurfingSpecialState(surfingId, surfingSpecialState);
		} catch (Exception e) {
			throw new FissionException(e);
		}
	}

	@Override
	public void surfingSpecialToSurfingRecover(int surfingId) {
		try {
			SurfingSpecialInfoEntity surfingSpecialInfoEntity = this.surfingReadDao.getSurfingSpecialInfo(surfingId);
			if (null == surfingSpecialInfoEntity || ConstUtil.SurfingSpecialState.INIT != surfingSpecialInfoEntity.getSurfingState()) {
				return;
			}

			this.surfingWriteDao.updateSurfingSpecialState(surfingSpecialInfoEntity.getSurfingId(), ConstUtil.SurfingSpecialState.MANAGE);

			List<SurfingRecoverInfoEntity> list = new ArrayList<>();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			for (int i = surfingSpecialInfoEntity.getSurfing(); i <= surfingSpecialInfoEntity.getToSurfing(); i++) {
				SurfingRecoverInfoEntity surfingRecoverInfoEntity = new SurfingRecoverInfoEntity();
				surfingRecoverInfoEntity.setCreateTime(timestamp);
				surfingRecoverInfoEntity.setSurfing(i);
				surfingRecoverInfoEntity.setSurfingState(ConstUtil.SurfingRecoverState.INIT);
				surfingRecoverInfoEntity.setUpdateTime(timestamp);
				list.add(surfingRecoverInfoEntity);
			}
			if (list.isEmpty()) {
				return;
			}
			this.surfingWriteDao.addSurfingRecoverInfo(list);
		} catch (Exception e) {
			throw new RollbackSourceException(e);
		}
	}

	private SurfingRecoverInfo initSurfingRecoverInfo(SurfingRecoverInfoEntity surfingRecoverInfoEntity) {
		if (null == surfingRecoverInfoEntity) {
			return null;
		}
		SurfingRecoverInfo surfingRecoverInfo = new SurfingRecoverInfo();
		long time = null == surfingRecoverInfoEntity.getCreateTime() ? System.currentTimeMillis() : surfingRecoverInfoEntity.getCreateTime().getTime();
		surfingRecoverInfo.setCreateTime(new Date(time));
		surfingRecoverInfo.setSurfing(surfingRecoverInfoEntity.getSurfing());
		surfingRecoverInfo.setSurfingState(surfingRecoverInfoEntity.getSurfingState());
		long updateTime = null == surfingRecoverInfoEntity.getUpdateTime() ? System.currentTimeMillis() : surfingRecoverInfoEntity.getUpdateTime().getTime();
		surfingRecoverInfo.setUpdateTime(new Date(updateTime));
		return surfingRecoverInfo;
	}

	@Override
	public Page<SurfingRecoverInfo> listSurfingRecoverInfo(short surfingRecoverState, int index, int length) {
		try {
			Page<SurfingRecoverInfoEntity> page = this.surfingReadDao.listSurfingRecoverInfo(surfingRecoverState, index, length);
			Function<SurfingRecoverInfoEntity, SurfingRecoverInfo> function = ((SurfingRecoverInfoEntity surfingRecoverInfoEntity) -> {
				return this.initSurfingRecoverInfo(surfingRecoverInfoEntity);
			});
			return this.initPageInfo(page, function);
		} catch (Exception e) {
			throw new FissionException(e);
		}
	}

	@Override
	public void updateSurfingRecoverState(int surfing, short surfingRecoverState) {
		try {
			this.surfingWriteDao.updateSurfingRecoverState(surfing, surfingRecoverState);
		} catch (Exception e) {
			throw new RollbackSourceException(e);
		}
	}
}
