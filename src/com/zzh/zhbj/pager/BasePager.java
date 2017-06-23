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
 * ViewPager中5个tab对应的每个Pager页面的基类 包含:标题栏和Fragment显示的内容
 * 
 * @author Administrator
 * 
 */
public class BasePager {
	public View mRootView;// 整个ViewPager的每一个Pager根视图View

	public TextView tvTitle;// 标题栏
	public ImageButton btnTitleMemu;// 标题栏上的侧滑菜单按钮
	public FrameLayout flPagerContent;// 整个ViewPager的每一个Pager的Frament

	public ImageButton btnPhotoStype;// 组图图片显示转换

	public Activity mActivity;

	public BasePager(Activity activity) {
		this.mActivity = activity;
		initView();
	}

	// 每个pager页面的基础框架布局
	private void initView() {
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);
		tvTitle = (TextView) mRootView.findViewById(R.id.tv_title_pager);
		btnTitleMemu = (ImageButton) mRootView
				.findViewById(R.id.btn_title_menu_pager);
		flPagerContent = (FrameLayout) mRootView
				.findViewById(R.id.fl_pager_content);
		
		btnPhotoStype = (ImageButton) mRootView.findViewById(R.id.btn_list_griad_icon);
		
		// 监听标题栏的ImageButton按钮
		btnTitleMemu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggleSlidingMenu();// 点击标题栏的按钮，触发侧滑菜单栏
			}
		});

	}

	/**
	 * 初始化数据
	 */
	public void initData() {

	}

	/**
	 * 切换SlidingMenu的状态
	 */
	public void toggleSlidingMenu() {
		MainActivity activity = (MainActivity) mActivity;
		SlidingMenu menu = activity.getSlidingMenu();
		menu.toggle();// // 切换状态, 显示时隐藏, 隐藏时显示
	}

	/**
	 * 设置SlidingMenu是否可以滑动
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
