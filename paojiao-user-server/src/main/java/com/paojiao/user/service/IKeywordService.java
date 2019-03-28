package com.paojiao.user.service;

import com.paojiao.user.service.bean.NickNameAsciiInfo;
import com.paojiao.user.service.bean.NickNameKeyworldInfo;

import java.util.List;

public interface IKeywordService {

	List<NickNameKeyworldInfo> listAllNickNameKeyworldInfo();

	/**
	 * 刷新昵称关键字信息
	 *
	 * @return
	 */
	void refreshNickNameKeyworldInfo();

	List<NickNameAsciiInfo> listAllNickNameAsciiInfo();

	/**
	 * 刷新昵称关Ascii信息
	 *
	 * @return
	 */
	void refreshNickNameAsciiInfo();
}
