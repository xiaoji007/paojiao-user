package com.paojiao.user.data.db;

import com.paojiao.user.data.db.entity.NickNameAsciiInfoEntity;
import com.paojiao.user.data.db.entity.NickNameKeywordInfoEntity;

import java.sql.SQLException;
import java.util.List;

public interface IKeywordReadDao {

	NickNameKeywordInfoEntity getNickNameKeyworldInfo(int nickNameKeyworldId) throws SQLException;

	NickNameKeywordInfoEntity getNickNameKeyworldInfo(String nickNameKeyworld, short ruleType) throws SQLException;

	List<NickNameKeywordInfoEntity> listAllNickNameKeywordInfo() throws SQLException;

	NickNameAsciiInfoEntity getNickNameAsciiInfo(int nickNameAsciiId) throws SQLException;

	List<NickNameAsciiInfoEntity> listAllNickNameAsciiInfo() throws SQLException;
}
