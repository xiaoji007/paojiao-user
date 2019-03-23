package com.paojiao.user.data.mongo.task;

import com.fission.task.adapter.IDataAdapter;
import com.fission.task.bean.TaskBaseBean;
import com.fission.task.util.DataAdapterUtil;
import com.fission.task.util.TaskDataUtil;
import com.fission.utils.tool.JsonUtil;
import com.paojiao.user.data.mongo.entity.UserPicLogInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMongoTaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserMongoTaskService.class);

    @Scheduled(cron = "0/2 * * * * ?")
    private void userPicLogAdapterTask() {
        this.execute(UserPicLogInfoEntity.class);
    }

    private <T extends TaskBaseBean> void execute(Class<T> clazz) {
        if (null == clazz) {
            return;
        }
        List<T> list = TaskDataUtil.clearData(clazz);
        if (null != list && !list.isEmpty()) {
            this.execute(list, clazz);
        }
    }

    private <T> void execute(List<T> list, Class<T> clazz) {
        if (null == list || list.isEmpty()) {
            return;
        }
        List<IDataAdapter<T>> adapters = DataAdapterUtil.getDataAdapter(clazz);
        if (null == adapters || adapters.isEmpty()) {
            return;
        }
        adapters.parallelStream().forEach((IDataAdapter<T> adapter) -> {
            if (null != adapter) {
                try {
                    adapter.execute(list);
                } catch (Throwable e) {
                    UserMongoTaskService.LOGGER.error("adapter forEach error.param(list:" + JsonUtil.objToJsonString(list) + ")", e);
                }
            }
        });
    }
}
