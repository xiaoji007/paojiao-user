package com.paojiao.user.data.db.entity ;
import com.fission.datasource.annotation.ColumnAnnotation ;
import com.fission.datasource.annotation.TableAnnotation ;
import lombok.Data ;

@TableAnnotation(dbName="paojiao_user",tableName="surfing_recover_info")
@Data
public class SurfingRecoverInfoEntity implements com.fission.datasource.bean.BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String UPDATE_TIME = "update_time";

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String SURFING = "surfing";

	public transient static final String SURFING_STATE = "surfing_state";

	@ColumnAnnotation(length=19,columnName=SurfingRecoverInfoEntity.UPDATE_TIME)
	private java.sql.Timestamp updateTime;

	@ColumnAnnotation(length=19,columnName=SurfingRecoverInfoEntity.CREATE_TIME)
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(isKey=true,length=10,columnName=SurfingRecoverInfoEntity.SURFING)
	private int surfing;

	@ColumnAnnotation(length=5,columnName=SurfingRecoverInfoEntity.SURFING_STATE)
	private short surfingState;

}