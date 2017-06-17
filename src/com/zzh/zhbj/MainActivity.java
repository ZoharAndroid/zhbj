package com.zzh.zhbj;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zzh.zhbj.fragment.ContentFragment;
import com.zzh.zhbj.fragment.LeftMenuFragment;
import com.zzh.zhbj.global.GlobalContacts;

public class MainActivity extends SlidingFragmentActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initSlidingMenu(); // 初始化SlidingMenu
		initFragment(); // 加载Fragment
	}

	/**
	 * 加载Fragment
	 */
	private void initFragment() {
		android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction transaction = manager
				.beginTransaction();
		transaction.replace(R.id.fm_left_menu, new LeftMenuFragment(),
				GlobalContacts.TAG_LEFT_MENU_FRAGMENT);
		transaction.replace(R.id.fm_main_content, new ContentFragment(),
				GlobalContacts.TAG_CONTENT_FRAGMENT);
		transaction.commit();
	}

	/**
	 * 初始化SlidingMenu
	 */
	private void initSlidingMenu() {
		// 1、设置SlidingMenu菜单的布局
		setBehindContentView(R.layout.left_menu);
		// 2、修改参数就必须要获取SlidingMenu菜单
		SlidingMenu menu = getSlidingMenu();
		// 3、设置触摸能够触发Menu的位置：一般设置为全屏
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 4、如果想到到Menu里面能够进行滑动恢复，就需要设置下面的
		// menu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// 4、设置侧滑栏的宽度
		menu.setBehindWidth(400);// 这个是设置菜单的宽度
		// menu.setBehindOffset(200);// 这个是设置空白区占据的宽度
	}

	// 从MainActivity中获取LeftMenuFragment
	public LeftMenuFragment getLeftMenuFragment() {
		FragmentManager manager = getSupportFragmentManager();
		LeftMenuFragment fragment = (LeftMenuFragment) manager
				.findFragmentByTag(GlobalContacts.TAG_LEFT_MENU_FRAGMENT);
		return fragment;
	}
	
	public ContentFragment getContentFragment(){
		FragmentManager manager = getSupportFragmentManager();
		ContentFragment fragment = (ContentFragment)manager.findFragmentByTag(GlobalContacts.TAG_CONTENT_FRAGMENT);
		return fragment;
	}

}
