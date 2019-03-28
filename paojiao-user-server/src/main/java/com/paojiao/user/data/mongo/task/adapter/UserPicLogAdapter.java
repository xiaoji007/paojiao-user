package com.paojiao.user.data.mongo.task.adapter;

import com.fission.task.adapter.BaseDataAdapter;
import com.fission.utils.tool.ArrayUtils;
import com.fission.utils.tool.JsonUtil;
import com.paojiao.user.data.mongo.IMGUserLogDao;
import com.paojiao.user.data.mongo.entity.MGUserPicLogInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class UserPicLogAdapter extends BaseDataAdapter<MGUserPicLogInfoEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPicLogAdapter.class);

    @Inject
    private IMGUserLogDao userLogDao;

    public UserPicLogAdapter() {
        super(MGUserPicLogInfoEntity.class);
    }

    @Override
    public void execute(List<MGUserPicLogInfoEntity> list) throws Exception {
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