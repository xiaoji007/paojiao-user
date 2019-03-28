package com.paojiao.user.controller.attr.init;

import com.fission.utils.tool.StringUtil;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserReligion extends UpdateUserAttr {

	public UpdateUserReligion() {
		super(ConstUtil.UserAttrId.RELIGION);
	}

	@Override
	public Map<Short, Object> getUpdateUserAttr(int userId, String data) {
		if (StringUtil.isBlank(data)) {
			data="";
		}
		int religion = Integer.parseInt(data);
		Map<Short, Object> map = new HashMap<>();
		map.put(ConstUtil.UserAttrId.RELIGION, religion);
		return map;
	}

}


