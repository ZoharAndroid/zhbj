package com.zzh.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 水平滑动的ViewPager
 * 
 * @author Administrator
 *
 */
public class HorizatalScrollViewPager extends ViewPager {

	public HorizatalScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HorizatalScrollViewPager(Context context) {
		super(context);
	}

	 /**
     * 事件分发，请求父控件不要拦截事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	if(getCurrentItem()!=0){
    		getParent().requestDisallowInterceptTouchEvent(true);//用getParent去请求,不是第一个就请求不要拦截
    	}else{
    		getParent().requestDisallowInterceptTouchEvent(false);//如果是第一个，就请求拦截
    	}
    	return super.dispatchTouchEvent(ev);
    }
}
