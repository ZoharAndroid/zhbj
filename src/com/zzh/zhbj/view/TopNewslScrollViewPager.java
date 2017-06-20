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
public class TopNewslScrollViewPager extends ViewPager {

	private int startX;
	private int startY;

	public TopNewslScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TopNewslScrollViewPager(Context context) {
		super(context);
	}

	/**
	 * 事件分发，请求父控件不要拦截事件
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);// 用getParent去请求,不是第一个就请求不要拦截
			startX = (int) ev.getRawX();
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int endX = (int) ev.getRawX();
			int endY = (int) ev.getRawY();
			// 判断左右滑动还是上下滑动
			if (Math.abs(endX - startX) > Math.abs(endY - startY)) {// 左右滑动
				if (endX > startX) {// 往右滑动
					//如果是第一个页面就不要父亲拦截
					if (getCurrentItem()==0) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				} else {
					// 往左滑动
					//如果是最后一页，需要拦截
					if (getCurrentItem()==(getAdapter().getCount()-1)) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
			} else {// 上下滑动
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
