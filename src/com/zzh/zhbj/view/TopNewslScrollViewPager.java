package com.zzh.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ˮƽ������ViewPager
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
	 * �¼��ַ������󸸿ؼ���Ҫ�����¼�
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);// ��getParentȥ����,���ǵ�һ��������Ҫ����
			startX = (int) ev.getRawX();
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int endX = (int) ev.getRawX();
			int endY = (int) ev.getRawY();
			// �ж����һ����������»���
			if (Math.abs(endX - startX) > Math.abs(endY - startY)) {// ���һ���
				if (endX > startX) {// ���һ���
					//����ǵ�һ��ҳ��Ͳ�Ҫ��������
					if (getCurrentItem()==0) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				} else {
					// ���󻬶�
					//��������һҳ����Ҫ����
					if (getCurrentItem()==(getAdapter().getCount()-1)) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
			} else {// ���»���
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
