package com.paojiao.user.controller.attr.init;

import com.alibaba.fastjson.JSONObject;
import com.fission.next.common.error.FissionException;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import com.paojiao.user.controller.util.ParamNameUtil;
import com.fission.utils.tool.JsonUtil;
import com.fission.utils.tool.StringUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserGis extends UpdateUserAttr {

	public UpdateUserGis() {
		super(ConstUtil.UserAttrId.GIS);
	}

	@Override
	public Map<Short, Object> getUpdateUserAttr(int userId, java.lang.String data) {
		if (StringUtil.isBlank(data)) {
			throw new FissionException("UpdateUserGis gis is null");
		}
		JSONObject json = JsonUtil.stringToJSONObject(data);
		String gisx = json.getString(ParamNameUtil.GIS_X);
		String gisy = json.getString(ParamNameUtil.GIS_Y);
		String gis = "";
		if (StringUtil.isNotBlank(gisx) && StringUtil.isNotBlank(gisy)) {
			gis = gisx + "," + gisy;
		}
		Map<Short, Object> map = new HashMap<>();
		map.put(ConstUtil.UserAttrId.GIS, gis);
		return map;
	}

}



