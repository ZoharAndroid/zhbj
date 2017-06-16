package com.zzh.zhbj.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
	}
	@Override
	public void initData() {
		//初始化标题
		tvTitle.setText("设置");
		//设置页面隐藏侧滑菜单
		btnTitleMemu.setVisibility(View.GONE);
		//禁止SlidingMenu侧滑菜单效果
		//SlidingMenu不可滑动
		setSlidingMenuEnabled(false);
		
		//初始化Home主页内容
		TextView tv = new TextView(mActivity);
		tv.setText("设置页面");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		flPagerContent.addView(tv);
	}
}
