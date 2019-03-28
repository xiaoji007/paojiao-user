package com.paojiao.user.config;

import com.alibaba.fastjson.JSONObject;
import com.fission.utils.tool.JsonUtil;
import com.fission.utils.tool.StringUtil;
import com.paojiao.user.api.util.ConstUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "user")
@RefreshScope
@Data
public class ApplicationConfig {
    /**
     *
     */
    private static final long serialVersionUID = 1030896969430842758L;

    private long animateTime;
    private int nickNameLength;
    private int descLength;
    private boolean nickNameVerify;
    private long onlineCheckTime = 3 * 60 * 1000;
    private String userAttrIntegrity;
    private String userInviteAddress;
    private int userInviteNum;
    private String userPicMap;
    private String picSuffix;
    private int picLength;
    private int picNum;
    private boolean userPicVerify;
    private boolean verify;


    public boolean isPicSuffix(String fileSuffix) {
        if (StringUtil.isBlank(this.picSuffix)) {
            return true;
        }
        if (StringUtil.isBlank(fileSuffix)) {
            return false;
        }
        return this.picSuffix.contains("," + fileSuffix.trim() + ",");
    }

    public String getPicBySex(short sex) {
        Map map = JsonUtil.stringToTObj(userPicMap, Map.class);
        com.alibaba.fastjson.JSONArray pics = (com.alibaba.fastjson.JSONArray) map.get(String.valueOf(sex));
        if (!pics.isEmpty()) {
            return String.valueOf(pics.get(0));
        }
        return "";
    }

    public int getUserAttrIntegrity(int userAttrId) {
        if (StringUtil.isBlank(this.userAttrIntegrity)) {
            return 0;
        }
        JSONObject jsonObject = JsonUtil.stringToJSONObject(this.userAttrIntegrity);
        Object obj = jsonObject.get(userAttrId + "");
        if (null == obj) {
            return 0;
        }
        return Integer.parseInt(obj.toString());
    }

    public int getUserAttrIntegrity() {
        if (StringUtil.isBlank(this.userAttrIntegrity)) {
            return 0;
        }
        JSONObject jsonObject = JsonUtil.stringToJSONObject(this.userAttrIntegrity);
        int integrity = 0;
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (!entry.getKey().equals(ConstUtil.UserAttrId.INDEX_PIC + "")) {
                integrity += Integer.parseInt(entry.getValue().toString());
            } else {
                integrity += 8 * Integer.parseInt(entry.getValue().toString());
            }
        }
        return integrity;
    }

    public boolean isOnlineCheck(long lastCheckTime) {
        return this.onlineCheckTime + lastCheckTime > System.currentTimeMillis();
    }
}



