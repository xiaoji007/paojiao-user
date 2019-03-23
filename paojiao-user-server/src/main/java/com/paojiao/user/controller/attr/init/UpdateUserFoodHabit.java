package com.paojiao.user.controller.attr.init;

import com.fission.next.common.error.FissionException;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import com.fission.utils.tool.StringUtil;
import com.google.common.base.Joiner;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateUserFoodHabit extends UpdateUserAttr {
	public UpdateUserFoodHabit() {
		super(ConstUtil.UserAttrId.FOOD_HABIT);
	}

	@Override
	public Map<Short, Object> getUpdateUserAttr(int userId, String data) {
		if (StringUtil.isBlank(data)) {
			throw new FissionException("UpdateUserProfessional food habit is null");
		}
		List<Integer> foodHabits = StringUtil.strToIntList(data, ",");
		data = Joiner.on(",").join(foodHabits);
		Map<Short, Object> map = new HashMap<>();
		map.put(ConstUtil.UserAttrId.FOOD_HABIT, StringUtil.isBlank(data) ? "" : data);
		return map;
	}
}



