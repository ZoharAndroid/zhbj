package com.zzh.zhbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharedpreference�Ĺ�����
 * 
 * @author Administrator
 *
 */
public class SharePreUtils {
	
	public static final String GUIDE_SP_NAME = "config";//sharedpreference���ļ���

	/**
	 * ��ȡSP�ļ��е�booleanֵ
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
	 * ��booleanֵд�뵽sp�ļ���
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
	

	/**
	 * ��ȡSP�ļ��е�Stringֵ
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getSPString(Context context, String key,
			String defValue) {
		SharedPreferences sp = context.getSharedPreferences(GUIDE_SP_NAME,
				Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}

	/**
	 * ��Stringֵд�뵽sp�ļ���
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setSPString(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(GUIDE_SP_NAME,
				Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
}
