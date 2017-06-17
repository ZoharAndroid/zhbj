package com.zzh.zhbj.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * 侧滑菜单详情页--新闻
 * 
 * @author Administrator
 *
 */
public class NewsMenuDetailPager extends BaseMenuDeitalPager {

	public NewsMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initViews() {
		// 初始化Home主页内容
		TextView tv = new TextView(mActivity);
		tv.setText("侧滑菜单详情页--新闻");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);

		return tv;
	}

}
