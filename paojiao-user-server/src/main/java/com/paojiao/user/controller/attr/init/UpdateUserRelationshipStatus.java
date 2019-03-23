package com.paojiao.user.controller.attr.init;

import com.fission.next.common.error.FissionException;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserRelationshipStatus extends UpdateUserAttr {

	public UpdateUserRelationshipStatus() {
		super(ConstUtil.UserAttrId.RELATIONSHIP_STATUS);
	}


	@Override
	public Map<Short, Object> getUpdateUserAttr(int userId, String data) {
		short val = Short.parseShort(data);
		if (ConstUtil.RelationshipStatusType.SINGLE != val && ConstUtil.RelationshipStatusType.LOVE != val && ConstUtil.RelationshipStatusType.MARRY != val && ConstUtil.RelationshipStatusType.ASK_ME != val) {
			//throw new FissionException("UpdateUserRelationshipStatus undefind");
		}
		Map<Short, Object> map = new HashMap<>();
		map.put(ConstUtil.UserAttrId.RELATIONSHIP_STATUS, val);
		return map;
	}

}



