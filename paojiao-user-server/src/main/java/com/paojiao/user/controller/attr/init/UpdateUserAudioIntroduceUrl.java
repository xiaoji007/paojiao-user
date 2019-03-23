package com.paojiao.user.controller.attr.init;

import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import com.fission.utils.tool.StringUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserAudioIntroduceUrl extends UpdateUserAttr {

	public UpdateUserAudioIntroduceUrl() {
		super(ConstUtil.UserAttrId.AUDIO_INTRODUCE_URL);
	}

	@Override
	public Map<Short, Object> getUpdateUserAttr(int userId, String data) {
		Map<Short, Object> map = new HashMap<>();
		map.put(ConstUtil.UserAttrId.AUDIO_INTRODUCE_URL, StringUtil.isBlank(data) ? "" : data);
		return map;
	}

}


