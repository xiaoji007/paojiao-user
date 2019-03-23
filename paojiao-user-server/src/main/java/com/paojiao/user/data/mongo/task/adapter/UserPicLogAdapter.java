package com.paojiao.user.data.mongo.task.adapter;

import com.fission.task.adapter.BaseDataAdapter;
import com.fission.utils.tool.ArrayUtils;
import com.fission.utils.tool.JsonUtil;
import com.paojiao.user.data.mongo.IUserLogDao;
import com.paojiao.user.data.mongo.entity.UserPicLogInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class UserPicLogAdapter extends BaseDataAdapter<UserPicLogInfoEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPicLogAdapter.class);

    @Inject
    private IUserLogDao userLogDao;

    public UserPicLogAdapter() {
        super(UserPicLogInfoEntity.class);
    }

    @Override
    public void execute(List<UserPicLogInfoEntity> list) throws Exception {
        try {
            if (ArrayUtils.isNullOrEmpty(list)) {
                return;
            }
            this.userLogDao.addUserPicLogInfo(list);
        } catch (Exception e) {
            String message = String.format("add UserInviteLogInfoEntity error.param(list:%s)", JsonUtil.objToJsonString(list));
            UserPicLogAdapter.LOGGER.error(message, e);
        }
    }
}