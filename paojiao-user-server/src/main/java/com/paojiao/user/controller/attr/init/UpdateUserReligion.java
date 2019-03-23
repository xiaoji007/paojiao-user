package com.paojiao.user.controller.attr.init;

import com.fission.next.common.error.FissionException;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import com.fission.utils.tool.StringUtil;
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
//		if (StringUtil.isBlank(data)) {
//			throw new FissionException("UpdateUserReligion religion is null");
//		}
		int religion = Integer.parseInt(data);
		Map<Short, Object> map = new HashMap<>();
		map.put(ConstUtil.UserAttrId.RELIGION, religion);
		return map;
	}

}


