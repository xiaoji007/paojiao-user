package com.paojiao.user.data.db.entity;
import com.fission.datasource.annotation.ColumnAnnotation;
import com.fission.datasource.annotation.TableAnnotation;
import com.fission.datasource.bean.BaseEntity;

@TableAnnotation(dbName="paojiao_user",tableName="nick_name_ascii_info")
public class NickNameAsciiInfoEntity implements BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String END_ASCII = "end_ascii";

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String START_ASCII = "start_ascii";

	public transient static final String ASCII_TYPE = "ascii_type";

	public transient static final String ASCII_ID = "ascii_id";

	@ColumnAnnotation(length=10,columnName="end_ascii")
	private int endAscii;

	@ColumnAnnotation(length=19,columnName="create_time")
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length=10,columnName="start_ascii")
	private int startAscii;

	@ColumnAnnotation(length=5,columnName="ascii_type")
	private short asciiType;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName="ascii_id")
	private int asciiId;

	public void setEndAscii(int endAscii){
	  this.endAscii = endAscii;
	}

	public int getEndAscii(){
	  return this.endAscii;
	}

	public void setCreateTime(java.sql.Timestamp createTime){
	  this.createTime = createTime;
	}

	public java.sql.Timestamp getCreateTime(){
	  return this.createTime;
	}

	public void setStartAscii(int startAscii){
	  this.startAscii = startAscii;
	}

	public int getStartAscii(){
	  return this.startAscii;
	}

	public void setAsciiType(short asciiType){
	  this.asciiType = asciiType;
	}

	public short getAsciiType(){
	  return this.asciiType;
	}

	public void setAsciiId(int asciiId){
	  this.asciiId = asciiId;
	}

	public int getAsciiId(){
	  return this.asciiId;
	}

}