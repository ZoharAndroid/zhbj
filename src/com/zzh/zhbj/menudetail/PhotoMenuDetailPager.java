package com.zzh.zhbj.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * �໬�˵�����ҳ--ר��
 * 
 * @author Administrator
 *
 */
public class PhotoMenuDetailPager extends BaseMenuDeitalPager {

	public PhotoMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initViews() {
		// ��ʼ��Home��ҳ����
		TextView tv = new TextView(mActivity);
		tv.setText("�໬�˵�����ҳ--ר��");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);

		return tv;
	}

}
