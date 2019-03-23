package com.paojiao.user.data.db.entity;

import com.fission.datasource.annotation.ColumnAnnotation;
import com.fission.datasource.annotation.TableAnnotation;
import com.fission.datasource.bean.BaseEntity;

@TableAnnotation(dbName="paojiao_user",tableName="user_black_info")
public class UserBlackInfoEntity implements BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String BLACK_TYPE = "black_type";

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String USER_ID = "user_id";

	public transient static final String EXPIRE_TIME = "expire_time";

	public transient static final String USER_BLACK_ID = "user_black_id";

	public transient static final String SCENE_TYPE = "scene_type";

	@ColumnAnnotation(length=5,columnName="black_type")
	private short blackType;

	@ColumnAnnotation(length=19,columnName="create_time")
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length=10,columnName="user_id")
	private int userId;

	@ColumnAnnotation(length=19,columnName="expire_time")
	private java.sql.Timestamp expireTime;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName="user_black_id")
	private int userBlackId;

	@ColumnAnnotation(length=5,columnName="scene_type")
	private short sceneType;

	public void setBlackType(short blackType){
	  this.blackType = blackType;
	}

	public short getBlackType(){
	  return this.blackType;
	}

	public void setCreateTime(java.sql.Timestamp createTime){
	  this.createTime = createTime;
	}

	public java.sql.Timestamp getCreateTime(){
	  return this.createTime;
	}

	public void setUserId(int userId){
	  this.userId = userId;
	}

	public int getUserId(){
	  return this.userId;
	}

	public void setExpireTime(java.sql.Timestamp expireTime){
	  this.expireTime = expireTime;
	}

	public java.sql.Timestamp getExpireTime(){
	  return this.expireTime;
	}

	public void setUserBlackId(int userBlackId){
	  this.userBlackId = userBlackId;
	}

	public int getUserBlackId(){
	  return this.userBlackId;
	}

	public void setSceneType(short sceneType){
	  this.sceneType = sceneType;
	}

	public short getSceneType(){
	  return this.sceneType;
	}

}