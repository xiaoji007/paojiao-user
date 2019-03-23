package com.paojiao.user.data.db.entity;
import com.fission.datasource.annotation.ColumnAnnotation;
import com.fission.datasource.annotation.TableAnnotation;
import com.fission.datasource.bean.BaseEntity;

@TableAnnotation(dbName="paojiao_user",tableName="user_invite_info")
public class UserInviteInfoEntity implements BaseEntity {


	private transient static final long serialVersionUID = 1L;

	public transient static final String SHARE_TYPE = "share_type";

	public transient static final String CREATE_TIME = "create_time";

	public transient static final String USER_INVITE_ID = "user_invite_id";

	public transient static final String USER_ID = "user_id";

	public transient static final String INVITE_CODE = "invite_code";

	public transient static final String INVITE_NUM = "invite_num";

	@ColumnAnnotation(length=10,columnName="share_type")
	private int shareType;

	@ColumnAnnotation(length=19,columnName="create_time")
	private java.sql.Timestamp createTime;

	@ColumnAnnotation(isKey=true,length=10,isAuto=true,columnName="user_invite_id")
	private int userInviteId;

	@ColumnAnnotation(length=10,columnName="user_id")
	private int userId;

	@ColumnAnnotation(length=128,columnName="invite_code")
	private String inviteCode;

	@ColumnAnnotation(length=10,columnName="invite_num")
	private int inviteNum;

	public void setShareType(int shareType){
	  this.shareType = shareType;
	}

	public int getShareType(){
	  return this.shareType;
	}

	public void setCreateTime(java.sql.Timestamp createTime){
	  this.createTime = createTime;
	}

	public java.sql.Timestamp getCreateTime(){
	  return this.createTime;
	}

	public void setUserInviteId(int userInviteId){
	  this.userInviteId = userInviteId;
	}

	public int getUserInviteId(){
	  return this.userInviteId;
	}

	public void setUserId(int userId){
	  this.userId = userId;
	}

	public int getUserId(){
	  return this.userId;
	}

	public void setInviteCode(String inviteCode){
	  this.inviteCode = inviteCode;
	}

	public String getInviteCode(){
	  return this.inviteCode;
	}

	public void setInviteNum(int inviteNum){
	  this.inviteNum = inviteNum;
	}

	public int getInviteNum(){
	  return this.inviteNum;
	}

}