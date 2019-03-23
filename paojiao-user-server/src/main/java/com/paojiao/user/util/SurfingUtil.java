package com.paojiao.user.util;

import com.paojiao.user.service.ISurfingService;
import com.paojiao.user.service.bean.SurfingRuleInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 靓号生成规则
 */
@Service
public class SurfingUtil {

    private static final Map<Integer, List<SurfingRuleInfo>> SURFING_RULE_MAP = new HashMap();
    private static final Map<String, Pattern> PATTERN_MAP = new HashMap();
    private static final String SURFING_RECOVER_KEY = "user.surfing.recover.list";
    private static final String SURFING_SPECIAL_KEY = "user.surfing.special.list";
    private static final int SURFING_RECOVER_TIMES = 5;
    private static SurfingUtil surfingUtil;

    @Inject
    private ISurfingService surfingService;

    @Inject
    @Named(ConstUtil.NameUtil.REDIS_USER)
    private RedisTemplate redis;

    private static int getCacheSurfing(int time) {
        Integer surfing = (Integer) SurfingUtil.surfingUtil.redis.opsForSet().pop(SurfingUtil.SURFING_RECOVER_KEY);
        if (null != surfing) {
            boolean bool = SurfingUtil.surfingUtil.surfingService.useSurfingRecover(surfing);
            if (bool) {
                return surfing;
            }
            if (time < 0) {
                return 0;
            }
            return SurfingUtil.getCacheSurfing(time--);
        }
        return 0;
    }

    private static int getDbSurfing() {
        int surfing = SurfingUtil.surfingUtil.surfingService.addSurfing();
        int endSurfing = SurfingUtil.endSurfing(surfing);
        if (endSurfing != surfing) {
            SurfingUtil.surfingUtil.surfingService.addSurfingSpecialInfo(surfing, endSurfing);
            return SurfingUtil.getDbSurfing();
        }
        List<SurfingRuleInfo> list = SurfingUtil.SURFING_RULE_MAP.get(0);
        if (null == list) {
            list = new ArrayList<>();
        }
        List<SurfingRuleInfo> list2 = SurfingUtil.SURFING_RULE_MAP.get(endSurfing + "".length());
        if (null != list2 && !list2.isEmpty()) {
            list.addAll(list2);
        }
        if (!list.isEmpty()) {
            for (SurfingRuleInfo surfingRuleInfo : list) {
                if (null != surfingRuleInfo) {
                    continue;
                }
                Matcher matcher = SurfingUtil.getPattern(surfingRuleInfo.getSurfingRule()).matcher(endSurfing + "");
                if (matcher.find()) {
                    SurfingUtil.surfingUtil.surfingService.addSurfingSpecialInfo(endSurfing, endSurfing + surfingRuleInfo.getSurfingIncrement());
                    return SurfingUtil.getDbSurfing();
                }
            }
        }
        return surfing;
    }

    /**
     * 生成亮靓号
     *
     * @return
     */
    public static int getSurfing() {
        int surfing = SurfingUtil.getCacheSurfing(SurfingUtil.SURFING_RECOVER_TIMES);
        if (surfing > 0) {
            return surfing;
        }
        return SurfingUtil.getDbSurfing();
    }

    private static int toSurfing(int surfing, int end) {
        String surfingStr = surfing + "";
        if (surfingStr.length() >= end) {
            int num = Integer.parseInt(surfingStr.substring(end - 1, end)) + 1;
            int heightNum = Integer.parseInt(surfingStr.substring(0, end - 1)) * ((int) Math.pow(10, surfingStr.length() - end + 1));
            int lowerNum = num * ((int) Math.pow(10, surfingStr.length() - end));
            return heightNum + lowerNum;
        }
        return surfing;
    }

    private static int toSurfing(int surfing, String regex) {
        return SurfingUtil.toSurfing(surfing, regex, 0);
    }

    private static Pattern getPattern(String regex) {
        Pattern pattern = SurfingUtil.PATTERN_MAP.get(regex);
        if (null == pattern) {
            synchronized (regex.intern()) {
                pattern = SurfingUtil.PATTERN_MAP.get(regex);
                if (null == pattern) {
                    pattern = Pattern.compile(regex);
                    SurfingUtil.PATTERN_MAP.put(regex, pattern);
                }
            }
        }
        return pattern;
    }

