package com.zzh.zhbj.utils;

import android.content.Context;

/**
 * 屏幕尺寸转换工具类
 * 
 * @author Administrator
 *
 */
public class DenstiyUtils {
	/**
	 * 密度转像素
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static float dp2px(Context context,int dp){
			float density = context.getResources().getDisplayMetrics().density;
			return dp/density;
	}
	
	/**
	 * 像素转密度
	 * @param context
	 * @param px
	 * @return
	 */
	public static int  px2dp(Context context,float px){
		float density = context.getResources().getDisplayMetrics().density;
		return (int) ((px*density)+0.5f);
	}
}
