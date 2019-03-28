package com.paojiao.user.data.mongo.entity;

import com.fission.task.bean.TaskBaseBean;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = MGUserPicLogInfoEntity.TABLE_NAME)
public class MGUserPicLogInfoEntity implements TaskBaseBean {

    public transient static final String TABLE_NAME = "user_pic_log_info";
    private transient static final long serialVersionUID = 1L;
    @Id
    private String id;
    private int userId;
    private String pic;
    private int index;
    private Date createTime;

}