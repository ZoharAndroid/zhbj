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
		//��ʼ������
		tvTitle.setText("��ҳ");
		//��ҳ���ز໬�˵�
		btnTitleMemu.setVisibility(View.GONE);
		//SlidingMenu���ɻ���
		setSlidingMenuEnabled(false);
		
		//��ʼ��Home��ҳ����
		TextView tv = new TextView(mActivity);
		tv.setText("��ҳ");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		flPagerContent.addView(tv);
	}
}
