package com.paojiao.user.data.db.entity;
import com.fission.datasource.annotation.ColumnAnnotation;
import com.fission.datasource.annotation.TableAnnotation;
import com.fission.datasource.bean.BaseEntity;

@TableAnnotation(dbName="fission_next_user",tableName="surfing_info")
public class SurfingInfoEntity implements BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String SURFING = "surfing";

	@ColumnAnnotation(length=19,columnName="create_time")
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName="surfing")
	private int surfing;

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

}