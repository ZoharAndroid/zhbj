package com.zzh.zhbj.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * 侧滑菜单详情页--组图
 * 
 * @author Administrator
 *
 */
public class TopicMenuDetailPager extends BaseMenuDeitalPager {

	public TopicMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initViews() {
		// 初始化Home主页内容
		TextView tv = new TextView(mActivity);
		tv.setText("侧滑菜单详情页--组图");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);

		return tv;
	}

}
