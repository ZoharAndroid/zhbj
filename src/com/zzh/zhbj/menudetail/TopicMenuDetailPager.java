package com.zzh.zhbj.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * �໬�˵�����ҳ--��ͼ
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
		// ��ʼ��Home��ҳ����
		TextView tv = new TextView(mActivity);
		tv.setText("�໬�˵�����ҳ--��ͼ");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);

		return tv;
	}

}
