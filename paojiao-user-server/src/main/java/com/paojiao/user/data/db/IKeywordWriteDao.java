package com.paojiao.user.data.db;

import com.paojiao.user.data.db.entity.*;

import java.sql.SQLException;

public interface IKeywordWriteDao {

	int addNickNameKeyworldInfo(NickNameKeywordInfoEntity nickNameKeywordInfoEntity) throws SQLException;

	void removeNickNameKeyworldInfo(int nickNameKeyworldId) throws SQLException;

	int addNickNameAsciiInfoEntity(NickNameAsciiInfoEntity nickNameAsciiInfoEntity) throws SQLException;

	void removeNickNameAsciiInfo(int nickNameAsciiId) throws SQLException;
}
