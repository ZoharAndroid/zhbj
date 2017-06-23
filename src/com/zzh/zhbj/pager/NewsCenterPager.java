package com.zzh.zhbj.pager;

import java.util.ArrayList;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zzh.zhbj.MainActivity;
import com.zzh.zhbj.domain.NewsJsonData;
import com.zzh.zhbj.fragment.LeftMenuFragment;
import com.zzh.zhbj.global.GlobalContacts;
import com.zzh.zhbj.menudetail.ActivityMenuDetailPager;
import com.zzh.zhbj.menudetail.BaseMenuDeitalPager;
import com.zzh.zhbj.menudetail.NewsMenuDetailPager;
import com.zzh.zhbj.menudetail.PhotoMenuDetailPager;
import com.zzh.zhbj.menudetail.TopicMenuDetailPager;
import com.zzh.zhbj.utils.CacheUtils;

/**
 * 新闻
 * 
 * @author Administrator
 *
 */
public class NewsCenterPager extends BasePager {
	private ArrayList<BaseMenuDeitalPager> mMenuDetailPagerList;
	private NewsJsonData fromJson;// JSon数据

	public NewsCenterPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		// 初始化标题
		tvTitle.setText("新闻");
		// 设置页面显示侧滑菜单
		btnTitleMemu.setVisibility(View.VISIBLE);
		// 可以SlidingMenu侧滑菜单效果
		// SlidingMenu可滑动
		setSlidingMenuEnabled(true);
		
		if (TextUtils.isEmpty(CacheUtils.getCache(mActivity, GlobalContacts.CATEGORIES_URL))) {
			//如果缓存为空，就从服务器中获取数据
			getDataFromServer();
		}else{
			//如果有缓存，直接解析
			pareJsonData(CacheUtils.getCache(mActivity, GlobalContacts.CATEGORIES_URL));
			
		}
		
		//不管有没有缓存，先把服务器最新的数据先更新了
		getDataFromServer();
	}

	/**
	 * 从服务器获得数据
	 */
	private void getDataFromServer() {
		// 使用xtuls请求服务器
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalContacts.CATEGORIES_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// 请求成功
						String result = responseInfo.result;
						pareJsonData(result);
						//然后写入缓存
						CacheUtils.setCache(mActivity, GlobalContacts.CATEGORIES_URL, result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// 请求失败
						Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
								.show();
						System.out.println("请求错误：" + msg);
						error.printStackTrace();

					}
				});

	}

	// 解析JSon数据
	private void pareJsonData(String result) {
		
		Gson gson = new Gson();
		fromJson = gson.fromJson(result, NewsJsonData.class);

		// 把解析结果都传到LeftMenuFragment中
		MainActivity mainUi = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
		leftMenuFragment.setMenuJsonData(fromJson);

		// 初始化4个侧滑菜单详情页面
		mMenuDetailPagerList = new ArrayList<BaseMenuDeitalPager>();
		mMenuDetailPagerList.add(new NewsMenuDetailPager(mActivity,fromJson.getData().get(0).getChildren()));
		mMenuDetailPagerList.add(new TopicMenuDetailPager(mActivity));
		mMenuDetailPagerList.add(new PhotoMenuDetailPager(mActivity,btnPhotoStype));
		mMenuDetailPagerList.add(new ActivityMenuDetailPager(mActivity));

		// 把NewsCetnerPager页面设置为侧滑菜单的第一项
		setCurrentMenuDetailPager(0);
	}

	/**
	 * 设置当前侧滑菜单详情页
	 * 
	 * @param position
	 */
	public void setCurrentMenuDetailPager(int position) {
		BaseMenuDeitalPager pager = mMenuDetailPagerList.get(position);// 获取当前要显示的菜单详情页
		flPagerContent.removeAllViews();//把之前添加的Fragment全部删除
		flPagerContent.addView(pager.mRootView);// 将菜单详情页的布局设置给帧布局
		
		//重新设置标题
		tvTitle.setText(fromJson.getData().get(position).getTitle());
		
		//初始化页签详情页
		pager.initData();
		
		if (pager instanceof PhotoMenuDetailPager) {
			btnPhotoStype.setVisibility(View.VISIBLE);
		}else{
			btnPhotoStype.setVisibility(View.INVISIBLE);
		}
	}
}
