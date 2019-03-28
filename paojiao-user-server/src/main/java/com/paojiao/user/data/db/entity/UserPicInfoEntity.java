package com.paojiao.user.data.db.entity ;
import com.fission.datasource.annotation.ColumnAnnotation ;
import com.fission.datasource.annotation.TableAnnotation ;
import lombok.Data ;

@TableAnnotation(dbName="paojiao_user",tableName=UserPicInfoEntity.TABLE_NAME)
@Data
public class UserPicInfoEntity implements com.fission.datasource.bean.BaseEntity {

	public transient static final String TABLE_NAME = "user_pic_info";

	private transient static final long serialVersionUID = 1L;

	public transient static final String USER_PIC_URL = "user_pic_url";

	public transient static final String UPDATE_TIME = "update_time";

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String USER_ID = "user_id";

	public transient static final String OLD_USER_PIC_URL = "old_user_pic_url";

	public transient static final String VERIFY_STATE = "verify_state";

	public transient static final String INDEX = "index";

	public transient static final String USER_PIC_ID = "user_pic_id";

	@ColumnAnnotation(length=100,columnName=UserPicInfoEntity.USER_PIC_URL)
	private String userPicUrl;

	@ColumnAnnotation(length=19,columnName=UserPicInfoEntity.UPDATE_TIME)
	private java.sql.Timestamp updateTime;

	@ColumnAnnotation(length=19,columnName=UserPicInfoEntity.CREATE_TIME)
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(length=10,columnName=UserPicInfoEntity.USER_ID)
	private int userId;

	@ColumnAnnotation(length=100,columnName=UserPicInfoEntity.OLD_USER_PIC_URL)
	private String oldUserPicUrl;

	@ColumnAnnotation(length=5,columnName=UserPicInfoEntity.VERIFY_STATE)
	private short verifyState;

	@ColumnAnnotation(length=5,columnName=UserPicInfoEntity.INDEX)
	private short index;

	@ColumnAnnotation(length=10,isAuto=true,columnName=UserPicInfoEntity.USER_PIC_ID)
	private int userPicId;

}