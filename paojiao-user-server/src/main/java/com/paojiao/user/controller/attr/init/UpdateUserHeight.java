package com.paojiao.user.controller.attr.init;

import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import com.fission.utils.tool.StringUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserHeight extends UpdateUserAttr {
	public UpdateUserHeight() {
		super(ConstUtil.UserAttrId.HEIGHT);
	}

	@Override
	public Map<Short, Object> getUpdateUserAttr(int userId, String data) {
		Map<Short, Object> map = new HashMap<>();
		int height = Integer.parseInt(data);
		if (height <= 0) {
			data = "";
		}
		map.put(ConstUtil.UserAttrId.HEIGHT, StringUtil.isBlank(data) ? "" : data);
		return map;
	}
}



