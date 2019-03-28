package com.paojiao.user.data.db.entity ;
import com.fission.datasource.annotation.ColumnAnnotation ;
import com.fission.datasource.annotation.TableAnnotation ;
import lombok.Data ;

@TableAnnotation(dbName="paojiao_user",tableName="user_attr_info")
@Data
public class UserAttrInfoEntity implements com.fission.datasource.bean.BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String USER_ATTR = "user_attr";

	public transient static final String LAST_UPDATE_TIME = "last_update_time";

	public transient static final String USER_ATTR_ID = "user_attr_id";

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String USER_ID = "user_id";

	public transient static final String USER_ATTR_TYPE = "user_attr_type";

	@ColumnAnnotation(length=255,columnName=UserAttrInfoEntity.USER_ATTR)
	private String userAttr;

	@ColumnAnnotation(length=19,columnName=UserAttrInfoEntity.LAST_UPDATE_TIME)
	private java.sql.Timestamp lastUpdateTime;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName=UserAttrInfoEntity.USER_ATTR_ID)
	private int userAttrId;

	@ColumnAnnotation(length=19,columnName=UserAttrInfoEntity.CREATE_TIME)
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length=10,columnName=UserAttrInfoEntity.USER_ID)
	private int userId;

	@ColumnAnnotation(length=5,columnName=UserAttrInfoEntity.USER_ATTR_TYPE)
	private short userAttrType;

}