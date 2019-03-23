package com.paojiao.user.controller.attr.init;

import com.fission.next.common.error.FissionException;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import com.fission.utils.tool.StringUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserSex extends UpdateUserAttr {

	public UpdateUserSex() {
		super(ConstUtil.UserAttrId.SEX);
	}

	@Override
	public Map<Short, Object> getUpdateUserAttr(int userId, String data) {
		if (StringUtil.isBlank(data)) {
			throw new FissionException("UpdateUserSex sex is null");
		}
		short sex = Short.parseShort(data.toString());
		if (!(ConstUtil.Sex.MAN == sex || ConstUtil.Sex.WOMAN == sex)) {
			throw new FissionException("UpdateUserSex sex undefind");
		}
		Map<Short, Object> map = new HashMap<>();
		map.put(ConstUtil.UserAttrId.SEX, sex);
		return map;
	}

}


