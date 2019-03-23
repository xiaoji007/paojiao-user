package com.paojiao.user.data.db.entity;

import com.fission.datasource.annotation.ColumnAnnotation;
import com.fission.datasource.annotation.TableAnnotation;
import com.fission.datasource.bean.BaseEntity;

@TableAnnotation(dbName="paojiao_user",tableName="nick_name_keyworld_info")
public class NickNameKeyworldInfoEntity implements BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String RULE_TYPE = "rule_type";

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String KEYWORLD_ID = "keyworld_id";

	public transient static final String KEYWORLD = "keyworld";

	@ColumnAnnotation(length=5,columnName="rule_type")
	private short ruleType;

	@ColumnAnnotation(length=19,columnName="create_time")
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName="keyworld_id")
	private int keyworldId;

	@ColumnAnnotation(length=255,columnName="keyworld")
	private String keyworld;

	public void setRuleType(short ruleType){
	  this.ruleType = ruleType;
	}

	public short getRuleType(){
	  return this.ruleType;
	}

	public void setCreateTime(java.sql.Timestamp createTime){
	  this.createTime = createTime;
	}

	public java.sql.Timestamp getCreateTime(){
	  return this.createTime;
	}

	public void setKeyworldId(int keyworldId){
	  this.keyworldId = keyworldId;
	}

	public int getKeyworldId(){
	  return this.keyworldId;
	}

	public void setKeyworld(String keyworld){
	  this.keyworld = keyworld;
	}

	public String getKeyworld(){
	  return this.keyworld;
	}

}