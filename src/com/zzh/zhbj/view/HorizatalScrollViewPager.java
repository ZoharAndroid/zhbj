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
public class HorizatalScrollViewPager extends ViewPager {

	public HorizatalScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HorizatalScrollViewPager(Context context) {
		super(context);
	}

	 /**
     * �¼��ַ������󸸿ؼ���Ҫ�����¼�
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	if(getCurrentItem()!=0){
    		getParent().requestDisallowInterceptTouchEvent(true);//��getParentȥ����,���ǵ�һ��������Ҫ����
    	}else{
    		getParent().requestDisallowInterceptTouchEvent(false);//����ǵ�һ��������������
    	}
    	return super.dispatchTouchEvent(ev);
    }
}
