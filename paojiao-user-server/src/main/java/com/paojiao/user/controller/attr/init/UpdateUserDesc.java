package com.paojiao.user.controller.attr.init;

import com.fission.next.common.error.FissionCodeException;
import com.fission.utils.tool.StringUtil;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.api.util.UserErrorCode;
import com.paojiao.user.config.ApplicationConfig;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import com.paojiao.user.controller.exception.KeywordException;
import com.paojiao.user.controller.util.KeyworldUtil;
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
    private ApplicationConfig applicationConfig;

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
                throw new KeywordException(keyworld, UserErrorCode.DESC_KEYWORD_ERROR);
            }
        }
        if (this.applicationConfig.getDescLength() > 0 && this.applicationConfig.getDescLength() < data.length()) {
            throw new FissionCodeException(UserErrorCode.DESC_LENGTH_ERROR);
        }
        Map<Short, Object> map = new HashMap<>();
        map.put(ConstUtil.UserAttrId.USER_DESC, data);
        return map;
    }
}



