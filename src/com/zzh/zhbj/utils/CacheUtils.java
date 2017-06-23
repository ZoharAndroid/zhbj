package com.zzh.zhbj.utils;

import android.content.Context;

/**
 * ���湤����
 * 
 * @author Administrator
 * 
 */
public class CacheUtils {

	/**
	 * ���û���
	 * 
	 * @param context
	 * @param key
	 *            url
	 * @param value
	 *            json����
	 */
	public static void setCache(Context context, String key, String value) {
		SharePreUtils.setSPString(context, key, value);
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param context
	 * @param key
	 *            url
	 * @return ���������
	 * 
	 */
	public static String getCache(Context context, String key) {
		return SharePreUtils.getSPString(context, key, null);
	}
}
