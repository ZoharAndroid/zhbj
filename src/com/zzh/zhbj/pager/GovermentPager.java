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
		// ��ʼ������
		tvTitle.setText("����");
		// ����ҳ����ʾ�໬�˵�
		btnTitleMemu.setVisibility(View.VISIBLE);
		// ����SlidingMenu�໬�˵�Ч��
		// SlidingMenu�ɻ���
		setSlidingMenuEnabled(true);

		// ��ʼ��Home��ҳ����
		TextView tv = new TextView(mActivity);
		tv.setText("����");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		flPagerContent.addView(tv);
	}
}
