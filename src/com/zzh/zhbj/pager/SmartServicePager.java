package com.zzh.zhbj.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class SmartServicePager extends BasePager {

	public SmartServicePager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		// ��ʼ������
		tvTitle.setText("�ǻ۷���");
		// ����ҳ����ʾ�໬�˵�
		btnTitleMemu.setVisibility(View.VISIBLE);
		// ����SlidingMenu�໬�˵�Ч��
		// SlidingMenu�ɻ���
		setSlidingMenuEnabled(true);

		// ��ʼ��Home��ҳ����
		TextView tv = new TextView(mActivity);
		tv.setText("�ǻ۷���");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		flPagerContent.addView(tv);
	}
}
