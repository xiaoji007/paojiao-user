package com.paojiao.user.data.db;

import com.paojiao.user.data.db.entity.NickNameAsciiInfoEntity;
import com.paojiao.user.data.db.entity.NickNameKeyworldInfoEntity;

import java.sql.SQLException;
import java.util.List;

public interface IKeyworldReadDao {

	NickNameKeyworldInfoEntity getNickNameKeyworldInfo(int nickNameKeyworldId) throws SQLException;

	NickNameKeyworldInfoEntity getNickNameKeyworldInfo(String nickNameKeyworld,short ruleType) throws SQLException;

	List<NickNameKeyworldInfoEntity> listAllNickNameKeywordInfo() throws SQLException;

	NickNameAsciiInfoEntity getNickNameAsciiInfo(int nickNameAsciiId) throws SQLException;

	List<NickNameAsciiInfoEntity> listAllNickNameAsciiInfo() throws SQLException;
}
