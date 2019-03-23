package com.paojiao.user.data.db.entity;

import com.fission.datasource.annotation.ColumnAnnotation;
import com.fission.datasource.annotation.TableAnnotation;
import com.fission.datasource.bean.BaseEntity;

import java.sql.Timestamp;

@TableAnnotation(dbName = "paojiao_user", tableName = "user_attr_info")
public class UserAttrInfoEntity implements BaseEntity {


	public transient static final String CREATE_TIME = "create_time";
	public transient static final String LAST_UPDATE_TIME = "last_update_time";
	public transient static final String USER_ID = "user_id";
	public transient static final String USER_ATTR_ID = "user_attr_id";
	public transient static final String USER_ATTR_TYPE = "user_attr_type";
	public transient static final String USER_ATTR = "user_attr";
	private transient static final long serialVersionUID = 1L;

	@ColumnAnnotation(length = 19, columnName = UserAttrInfoEntity.CREATE_TIME)
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length = 19, columnName = UserAttrInfoEntity.LAST_UPDATE_TIME)
	private java.sql.Timestamp lastUpdateTime;

	@ColumnAnnotation(length = 100, columnName = UserAttrInfoEntity.USER_ATTR)
	private String userAttr;

	@ColumnAnnotation(length = 5, columnName = UserAttrInfoEntity.USER_ATTR_TYPE)
	private short userAttrType;

	@ColumnAnnotation(length = 10, columnName = UserAttrInfoEntity.USER_ID)
	private int userId;

	@ColumnAnnotation(isKey = true, length = 10, isAuto = true, columnName = UserAttrInfoEntity.USER_ATTR_ID)
	private int userAttrId;


	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getUserAttr() {
		return userAttr;
	}

	public void setUserAttr(String userAttr) {
		this.userAttr = userAttr;
	}

	public short getUserAttrType() {
		return userAttrType;
	}

	public void setUserAttrType(short userAttrType) {
		this.userAttrType = userAttrType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserAttrId() {
		return userAttrId;
	}

	public void setUserAttrId(int userAttrId) {
		this.userAttrId = userAttrId;
	}
}