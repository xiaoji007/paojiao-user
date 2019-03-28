package com.paojiao.user.data.db.entity ;
import com.fission.datasource.annotation.ColumnAnnotation ;
import com.fission.datasource.annotation.TableAnnotation ;
import lombok.Data ;

@TableAnnotation(dbName="paojiao_user",tableName="surfing_info")
@Data
public class SurfingInfoEntity implements com.fission.datasource.bean.BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String SURFING = "surfing";

	@ColumnAnnotation(length=19,columnName=SurfingInfoEntity.CREATE_TIME)
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName=SurfingInfoEntity.SURFING)
	private int surfing;

}