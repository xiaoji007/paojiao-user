package com.paojiao.user.data.db.entity;

import com.fission.datasource.annotation.ColumnAnnotation;
import com.fission.datasource.annotation.TableAnnotation;
import com.fission.datasource.bean.BaseEntity;

@TableAnnotation(dbName="paojiao_user",tableName="surfing_special_info")
public class SurfingSpecialInfoEntity implements BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String TO_SURFING = "to_surfing";

	public transient static final String SURFING = "surfing";

	public transient static final String SURFING_STATE = "surfing_state";

	public transient static final String SURFING_ID = "surfing_id";

	@ColumnAnnotation(length=19,columnName="create_time")
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length=10,columnName="to_surfing")
	private int toSurfing;

	@ColumnAnnotation(length=10,columnName="surfing")
	private int surfing;

	@ColumnAnnotation(length=5,columnName="surfing_state")
	private short surfingState;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName="surfing_id")
	private int surfingId;

	public void setCreateTime(java.sql.Timestamp createTime){
	  this.createTime = createTime;
	}

	public java.sql.Timestamp getCreateTime(){
	  return this.createTime;
	}

	public void setToSurfing(int toSurfing){
	  this.toSurfing = toSurfing;
	}

	public int getToSurfing(){
	  return this.toSurfing;
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

	public void setSurfingId(int surfingId){
	  this.surfingId = surfingId;
	}

	public int getSurfingId(){
	  return this.surfingId;
	}

}