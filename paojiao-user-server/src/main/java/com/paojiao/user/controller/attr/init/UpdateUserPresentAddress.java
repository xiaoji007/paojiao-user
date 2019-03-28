package com.paojiao.user.controller.attr.init;

import com.fission.utils.tool.StringUtil;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserPresentAddress extends UpdateUserAttr {

    public UpdateUserPresentAddress() {
        super(ConstUtil.UserAttrId.PRESENT_ADDRESS);
    }

    @Override
    public Map<Short, Object> getUpdateUserAttr(int userId, String data) {
        if (StringUtil.isBlank(data)) {
            data = "";
        }
        Map<Short, Object> map = new HashMap<>();
        map.put(ConstUtil.UserAttrId.PRESENT_ADDRESS, data);
        return map;
    }

}



