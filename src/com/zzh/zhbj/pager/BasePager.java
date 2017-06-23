package com.zzh.zhbj.pager;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zzh.zhbj.MainActivity;
import com.zzh.zhbj.R;

/**
 * ViewPager��5��tab��Ӧ��ÿ��Pagerҳ��Ļ��� ����:��������Fragment��ʾ������
 * 
 * @author Administrator
 * 
 */
public class BasePager {
	public View mRootView;// ����ViewPager��ÿһ��Pager����ͼView

	public TextView tvTitle;// ������
	public ImageButton btnTitleMemu;// �������ϵĲ໬�˵���ť
	public FrameLayout flPagerContent;// ����ViewPager��ÿһ��Pager��Frament

	public ImageButton btnPhotoStype;// ��ͼͼƬ��ʾת��

	public Activity mActivity;

	public BasePager(Activity activity) {
		this.mActivity = activity;
		initView();
	}

	// ÿ��pagerҳ��Ļ�����ܲ���
	private void initView() {
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);
		tvTitle = (TextView) mRootView.findViewById(R.id.tv_title_pager);
		btnTitleMemu = (ImageButton) mRootView
				.findViewById(R.id.btn_title_menu_pager);
		flPagerContent = (FrameLayout) mRootView
				.findViewById(R.id.fl_pager_content);
		
		btnPhotoStype = (ImageButton) mRootView.findViewById(R.id.btn_list_griad_icon);
		
		// ������������ImageButton��ť
		btnTitleMemu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggleSlidingMenu();// ����������İ�ť�������໬�˵���
			}
		});

	}

	/**
	 * ��ʼ������
	 */
	public void initData() {

	}

	/**
	 * �л�SlidingMenu��״̬
	 */
	public void toggleSlidingMenu() {
		MainActivity activity = (MainActivity) mActivity;
		SlidingMenu menu = activity.getSlidingMenu();
		menu.toggle();// // �л�״̬, ��ʾʱ����, ����ʱ��ʾ
	}

	/**
	 * ����SlidingMenu�Ƿ���Ի���
	 * 
	 * @param enable
	 */
	public void setSlidingMenuEnabled(boolean enable) {
		MainActivity activity = (MainActivity) mActivity;
		SlidingMenu menu = activity.getSlidingMenu();
		if (enable) {
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
}
