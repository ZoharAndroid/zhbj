package com.zzh.zhbj.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * �໬�˵�����ҳ--����
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
		// ��ʼ��Home��ҳ����
		TextView tv = new TextView(mActivity);
		tv.setText("�໬�˵�����ҳ--����");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);

		return tv;
	}

}
