package com.zzh.zhbj.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.zzh.zhbj.R;
import com.zzh.zhbj.domain.NewsJsonData.NewsJsonTab;

/**
 * 侧滑菜单详情页--新闻
 * 
 * @author Administrator
 * 
 */
public class NewsMenuDetailPager extends BaseMenuDeitalPager {
	private ViewPager mNewsViewPager;
	private ArrayList<TabMenuDetail> mTabMenuList;

	private ArrayList<NewsJsonTab> mNewsJsonData;

	public NewsMenuDetailPager(Activity activity,
			ArrayList<NewsJsonTab> children) {
		super(activity);
		mNewsJsonData = children;// 将NewsCenterPager解析出来的childern传递过来
	}

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mNewsViewPager = (ViewPager) view
				.findViewById(R.id.vp_news_menu_detail);

		return view;
	}

	@Override
	public void initData() {
		// 初始化页签数据
		mTabMenuList = new ArrayList<TabMenuDetail>();
		for (int i = 0; i < mNewsJsonData.size(); i++) {
			TabMenuDetail pager = new TabMenuDetail(mActivity,mNewsJsonData.get(i));
			mTabMenuList.add(pager);
		}
		
		mNewsViewPager.setAdapter(new NewsMenuAdapter());
	}

	class NewsMenuAdapter extends PagerAdapter {

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
