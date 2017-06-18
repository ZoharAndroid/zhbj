package com.zzh.zhbj.menudetail;

import com.zzh.zhbj.domain.NewsJsonData.NewsJsonTab;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * ��������Tab��ǩ
 * 
 * @author Administrator
 *
 */
public class TabMenuDetail extends BaseMenuDeitalPager {
	
	private NewsJsonTab mNewsTab;
	
	private TextView tv;

	public TabMenuDetail(Activity activity, NewsJsonTab newsJsonTab) {
		super(activity);
		mNewsTab = newsJsonTab;
	}

	@Override
	public View initViews() {
		tv = new TextView(mActivity);
		tv.setText("��ǩ");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		return tv;
	}
	
	@Override
	public void initData() {
		tv.setText(mNewsTab.getTitle());
	}

}
