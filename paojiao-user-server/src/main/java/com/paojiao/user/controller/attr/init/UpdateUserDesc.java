package com.paojiao.user.controller.attr.init;

import com.fission.utils.tool.StringUtil;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import com.paojiao.user.controller.exception.DescKeywordException;
import com.paojiao.user.controller.util.KeyworldUtil;
import com.paojiao.user.service.IKeyworldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserDesc extends UpdateUserAttr {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateUserDesc.class);

    @Inject
    private IKeyworldService keyworldService;

    public UpdateUserDesc() {
        super(ConstUtil.UserAttrId.USER_DESC);
    }

    @Override
    public Map<Short, Object> getUpdateUserAttr(int userId, String data) {
        if (StringUtil.isBlank(data)) {
            data = "";
        } else {
            String keyworld = KeyworldUtil.getKeyWorld(data, UpdateUserNickName.NICK_NAME_KEYWORLD_MAP);
            if (StringUtil.isNotBlank(keyworld)) {
                throw new DescKeywordException(keyworld);
            }
        }
        Map<Short, Object> map = new HashMap<>();
        map.put(ConstUtil.UserAttrId.USER_DESC, data);
        return map;
    }
}



