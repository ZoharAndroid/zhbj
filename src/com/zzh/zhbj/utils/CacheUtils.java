package com.zzh.zhbj.utils;

import android.content.Context;

/**
 * 缓存工具类
 * 
 * @author Administrator
 * 
 */
public class CacheUtils {

	/**
	 * 设置缓存
	 * 
	 * @param context
	 * @param key
	 *            url
	 * @param value
	 *            json数据
	 */
	public static void setCache(Context context, String key, String value) {
		SharePreUtils.setSPString(context, key, value);
	}

	/**
	 * 获取缓存数据
	 * 
	 * @param context
	 * @param key
	 *            url
	 * @return 缓存的数据
	 * 
	 */
	public static String getCache(Context context, String key) {
		return SharePreUtils.getSPString(context, key, null);
	}
}
