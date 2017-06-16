package com.zzh.zhbj.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class HomePager extends BasePager {

	public HomePager(Activity activity) {
		super(activity);
	}
	
	@Override
	public void initData() {
		//初始化标题
		tvTitle.setText("首页");
		//首页隐藏侧滑菜单
		btnTitleMemu.setVisibility(View.GONE);
		//SlidingMenu不可滑动
		setSlidingMenuEnabled(false);
		
		//初始化Home主页内容
		TextView tv = new TextView(mActivity);
		tv.setText("首页");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		flPagerContent.addView(tv);
	}
}
