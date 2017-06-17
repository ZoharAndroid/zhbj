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
		initSlidingMenu(); // ��ʼ��SlidingMenu
		initFragment(); // ����Fragment
	}

	/**
	 * ����Fragment
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
	 * ��ʼ��SlidingMenu
	 */
	private void initSlidingMenu() {
		// 1������SlidingMenu�˵��Ĳ���
		setBehindContentView(R.layout.left_menu);
		// 2���޸Ĳ����ͱ���Ҫ��ȡSlidingMenu�˵�
		SlidingMenu menu = getSlidingMenu();
		// 3�����ô����ܹ�����Menu��λ�ã�һ������Ϊȫ��
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 4������뵽��Menu�����ܹ����л����ָ�������Ҫ���������
		// menu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// 4�����ò໬���Ŀ��
		menu.setBehindWidth(400);// ��������ò˵��Ŀ��
		// menu.setBehindOffset(200);// ��������ÿհ���ռ�ݵĿ��
	}

	// ��MainActivity�л�ȡLeftMenuFragment
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