    private static int toSurfing(int surfing, String regex, int shift) {
        String surfingStr = surfing + "";
        Matcher matcher = SurfingUtil.getPattern(regex).matcher(surfingStr);
        if (matcher.find()) {
            return SurfingUtil.toSurfing(surfing, matcher.end() + shift);
        }
        return surfing;
    }

    private static int toSurfing(int surfing) {
        char[] chars = (surfing + "").toCharArray();
//		Map<Character, AtomicInteger> charMap = new HashMap<>();
//		int size = chars.length;
//		int maxCharSize = 0, replaceIndex = 0;
//		for (int i = 0; i < size; i++) {
//			char aChar = chars[i];
//			AtomicInteger num = charMap.computeIfAbsent(aChar, key -> new AtomicInteger(0));
//			num.addAndGet(1);
//			if (num.get() >= maxCharSize) {
//				replaceIndex = i + 1;
//				maxCharSize = num.get();
//			}
//		}
        Set<Character> charSet = new HashSet<>();
        int size = chars.length;
        for (int i = 0; i < size; i++) {
            char aChar = chars[i];
            charSet.add(aChar);
        }
        if (charSet.size() <= chars.length / 2) {
            return SurfingUtil.toSurfing(surfing, size);
        } else {
            return surfing;
        }
    }

    private static int endSurfing(int surfing) {
        String surfing_aaa = "(\\d)\\1{2}";
        String surfing_aabb = "(\\d)\\1(\\d)\\2";
        String surfing_abc = "(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){%s}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){%s})";
        int returnSurfing = surfing;
        while (true) {
            int surfingTemp = returnSurfing;
            String surfingStr = surfing + "";
            int num = surfingStr.length() / 2 + (surfingStr.length() % 2 == 1 ? 1 : 0);
            returnSurfing = SurfingUtil.toSurfing(returnSurfing, String.format(surfing_abc, num, num), 1);
            returnSurfing = SurfingUtil.toSurfing(returnSurfing, surfing_aaa);
            returnSurfing = SurfingUtil.toSurfing(returnSurfing, surfing_aabb);
            returnSurfing = SurfingUtil.toSurfing(returnSurfing);
            if (returnSurfing == surfingTemp) {
                return returnSurfing == surfing ? returnSurfing : (returnSurfing - 1);
            }
        }
    }

    public static void main(String[] args) {
        int surfing = 114415;
        System.err.println(SurfingUtil.endSurfing(surfing));
    }

    @Scheduled(fixedDelay = 60000)
    private void initSurfingRule() {
        Map<Integer, List<SurfingRuleInfo>> map = this.surfingService.listSurfingRuleInfo();
        SurfingUtil.SURFING_RULE_MAP.clear();
        if (null != map && !map.isEmpty()) {
            SurfingUtil.SURFING_RULE_MAP.putAll(map);
        }
    }

    @Scheduled(cron = "0 0 0/4 * * ?")
    private void initSurfingRecover() {
        this.redis.delete(SurfingUtil.SURFING_RECOVER_KEY);
        List<Integer> list = this.surfingService.listSurfingRecoverSurfingByUserUnuse();
        if (null != list && !list.isEmpty()) {
            this.redis.opsForSet().add(SurfingUtil.SURFING_RECOVER_KEY, list.toArray(new Integer[list.size()]));
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    private void initSurfingSpecial() {
        this.redis.delete(SurfingUtil.SURFING_SPECIAL_KEY);
        List<Integer> list = this.surfingService.listSurfingSpecialSurfingIdByInit();
        if (null != list && !list.isEmpty()) {
            this.redis.opsForSet().add(SurfingUtil.SURFING_SPECIAL_KEY, list.toArray(new Integer[list.size()]));
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    private void surfingSpecialToRecover() {
        while (true) {
            Integer surfingId = (Integer) SurfingUtil.surfingUtil.redis.opsForSet().pop(SurfingUtil.SURFING_SPECIAL_KEY);
            if (null == surfingId) {
                return;
            }
            try {
                this.surfingService.surfingSpecialToSurfingRecover(surfingId);
            } catch (Exception e) {

            }
        }
    }

    @PostConstruct
    private void init() {
        SurfingUtil.surfingUtil = this;
        this.initSurfingRule();
        this.initSurfingRecover();
        this.initSurfingSpecial();
    }
}
