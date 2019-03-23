package com.paojiao.user.controller.attr.init;

import com.fission.next.common.error.FissionException;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import com.fission.utils.tool.StringUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserHeadPic extends UpdateUserAttr {

	public UpdateUserHeadPic() {
		super(ConstUtil.UserAttrId.HEAD_PIC);
	}


	@Override
	public Map<Short, Object> getUpdateUserAttr(int userId,String data) {
		if (StringUtil.isBlank(data)) {
			throw new FissionException("UpdateUserHeadPic head pic is null");
		}
		Map<Short, Object> map = new HashMap<>();
		map.put(ConstUtil.UserAttrId.HEAD_PIC, data);
		return map;
	}

}



