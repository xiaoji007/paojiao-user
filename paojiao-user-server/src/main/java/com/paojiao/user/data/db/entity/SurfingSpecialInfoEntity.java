package com.paojiao.user.data.db.entity ;
import com.fission.datasource.annotation.ColumnAnnotation ;
import com.fission.datasource.annotation.TableAnnotation ;
import lombok.Data ;

@TableAnnotation(dbName="paojiao_user",tableName="surfing_special_info")
@Data
public class SurfingSpecialInfoEntity implements com.fission.datasource.bean.BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String TO_SURFING = "to_surfing";

	public transient static final String SURFING = "surfing";

	public transient static final String SURFING_STATE = "surfing_state";

	public transient static final String SURFING_ID = "surfing_id";

	@ColumnAnnotation(length=19,columnName=SurfingSpecialInfoEntity.CREATE_TIME)
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length=10,columnName=SurfingSpecialInfoEntity.TO_SURFING)
	private int toSurfing;

	@ColumnAnnotation(length=10,columnName=SurfingSpecialInfoEntity.SURFING)
	private int surfing;

	@ColumnAnnotation(length=5,columnName=SurfingSpecialInfoEntity.SURFING_STATE)
	private short surfingState;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName=SurfingSpecialInfoEntity.SURFING_ID)
	private int surfingId;

}