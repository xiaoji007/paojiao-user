package com.paojiao.user.controller.attr.init;

import com.fission.next.common.error.FissionException;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserObjective extends UpdateUserAttr {

	public UpdateUserObjective() {
		super(ConstUtil.UserAttrId.OBJECTIVE);
	}

	@Override
	public Map<Short, Object> getUpdateUserAttr(int userId, String data) {
		short val = Short.parseShort(data);
//		if (ConstUtil.ObjectiveType.CHAT != val && ConstUtil.ObjectiveType.DATE != val && ConstUtil.ObjectiveType.FRIEND != val) {
//			throw new FissionException("UpdateUserObjective objecttive is null");
//		}
		Map<Short, Object> map = new HashMap<>();
		map.put(ConstUtil.UserAttrId.OBJECTIVE, val);
		return map;
	}

}



