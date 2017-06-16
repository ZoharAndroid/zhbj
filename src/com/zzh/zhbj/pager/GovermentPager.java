package com.zzh.zhbj.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class GovermentPager extends BasePager {

	public GovermentPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		// 初始化标题
		tvTitle.setText("政务");
		// 设置页面显示侧滑菜单
		btnTitleMemu.setVisibility(View.VISIBLE);
		// 可以SlidingMenu侧滑菜单效果
		// SlidingMenu可滑动
		setSlidingMenuEnabled(true);

		// 初始化Home主页内容
		TextView tv = new TextView(mActivity);
		tv.setText("政务");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		flPagerContent.addView(tv);
	}
}
