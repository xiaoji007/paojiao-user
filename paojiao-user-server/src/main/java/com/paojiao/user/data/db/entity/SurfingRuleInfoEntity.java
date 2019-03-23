package com.paojiao.user.data.db.entity;
import com.fission.datasource.annotation.ColumnAnnotation;
import com.fission.datasource.annotation.TableAnnotation;
import com.fission.datasource.bean.BaseEntity;;

@TableAnnotation(dbName="paojiao_user",tableName="surfing_rule_info")
public class SurfingRuleInfoEntity implements BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String SURFING_INCREMENT = "surfing_increment";

	public transient static final String SURFING_RULE = "surfing_rule";

	public transient static final String SURFING_NUM = "surfing_num";

	public transient static final String SURFING_RULE_ID = "surfing_rule_id";

	public transient static final String SURFING_RULE_DESC = "surfing_rule_desc";

	@ColumnAnnotation(length=19,columnName="create_time")
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length=10,columnName="surfing_increment")
	private int surfingIncrement;

	@ColumnAnnotation(length=255,columnName="surfing_rule")
	private String surfingRule;

	@ColumnAnnotation(length=10,columnName="surfing_num")
	private int surfingNum;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName="surfing_rule_id")
	private int surfingRuleId;

	@ColumnAnnotation(length=100,columnName="surfing_rule_desc")
	private String surfingRuleDesc;

	public void setCreateTime(java.sql.Timestamp createTime){
	  this.createTime = createTime;
	}

	public java.sql.Timestamp getCreateTime(){
	  return this.createTime;
	}

	public void setSurfingIncrement(int surfingIncrement){
	  this.surfingIncrement = surfingIncrement;
	}

	public int getSurfingIncrement(){
	  return this.surfingIncrement;
	}

	public void setSurfingRule(String surfingRule){
	  this.surfingRule = surfingRule;
	}

	public String getSurfingRule(){
	  return this.surfingRule;
	}

	public void setSurfingNum(int surfingNum){
	  this.surfingNum = surfingNum;
	}

	public int getSurfingNum(){
	  return this.surfingNum;
	}

	public void setSurfingRuleId(int surfingRuleId){
	  this.surfingRuleId = surfingRuleId;
	}

	public int getSurfingRuleId(){
	  return this.surfingRuleId;
	}

	public void setSurfingRuleDesc(String surfingRuleDesc){
	  this.surfingRuleDesc = surfingRuleDesc;
	}

	public String getSurfingRuleDesc(){
	  return this.surfingRuleDesc;
	}

}