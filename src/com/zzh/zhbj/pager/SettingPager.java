package com.zzh.zhbj.pager;

import com.zzh.zhbj.R;
import com.zzh.zhbj.SendMessageActivity;
import com.zzh.zhbj.utils.DenstiyUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 设置界面
 * 
 * @author Administrator
 *
 */
public class SettingPager extends BasePager {

	private Button btnStartSendSMS;
	public SettingPager(Activity activity) {
		super(activity);
	}
	@Override
	public void initData() {
		tvTitle.setText("设置");
		btnTitleMemu.setVisibility(View.GONE);
		setSlidingMenuEnabled(false);
		
		View view = View.inflate(mActivity, R.layout.setting_pager_layout, null);
		btnStartSendSMS = (Button) view.findViewById(R.id.btn_start_send_sms);
		btnStartSendSMS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, SendMessageActivity.class);
				mActivity.startActivity(intent);
			}
		});
		
		flPagerContent.addView(view);
	}
}
