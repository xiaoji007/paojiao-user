package com.paojiao.user.data.db.entity ;
import com.fission.datasource.annotation.ColumnAnnotation ;
import com.fission.datasource.annotation.TableAnnotation ;
import lombok.Data ;

@TableAnnotation(dbName="paojiao_user",tableName="nick_name_ascii_info")
@Data
public class NickNameAsciiInfoEntity implements com.fission.datasource.bean.BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String END_ASCII = "end_ascii";

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String START_ASCII = "start_ascii";

	public transient static final String ASCII_TYPE = "ascii_type";

	public transient static final String ASCII_ID = "ascii_id";

	@ColumnAnnotation(length=10,columnName=NickNameAsciiInfoEntity.END_ASCII)
	private int endAscii;

	@ColumnAnnotation(length=19,columnName=NickNameAsciiInfoEntity.CREATE_TIME)
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length=10,columnName=NickNameAsciiInfoEntity.START_ASCII)
	private int startAscii;

	@ColumnAnnotation(length=5,columnName=NickNameAsciiInfoEntity.ASCII_TYPE)
	private short asciiType;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName=NickNameAsciiInfoEntity.ASCII_ID)
	private int asciiId;

}