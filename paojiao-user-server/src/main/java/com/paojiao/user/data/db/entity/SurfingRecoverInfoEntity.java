package com.paojiao.user.data.db.entity;
import com.fission.datasource.annotation.ColumnAnnotation;
import com.fission.datasource.annotation.TableAnnotation;
import com.fission.datasource.bean.BaseEntity;

@TableAnnotation(dbName="paojiao_user",tableName="surfing_recover_info")
public class SurfingRecoverInfoEntity implements BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String UPDATE_TIME = "update_time";

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String SURFING = "surfing";

	public transient static final String SURFING_STATE = "surfing_state";

	@ColumnAnnotation(length=19,columnName="update_time")
	private java.sql.Timestamp updateTime;

	@ColumnAnnotation(length=19,columnName="create_time")
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(isKey=true,length=10,columnName="surfing")
	private int surfing;

	@ColumnAnnotation(length=5,columnName="surfing_state")
	private short surfingState;

	public void setUpdateTime(java.sql.Timestamp updateTime){
	  this.updateTime = updateTime;
	}

	public java.sql.Timestamp getUpdateTime(){
	  return this.updateTime;
	}

	public void setCreateTime(java.sql.Timestamp createTime){
	  this.createTime = createTime;
	}

	public java.sql.Timestamp getCreateTime(){
	  return this.createTime;
	}

	public void setSurfing(int surfing){
	  this.surfing = surfing;
	}

	public int getSurfing(){
	  return this.surfing;
	}

	public void setSurfingState(short surfingState){
	  this.surfingState = surfingState;
	}

	public short getSurfingState(){
	  return this.surfingState;
	}

}