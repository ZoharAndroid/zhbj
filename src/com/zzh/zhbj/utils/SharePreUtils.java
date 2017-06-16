package com.zzh.zhbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharedpreference的工具类
 * 
 * @author Administrator
 *
 */
public class SharePreUtils {
	
	public static final String GUIDE_SP_NAME = "config";//sharedpreference的文件名

	/**
	 * 获取SP文件中的boolean值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getSPBoolean(Context context, String key,
			boolean defValue) {
		SharedPreferences sp = context.getSharedPreferences(GUIDE_SP_NAME,
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}

	/**
	 * 将boolean值写入到sp文件中
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setSPBoolean(Context context, String key, boolean value) {
		SharedPreferences sp = context.getSharedPreferences(GUIDE_SP_NAME,
				Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}
}
