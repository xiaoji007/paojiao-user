package com.paojiao.user.data.db;

import com.paojiao.user.data.db.entity.*;

import java.sql.SQLException;

public interface IKeyworldWriteDao {

	int addNickNameKeyworldInfo(NickNameKeyworldInfoEntity nickNameKeyworldInfoEntity) throws SQLException;

	void removeNickNameKeyworldInfo(int nickNameKeyworldId) throws SQLException;

	int addNickNameAsciiInfoEntity(NickNameAsciiInfoEntity nickNameAsciiInfoEntity) throws SQLException;

	void removeNickNameAsciiInfo(int nickNameAsciiId) throws SQLException;
}
