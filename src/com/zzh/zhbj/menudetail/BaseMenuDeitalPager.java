package com.zzh.zhbj.menudetail;

import android.app.Activity;
import android.view.View;

/**
 * �໬�˵�����ҳ--����
 * 
 * @author Administrator
 *
 */
public abstract class BaseMenuDeitalPager {
	public  Activity mActivity;

	public View mRootView;
	
	public BaseMenuDeitalPager(Activity activity) {
		mActivity = activity;
		mRootView = initViews();
	}

	public abstract View initViews();

	public void initData() {

	};

}
