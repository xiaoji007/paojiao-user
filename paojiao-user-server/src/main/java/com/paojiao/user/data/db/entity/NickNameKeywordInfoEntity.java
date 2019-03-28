package com.paojiao.user.data.db.entity ;
import com.fission.datasource.annotation.ColumnAnnotation ;
import com.fission.datasource.annotation.TableAnnotation ;
import lombok.Data ;

@TableAnnotation(dbName="paojiao_user",tableName="nick_name_keyword_info")
@Data
public class NickNameKeywordInfoEntity implements com.fission.datasource.bean.BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String RULE_TYPE = "rule_type";

	public transient static final String KEYWORD_ID = "keyword_id";

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String KEYWORD = "keyword";

	@ColumnAnnotation(length=5,columnName=NickNameKeywordInfoEntity.RULE_TYPE)
	private short ruleType;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName=NickNameKeywordInfoEntity.KEYWORD_ID)
	private int keywordId;

	@ColumnAnnotation(length=19,columnName=NickNameKeywordInfoEntity.CREATE_TIME)
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length=255,columnName=NickNameKeywordInfoEntity.KEYWORD)
	private String keyword;

}