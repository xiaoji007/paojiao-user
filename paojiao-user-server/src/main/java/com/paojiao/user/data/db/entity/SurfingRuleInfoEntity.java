package com.paojiao.user.data.db.entity ;
import com.fission.datasource.annotation.ColumnAnnotation ;
import com.fission.datasource.annotation.TableAnnotation ;
import lombok.Data ;

@TableAnnotation(dbName="paojiao_user",tableName="surfing_rule_info")
@Data
public class SurfingRuleInfoEntity implements com.fission.datasource.bean.BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String SURFING_INCREMENT = "surfing_increment";

	public transient static final String SURFING_RULE = "surfing_rule";

	public transient static final String SURFING_NUM = "surfing_num";

	public transient static final String SURFING_RULE_ID = "surfing_rule_id";

	public transient static final String SURFING_RULE_DESC = "surfing_rule_desc";

	@ColumnAnnotation(length=19,columnName=SurfingRuleInfoEntity.CREATE_TIME)
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length=10,columnName=SurfingRuleInfoEntity.SURFING_INCREMENT)
	private int surfingIncrement;

	@ColumnAnnotation(length=255,columnName=SurfingRuleInfoEntity.SURFING_RULE)
	private String surfingRule;

	@ColumnAnnotation(length=10,columnName=SurfingRuleInfoEntity.SURFING_NUM)
	private int surfingNum;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName=SurfingRuleInfoEntity.SURFING_RULE_ID)
	private int surfingRuleId;

	@ColumnAnnotation(length=100,columnName=SurfingRuleInfoEntity.SURFING_RULE_DESC)
	private String surfingRuleDesc;

}