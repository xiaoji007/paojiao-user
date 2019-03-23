package com.paojiao.user.controller.attr;

import com.alibaba.fastjson.JSONObject;
import com.fission.next.common.bean.ClientContext;
import com.fission.next.common.constant.Language;
import com.fission.next.common.error.FissionException;
import com.fission.next.utils.ErrorCode;
import com.fission.utils.tool.ArrayUtils;
import com.fission.utils.tool.JsonUtil;
import com.paojiao.user.api.services.IUserService;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.config.ApplicationConfig;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserService {

    @Inject
    private ApplicationConfig applicationConfig;
    @Inject
    private IUserService userHandler;

    public void updateUserAttr(int userId, String userAttr, boolean init, ClientContext clientContext) {
        Map<Short, Object> data = this.initUserAttr(userId, userAttr);
        data.remove(ConstUtil.UserAttrId.HEAD_PIC);
        data.remove(ConstUtil.UserAttrId.USER_TYPE);
        data.remove(ConstUtil.UserAttrId.SURFING);
        if (init) {
//			if (!data.containsKey(ConstUtil.UserAttrId.NICK_NAME)) {
//				throw new NickNameNullException();
//			}

//			if (!data.containsKey(ConstUtil.UserAttrId.SEX)) {
//				throw new SexException();
//			}
//
//			if (!data.containsKey(ConstUtil.UserAttrId.BIRTHDAY)) {
//				throw new BirthdayException();
//			}
            Integer language = (Integer) data.get(ConstUtil.UserAttrId.LANGUAGE_TYPE);
            if (null == language) {
                language = Language.UNDEFIND;
                data.put(ConstUtil.UserAttrId.LANGUAGE_TYPE, language);
                data.put(ConstUtil.UserAttrId.REGION, (language / Language.BASE_REGION) * Language.BASE_REGION);
            }
//            data.put(ConstUtil.UserAttrId.HEAD_PIC, applicationConfig.getPicBySex(ConstUtil.UserAttrId.SEX));
            data.put(ConstUtil.UserAttrId.USER_TYPE, ConstUtil.UserType.COMMON);
            data.put(ConstUtil.UserAttrId.OBJECTIVE, ConstUtil.ObjectiveType.CHAT);
        } else {
            data.remove(ConstUtil.UserAttrId.SEX);
        }
        if (data.isEmpty()) {
            return;
        }
        if (ErrorCode.SUCCESS != this.userHandler.updateUserAttr(userId, data, clientContext).getCode()) {
            throw new FissionException();
        }
    }

    private Map<Short, Object> initUserAttr(int userId, String userAttr) {
        if (null == userAttr || userAttr.isEmpty()) {
            throw new FissionException("initUserAttr userAttr is null");
        }
        JSONObject json = JsonUtil.stringToJSONObject(userAttr);
        Map<Short, Object> data = new HashMap<>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            UpdateUserAttr updateUserAttr = UpdateUserAttr.getUpdateUserAttr(Short.parseShort(entry.getKey().toString()));
            if (null == updateUserAttr) {
                continue;
            }
            Map<Short, Object> updateUserAttrMap = updateUserAttr.getUpdateUserAttr(userId, (String) entry.getValue());
            if (ArrayUtils.isNotEmpty(updateUserAttrMap)) {
                data.putAll(updateUserAttrMap);
            }
        }
        if (data.isEmpty()) {
            throw new FissionException("updateUserAttr data is null");
        }
        return data;
    }

}
