package com.paojiao.user.controller.attr.init;

import com.fission.next.common.constant.Language;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserLanguage extends UpdateUserAttr {

	public UpdateUserLanguage() {
		super(ConstUtil.UserAttrId.LANGUAGE_TYPE);
	}

	@Override
	public Map<Short, Object> getUpdateUserAttr(int userId,String data) {
		int languageType = Integer.parseInt(data.toString());
		Map<Short, Object> map = new HashMap<>();
		map.put(ConstUtil.UserAttrId.LANGUAGE_TYPE, languageType);
		map.put(ConstUtil.UserAttrId.REGION, (languageType / Language.BASE_REGION) * Language.BASE_REGION);
		return map;
	}

}


