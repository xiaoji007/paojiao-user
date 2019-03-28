package com.paojiao.user.controller.attr.init;

import com.fission.next.common.error.FissionCodeException;
import com.fission.next.common.error.FissionException;
import com.fission.utils.tool.StringUtil;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.api.util.UserErrorCode;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserBirthday extends UpdateUserAttr {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public UpdateUserBirthday() {
        super(ConstUtil.UserAttrId.BIRTHDAY);
    }

    @Override
    public Map<Short, Object> getUpdateUserAttr(int userId, String data) {
        Map<Short, Object> map = new HashMap<>();
        Date birthday = null;
        if (StringUtil.isBlank(data)) {
//            throw new FissionCodeException(UserErrorCode.BIRTHDAY_ERROR);
        } else {
            try {
                birthday = UpdateUserBirthday.FORMAT.parse(data);
                return map;
            } catch (Exception e) {
                throw new FissionException(e);
            }
        }
        map.put(ConstUtil.UserAttrId.BIRTHDAY, birthday);
        return map;
    }

}



