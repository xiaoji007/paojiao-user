package com.paojiao.user.controller.util;

import com.fission.utils.tool.StringUtil;
import com.paojiao.user.util.ConstUtil;

import java.util.*;

public class KeyworldUtil {

	public static String getKeyWorld(String message, Map<Short, Map<Character, Set<String>>> worldMap) {
		if (StringUtil.isBlank(message) || null == worldMap || worldMap.isEmpty()) {
			return null;
		}
		String world = KeyworldUtil.getContainWorld(message, worldMap.get(ConstUtil.KeyworldRultType.CONTAIN));
		if (StringUtil.isBlank(world)) {
			world = KeyworldUtil.getEqualsWorld(message, worldMap.get(ConstUtil.KeyworldRultType.EQUALS));
		}
		return world;
	}

	private static String removeMessage(String message) {
		if (StringUtil.isBlank(message)) {
			return "";
		}
		String messageTemp = message.replaceAll("\\p{N}", "").replaceAll("\\p{S}", "").replaceAll("\\p{C}", "").replaceAll("\\p{Z}", " ").replaceAll("\\p{P}", " ").toLowerCase();
		String[] strs = messageTemp.split(" ");
		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			if (StringUtil.isNotBlank(str)) {
				builder.append(" ");
				builder.append(str);
			}
		}
		return builder.toString().trim();
	}

	public static String getContainWorld(String message, Map<Character, Set<String>> worldMap) {
		if (StringUtil.isBlank(message) || null == worldMap || worldMap.isEmpty()) {
			return null;
		}
		String str = KeyworldUtil.removeMessage(message);
		if (StringUtil.isBlank(message)) {
			return null;
		}
		String containStr = str.replaceAll(" ", "");
		char[] chars = containStr.toCharArray();
		int size = chars.length;
		List<Character> chatList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Set<String> set = worldMap.get(chars[i]);
			if (null == set || set.isEmpty() || chatList.contains(chars[i])) {
				continue;
			}
			for (String world : set) {
				if (null == world) {
					continue;
				}
				if (containStr.contains(world)) {
					return world;
				}
			}
			chatList.add(chars[i]);
		}
		return null;
	}

	public static String getEqualsWorld(String message, Map<Character, Set<String>> worldMap) {
		if (StringUtil.isBlank(message) || null == worldMap || worldMap.isEmpty()) {
			return null;
		}
		String str = KeyworldUtil.removeMessage(message);
		if (StringUtil.isBlank(message)) {
			return null;
		}
		String strTemp = " " + str + " ";
		String[] worlds = str.split(" ");
		int size = worlds.length;
		for (int i = 0; i < size; i++) {
			String worldTemp = worlds[i];
			if (StringUtil.isBlank(worldTemp)) {
				continue;
			}
			Set<String> set = worldMap.get(worldTemp.charAt(0));
			if (null == set || set.isEmpty()) {
				continue;
			}
			for (String world : set) {
				if (null == world) {
					continue;
				}
				if (world.indexOf(" ") > -1) {
					if (strTemp.indexOf(" " + world.trim() + " ") > -1) {
						return world;
					}
				} else {
					if (worldTemp.equals(world)) {
						return world;
					}
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Map<Character, Set<String>> worldMap = new HashMap<>();
		Set<String> set = worldMap.computeIfAbsent('a',key->new HashSet<>());
		set.add("a b c");
		System.out.println(KeyworldUtil.getEqualsWorld("wd a 1 b c",worldMap));
	}
}
