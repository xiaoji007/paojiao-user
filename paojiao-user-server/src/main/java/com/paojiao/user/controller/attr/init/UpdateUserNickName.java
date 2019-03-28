package com.paojiao.user.controller.attr.init;

import com.fission.next.common.error.FissionCodeException;
import com.fission.utils.tool.ArrayUtils;
import com.fission.utils.tool.StringUtil;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.api.util.UserErrorCode;
import com.paojiao.user.config.ApplicationConfig;
import com.paojiao.user.controller.attr.UpdateUserAttr;
import com.paojiao.user.controller.exception.KeywordException;
import com.paojiao.user.controller.util.KeyworldUtil;
import com.paojiao.user.service.IKeywordService;
import com.paojiao.user.service.bean.NickNameAsciiInfo;
import com.paojiao.user.service.bean.NickNameKeyworldInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UpdateUserNickName extends UpdateUserAttr {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateUserNickName.class);
    private static final ConcurrentHashMap<Short, Map<Integer, Integer>> NICK_NAME_ASCII_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Short, List<Integer>> NICK_NAME_ASCII_KEY_LIST = new ConcurrentHashMap<>();
    protected static final ConcurrentHashMap<Short, Map<Character, Set<String>>> NICK_NAME_KEYWORLD_MAP = new ConcurrentHashMap<>();

    @Inject
    private ApplicationConfig applicationConfig;

    @Inject
    private IKeywordService keywordService;

    public UpdateUserNickName() {
        super(ConstUtil.UserAttrId.NICK_NAME);
    }

    @Override
    public Map<Short, Object> getUpdateUserAttr(int userId, String data) {
        if (StringUtil.isBlank(data)) {
            throw new FissionCodeException(UserErrorCode.NICK_NAME_NULL_ERROR);
        }

        if (data.trim().length() > this.applicationConfig.getNickNameLength() || data.length() < 1) {
            throw new FissionCodeException(UserErrorCode.NICK_NAME_LENGTH_ERROR);
        }

        String keyworld = this.getNickNameAscii(data);
        if (StringUtil.isNotBlank(keyworld)) {
            throw new KeywordException(keyworld,UserErrorCode.NICK_NAME_ASCII_ERROR);
        }

        keyworld = KeyworldUtil.getKeyWorld(data, UpdateUserNickName.NICK_NAME_KEYWORLD_MAP);
        if (StringUtil.isNotBlank(keyworld)) {
            throw new KeywordException(keyworld,UserErrorCode.NICK_NAME_KEYWORD_ERROR);
        }

        Map<Short, Object> map = new HashMap<>();
        map.put(ConstUtil.UserAttrId.NICK_NAME, data);
        return map;
    }

    @PostConstruct
    public void initNickNameKeyword() {
        List<NickNameKeyworldInfo> nickNameKeyworldInfoList = this.keywordService.listAllNickNameKeyworldInfo();
        Map<Short, Map<Character, Set<String>>> worldMap = new HashMap();
        if (ArrayUtils.isNotEmpty(nickNameKeyworldInfoList)) {
            nickNameKeyworldInfoList.forEach((NickNameKeyworldInfo nickNameKeyworldInfo) -> {
                if (null != nickNameKeyworldInfo && StringUtil.isNotBlank(nickNameKeyworldInfo.getKeyworld())) {
                    Map<Character, Set<String>> map = worldMap.computeIfAbsent(nickNameKeyworldInfo.getRuleType(), key -> new HashMap());
                    Set<String> worldList = map.computeIfAbsent(nickNameKeyworldInfo.getKeyworld().toLowerCase().charAt(0), key -> new HashSet<>());
                    worldList.add(nickNameKeyworldInfo.getKeyworld().toLowerCase());
                }
            });
        }
        UpdateUserNickName.NICK_NAME_KEYWORLD_MAP.clear();
        UpdateUserNickName.NICK_NAME_KEYWORLD_MAP.putAll(worldMap);
    }

    @PostConstruct
    public void initNickNameAscii() {
        List<NickNameAsciiInfo> nickNameAsciiInfoList = this.keywordService.listAllNickNameAsciiInfo();
        Map<Short, List<Integer>> sortMap = new HashMap<>();
        Map<Short, Map<Integer, Integer>> asciiMap = new HashMap<>();
        if (null != nickNameAsciiInfoList && !nickNameAsciiInfoList.isEmpty()) {
            nickNameAsciiInfoList.forEach((NickNameAsciiInfo nickNameAsciiInfo) -> {
                if (null != nickNameAsciiInfo) {
                    Map<Integer, Integer> map = asciiMap.computeIfAbsent(nickNameAsciiInfo.getAsciiType(), key -> new HashMap<>());
                    map.put(nickNameAsciiInfo.getStartAscii(), nickNameAsciiInfo.getEndAscii());
                    List<Integer> list = sortMap.computeIfAbsent(nickNameAsciiInfo.getAsciiType(), key -> new ArrayList<>());
                    list.add(nickNameAsciiInfo.getStartAscii());
                }
            });
        }

        UpdateUserNickName.NICK_NAME_ASCII_MAP.clear();
        UpdateUserNickName.NICK_NAME_ASCII_KEY_LIST.clear();
        UpdateUserNickName.NICK_NAME_ASCII_MAP.putAll(asciiMap);
        UpdateUserNickName.NICK_NAME_ASCII_KEY_LIST.putAll(sortMap);
    }

    private String getNickNameAscii(String nickName) {
        if (StringUtil.isBlank(nickName) || UpdateUserNickName.NICK_NAME_ASCII_MAP.isEmpty()) {
            return null;
        }
        Set<Integer> set = new HashSet<>();
        String asciiStr = null;
        //nickName ASCII
        char[] asciis = nickName.trim().toCharArray();
        for (char asciiChar : asciis) {
            int ascii = (int) asciiChar;
            if (set.contains(ascii)) {
                break;
            }

            //所有系统ASCII
            for (Map.Entry<Short, List<Integer>> entry : UpdateUserNickName.NICK_NAME_ASCII_KEY_LIST.entrySet()) {
                //连续ASCII key
                List<Integer> keys;
                if (null == entry || null == (keys = entry.getValue()) || keys.isEmpty()) {
                    continue;
                }
                short state = entry.getKey();
                boolean bool = false;

                //是否命中ASCII连续空间
                break_:
                for (Integer min : keys) {
                    if (null == min || ascii < min) {
                        continue;
                    }
                    ////连续ASCII key 最大 ASCII
                    Integer max = UpdateUserNickName.NICK_NAME_ASCII_MAP.get(state).get(min);
                    if (ascii <= max) {
                        bool = true;
                        break break_;
                    }
                }
                //没有命中可以使用的ASCII连续空间或者命中不可以使用的ASCII连续空间
                if ((!bool && com.paojiao.user.util.ConstUtil.NickNameKeywordAscIIType.Y == state) || (bool && com.paojiao.user.util.ConstUtil.NickNameKeywordAscIIType.N == state)) {
                    return String.valueOf((char) ascii);
                }
            }
            set.add(ascii);
        }
        return null;
    }
}



