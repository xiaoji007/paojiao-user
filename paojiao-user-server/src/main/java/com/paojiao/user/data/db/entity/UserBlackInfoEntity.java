package com.paojiao.user.data.db.entity ;
import com.fission.datasource.annotation.ColumnAnnotation ;
import com.fission.datasource.annotation.TableAnnotation ;
import lombok.Data ;

@TableAnnotation(dbName="paojiao_user",tableName="user_black_info")
@Data
public class UserBlackInfoEntity implements com.fission.datasource.bean.BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String BLACK_TYPE = "black_type";

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String USER_ID = "user_id";

	public transient static final String EXPIRE_TIME = "expire_time";

	public transient static final String USER_BLACK_ID = "user_black_id";

	public transient static final String SCENE_TYPE = "scene_type";

	@ColumnAnnotation(length=5,columnName=UserBlackInfoEntity.BLACK_TYPE)
	private short blackType;

	@ColumnAnnotation(length=19,columnName=UserBlackInfoEntity.CREATE_TIME)
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length=10,columnName=UserBlackInfoEntity.USER_ID)
	private int userId;

	@ColumnAnnotation(length=19,columnName=UserBlackInfoEntity.EXPIRE_TIME)
	private java.sql.Timestamp expireTime;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName=UserBlackInfoEntity.USER_BLACK_ID)
	private int userBlackId;

	@ColumnAnnotation(length=5,columnName=UserBlackInfoEntity.SCENE_TYPE)
	private short sceneType;

}