package com.xxl.job.service.handler;

import com.paojiao.user.task.UserTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.spring.SpringUtil;
import org.springframework.context.ApplicationContext;

public class DemoGlueJobHandler extends IJobHandler {

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		ApplicationContext applicationContext = SpringUtil.me().getApplicationContext();
		UserTaskService userTaskService = applicationContext.getBean(UserTaskService.class);
		userTaskService.addInviteUser(5251016);
//		ESUserDaoImpl esUserDao = applicationContext.getBean(ESUserDaoImpl.class);
//		List<ESUserInviteInfoEntity> esUserInviteInfoEntitys = esUserDao.listUserInviteInfo(5251016);
//		for (ESUserInviteInfoEntity esUserInviteInfoEntity : esUserInviteInfoEntitys) {
//			boolean bUpdate = esUserDao.manageUserInviteState(esUserInviteInfoEntity);
//			XxlJobLogger.log("esUserInviteInfoEntity=" + esUserInviteInfoEntity.getId() + " ，bUpdate=" + bUpdate);
//			Consumer<BoolQueryBuilder> queryConsumer = new Consumer<BoolQueryBuilder>() {
//				@Override
//				public void accept(BoolQueryBuilder boolQueryBuilder) {
//
//					boolQueryBuilder.must(QueryBuilders.termQuery(ESUserInviteInfoEntity.ID, esUserInviteInfoEntity.getId()));
////					boolQueryBuilder.must(QueryBuilders.termQuery(ESUserInviteInfoEntity.INVITE, true));
////					boolQueryBuilder.must(QueryBuilders.termQuery(ESUserInviteInfoEntity.INVITE_STATE, ConstUtil.InviteState.INIT));
//				}
//			};
//			try {
//				Method method = ReflectionUtils.findMethod(ESUserDaoImpl.class, "getESEntity", String.class, String.class, Class.class, Consumer.class, Consumer.class);
//				method.setAccessible(true);
//				ESUserInviteInfoEntity esUserInviteInfoEntity2 = (ESUserInviteInfoEntity) ReflectionUtils.invokeJdbcMethod(method, esUserDao, ESUserInviteInfoEntity.INDEX_NAME, ESUserInviteInfoEntity.TYPE_NAME, ESUserInviteInfoEntity.class, queryConsumer, null);
//				XxlJobLogger.log("esUserInviteInfoEntity2=" + esUserInviteInfoEntity2);
//				XxlJobLogger.log(queryConsumer.toString());
//			} catch (Exception e) {
//				XxlJobLogger.log(" inviteUserTask error." + e.toString());
//			}
//		}
		return ReturnT.SUCCESS;
	}

}