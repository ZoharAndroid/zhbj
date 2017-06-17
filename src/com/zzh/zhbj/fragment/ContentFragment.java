package com.zzh.zhbj.fragment;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zzh.zhbj.R;
import com.zzh.zhbj.pager.BasePager;
import com.zzh.zhbj.pager.GovermentPager;
import com.zzh.zhbj.pager.HomePager;
import com.zzh.zhbj.pager.NewsCenterPager;
import com.zzh.zhbj.pager.SettingPager;
import com.zzh.zhbj.pager.SmartServicePager;

/**
 * 首页内容Fragment
 * 
 * @author Administrator
 * 
 */
public class ContentFragment extends BaseFragment {

	private RadioGroup rgTabGroup;// radioGruop Tab集合
	private ViewPager vpPagerContent;

	private ArrayList<BasePager> mPagerList = new ArrayList<BasePager>();

	@Override
	public View initFragmentView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		rgTabGroup = (RadioGroup) view.findViewById(R.id.rg_tab_group);
		vpPagerContent = (ViewPager) view.findViewById(R.id.vp_content);
		return view;
	}

	@Override
	public void initData() {
		// 默认勾选首页
		rgTabGroup.check(R.id.rb_home);

		// 初始化五个界面
		mPagerList.add(new HomePager(mActivity));
		mPagerList.add(new NewsCenterPager(mActivity));
		mPagerList.add(new SmartServicePager(mActivity));
		mPagerList.add(new GovermentPager(mActivity));
		mPagerList.add(new SettingPager(mActivity));
		
		//加载五个界面
		vpPagerContent.setAdapter(new PagerContentAdapter());
		
		//默认加载第一个界面
		mPagerList.get(0).initData();
		
		//点击5geTab可以切换界面
		rgTabGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					vpPagerContent.setCurrentItem(0, false);//加载第一个界面，禁止ViewPager的滑动效果
					break;
				case R.id.rb_newscenter:
					vpPagerContent.setCurrentItem(1, false);//加载第一个界面，禁止ViewPager的滑动效果
					break;
				case R.id.rb_smartservice:
					vpPagerContent.setCurrentItem(2, false);//加载第一个界面，禁止ViewPager的滑动效果
					break;
				case R.id.rb_goverment:
					vpPagerContent.setCurrentItem(3, false);//加载第一个界面，禁止ViewPager的滑动效果
					break;
				case R.id.rb_setting:
					vpPagerContent.setCurrentItem(4, false);//加载第一个界面，禁止ViewPager的滑动效果
					break;
				default:
					break;
				}
			}
		});
		
		
		vpPagerContent.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// 每次加载选中的界面
				mPagerList.get(position).initData();
				
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
	}
	
	/**
	 * ViewPager 每个Pager的页面的适配器 
	 * 
	 * @author Administrator
	 *
	 */
	class PagerContentAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagerList.get(position);
			container.addView(pager.mRootView);
			return pager.mRootView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
		
	}

	/**
	 * 从集合从获得NewsCenterPager页面
	 * 
	 * @return
	 */
	public NewsCenterPager getNewsCenterPager(){
		return (NewsCenterPager) mPagerList.get(1);
	}
}
