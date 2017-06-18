package com.zzh.zhbj.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.viewpagerindicator.TabPageIndicator;
import com.zzh.zhbj.R;
import com.zzh.zhbj.domain.NewsJsonData.NewsJsonTab;

/**
 * �໬�˵�����ҳ--����
 * 
 * @author Administrator
 * 
 */
public class NewsMenuDetailPager extends BaseMenuDeitalPager {
	private ViewPager mNewsViewPager;
	private ArrayList<TabMenuDetail> mTabMenuList;

	private ArrayList<NewsJsonTab> mNewsJsonData;

	private TabPageIndicator indicator;

	private ImageButton ibTabNext;

	public NewsMenuDetailPager(Activity activity,
			ArrayList<NewsJsonTab> children) {
		super(activity);
		mNewsJsonData = children;// ��NewsCenterPager����������childern���ݹ���
	}

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mNewsViewPager = (ViewPager) view
				.findViewById(R.id.vp_news_menu_detail);

		ibTabNext = (ImageButton) view.findViewById(R.id.btn_tab_next);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		return view;
	}

	@Override
	public void initData() {
		// ��ʼ��ҳǩ����
		mTabMenuList = new ArrayList<TabMenuDetail>();
		for (int i = 0; i < mNewsJsonData.size(); i++) {
			TabMenuDetail pager = new TabMenuDetail(mActivity,
					mNewsJsonData.get(i));
			mTabMenuList.add(pager);
		}
		// ����ViewPager������
		mNewsViewPager.setAdapter(new NewsMenuAdapter());

		// ��ʼ��ViewPagerIndicator����
		indicator.setViewPager(mNewsViewPager);

		// Tab��ǩ��һҳͼ����
		ibTabNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int currentItem = mNewsViewPager.getCurrentItem();// ��ȡ��ǰҳǩλ��
				mNewsViewPager.setCurrentItem(currentItem + 1);
			}
		});

	}

	class NewsMenuAdapter extends PagerAdapter {

		@Override
		public CharSequence getPageTitle(int position) {
			return mNewsJsonData.get(position).getTitle();
		}

		@Override
		public int getCount() {
			return mTabMenuList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabMenuDetail pager = mTabMenuList.get(position);
			container.addView(pager.mRootView);
			pager.initData();

			return pager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}
}
