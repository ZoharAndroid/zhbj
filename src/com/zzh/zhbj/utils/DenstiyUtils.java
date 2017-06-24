package com.zzh.zhbj.utils;

import android.content.Context;

/**
 * ��Ļ�ߴ�ת��������
 * 
 * @author Administrator
 *
 */
public class DenstiyUtils {
	/**
	 * �ܶ�ת����
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
	 * ����ת�ܶ�
	 * @param context
	 * @param px
	 * @return
	 */
	public static int  px2dp(Context context,float px){
		float density = context.getResources().getDisplayMetrics().density;
		return (int) ((px*density)+0.5f);
	}
}
