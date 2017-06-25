package com.zzh.zhbj.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
	}
	@Override
	public void initData() {
		//��ʼ������
		tvTitle.setText("����");
		//����ҳ�����ز໬�˵�
		btnTitleMemu.setVisibility(View.GONE);
		//��ֹSlidingMenu�໬�˵�Ч��
		//SlidingMenu���ɻ���
		setSlidingMenuEnabled(false);
		
		//��ʼ��Home��ҳ����
		TextView tv = new TextView(mActivity);
		tv.setText("����ҳ��");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		
		flPagerContent.addView(tv);
	}
}
