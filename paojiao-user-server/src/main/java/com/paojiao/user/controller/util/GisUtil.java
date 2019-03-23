package com.paojiao.user.controller.util;

import java.math.BigDecimal;
import java.util.Map;

public class GisUtil {
	public final static double EARTH_RADIUS = 6378137.0;// 地球赤道半径(单位：m。6378137m是1980年的标准，比1975年的标准6378140少3m）

	/// <summary>
	/// 角度数转换为弧度公式
	/// </summary>
	/// <param name="d"></param>
	/// <returns></returns>
	private static double radians(double d) {
		return d * Math.PI / 180.0;
	}

	/// <summary>
	/// 弧度转换为角度数公式
	/// </summary>
	/// <param name="d"></param>
	/// <returns></returns>
	private static double genoPoints(double d) {
		return d * (180 / Math.PI);
	}

	/// <summary>
	/// 计算两个经纬度之间的直接距离(google 算法)
	/// </summary>
	public static double getDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
		if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
			return -1;
		}
		double radLat1 = radians(lat1);
		double radLng1 = radians(lon1);
		double radLat2 = radians(lat2);
		double radLng2 = radians(lon2);

		double s = Math.acos(Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radLng1 - radLng2) + Math.sin(radLat1) * Math.sin(radLat2));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static double getDistance(Map<String, BigDecimal> gis1, Map<String, BigDecimal> gis2) {
		if (null == gis1 || gis1.isEmpty() || null == gis2 || gis2.isEmpty()) {
			return -1;
		}
		BigDecimal lon = (BigDecimal) gis1.get("lon");
		BigDecimal lat = (BigDecimal) gis1.get("lat");

		BigDecimal lon2 = (BigDecimal) gis2.get("lon");
		BigDecimal lat2 = (BigDecimal) gis2.get("lat");
		if (null != lon && null != lat && null != lon2 && null != lat2) {
			return GisUtil.getDistance(lat.doubleValue(), lon.doubleValue(), lat2.doubleValue(), lon2.doubleValue());
		}
		return -1;
	}

//	public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
//		if (lon1 == 0 || lat1 == 0 || lon2 == 0 || lat2 == 0) {
//			return -1;
//		}
//		// 角度转换为弧度
//		double ew1 = lon1 * DEF_PI180;
//		double ns1 = lat1 * DEF_PI180;
//		double ew2 = lon2 * DEF_PI180;
//		double ns2 = lat2 * DEF_PI180;
//		// 求大圆劣弧与球心所夹的角(弧度)
//		double distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
//		// 调整到[-1..1]范围内，避免溢出
//		if (distance > 1.0) {
//			distance = 1.0;
//		} else if (distance < -1.0) {
//			distance = -1.0;
//		}
//		// 求大圆劣弧长度
//		distance = DEF_R * Math.acos(distance);
//		return distance;
//	}
}
