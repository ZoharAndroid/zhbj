package com.zzh.zhbj.pager;

import java.util.ArrayList;

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
import com.zzh.zhbj.menudetail.ActivityMenuDetailPager;
import com.zzh.zhbj.menudetail.BaseMenuDeitalPager;
import com.zzh.zhbj.menudetail.NewsMenuDetailPager;
import com.zzh.zhbj.menudetail.PhotoMenuDetailPager;
import com.zzh.zhbj.menudetail.TopicMenuDetailPager;

public class NewsCenterPager extends BasePager {
	private ArrayList<BaseMenuDeitalPager> mMenuDetailPagerList;
	private NewsJsonData fromJson;// JSon����

	public NewsCenterPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		// ��ʼ������
		tvTitle.setText("����");
		// ����ҳ����ʾ�໬�˵�
		btnTitleMemu.setVisibility(View.VISIBLE);
		// ����SlidingMenu�໬�˵�Ч��
		// SlidingMenu�ɻ���
		setSlidingMenuEnabled(true);

		getDataFromServer();
	}

	/**
	 * �ӷ������������
	 */
	private void getDataFromServer() {
		// ʹ��xtuls���������
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalContacts.CATEGORIES_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// ����ɹ�
						String result = responseInfo.result;
						pareJsonData(result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// ����ʧ��
						Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
								.show();
						System.out.println("�������" + msg);
						error.printStackTrace();

					}
				});

	}

	// ����JSon����
	private void pareJsonData(String result) {
		Gson gson = new Gson();
		fromJson = gson.fromJson(result, NewsJsonData.class);

		// �ѽ������������LeftMenuFragment��
		MainActivity mainUi = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
		leftMenuFragment.setMenuJsonData(fromJson);

		// ��ʼ��4���໬�˵�����ҳ��
		mMenuDetailPagerList = new ArrayList<BaseMenuDeitalPager>();
		mMenuDetailPagerList.add(new NewsMenuDetailPager(mActivity));
		mMenuDetailPagerList.add(new TopicMenuDetailPager(mActivity));
		mMenuDetailPagerList.add(new PhotoMenuDetailPager(mActivity));
		mMenuDetailPagerList.add(new ActivityMenuDetailPager(mActivity));

		// ��NewsCetnerPagerҳ������Ϊ�໬�˵��ĵ�һ��
		setCurrentMenuDetailPager(0);
	}

	/**
	 * ���õ�ǰ�໬�˵�����ҳ
	 * 
	 * @param position
	 */
	public void setCurrentMenuDetailPager(int position) {
		BaseMenuDeitalPager pager = mMenuDetailPagerList.get(position);// ��ȡ��ǰҪ��ʾ�Ĳ˵�����ҳ
		flPagerContent.removeAllViews();//��֮ǰ��ӵ�Fragmentȫ��ɾ��
		flPagerContent.addView(pager.mRootView);// ���˵�����ҳ�Ĳ������ø�֡����
		
		
		//�������ñ���
		tvTitle.setText(fromJson.getData().get(position).getTitle());
	}
}
