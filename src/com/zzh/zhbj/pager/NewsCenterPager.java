package com.zzh.zhbj.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
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

public class NewsCenterPager extends BasePager {

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

		// 初始化Home主页内容
		TextView tv = new TextView(mActivity);
		tv.setText("新闻中心");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		flPagerContent.addView(tv);

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
		NewsJsonData fromJson = gson.fromJson(result, NewsJsonData.class);
		// 把解析结果都传到LeftMenuFragment中

		MainActivity mainUi = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
		leftMenuFragment.setMenuJsonData(fromJson);

	}
}
