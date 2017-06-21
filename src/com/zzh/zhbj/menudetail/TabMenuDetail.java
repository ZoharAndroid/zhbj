package com.zzh.zhbj.menudetail;

import java.util.ArrayList;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.viewpagerindicator.CirclePageIndicator;
import com.zzh.zhbj.R;
import com.zzh.zhbj.domain.NewsJsonData.NewsJsonTab;
import com.zzh.zhbj.domain.TabNewsDetailData;
import com.zzh.zhbj.domain.TabNewsDetailData.TabDetailData.DetailNewsData;
import com.zzh.zhbj.domain.TabNewsDetailData.TabDetailData.TopNewsData;
import com.zzh.zhbj.global.GlobalContacts;
import com.zzh.zhbj.utils.SharePreUtils;
import com.zzh.zhbj.view.RefreashListView;
import com.zzh.zhbj.view.RefreashListView.OnRefreashListener;

/**
 * ��������Tab��ǩ
 * 
 * @author Administrator
 * 
 */
public class TabMenuDetail extends BaseMenuDeitalPager {

	private NewsJsonTab mNewsTab;

	private ViewPager mViewPager;

	private TextView mTvTopNewsTitle;// ͷ�����ű���

	private String url;// ����������������

	public TabNewsDetailData mTabNewsDetailData;// �������������������

	private ArrayList<TopNewsData> mTopNewsList;// ͷ����������List

	private CirclePageIndicator indicator;// ͷ�����ű�����ʾָʾ��

	private RefreashListView mListViewNews;// �����б�

	private ArrayList<DetailNewsData> mNewsList;// ��������

	private String mLodingMoreUrl;

	private NewsAdapter mNewsAdapter;

	public TabMenuDetail(Activity activity, NewsJsonTab newsJsonTab) {
		super(activity);
		mNewsTab = newsJsonTab;
		url = GlobalContacts.BASE_URL + mNewsTab.getUrl();
	}

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.tab_news_detail, null);
		View headerView = View.inflate(mActivity,
				R.layout.item_hear_view_top_news, null);

		mViewPager = (ViewPager) headerView.findViewById(R.id.vp_news_detail);
		mTvTopNewsTitle = (TextView) headerView
				.findViewById(R.id.tv_top_news_title);
		indicator = (CirclePageIndicator) headerView
				.findViewById(R.id.indicator);
		mListViewNews = (RefreashListView) view.findViewById(R.id.lv_news_list);

		mListViewNews.addHeaderView(headerView);

		// ���Զ���ListView��������ˢ��
		mListViewNews.setOnRefreashListener(new OnRefreashListener() {

			@Override
			public void onRefreash() {
				// ���´ӷ������л�ȡ����
				getDataFromServer();
			}

			@Override
			public void onLoadingMore() {
				if (mLodingMoreUrl != null) {
					getMoreDataFromeServer();
				} else {
					mListViewNews.onRefreashComplete();
				}

			}
		});

		// ListView��Ŀ����¼�
		mListViewNews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ��¼���������id
				String stringIds = SharePreUtils.getSPString(mActivity,
						"item_news_read_id", "");
				String itemId = mNewsList.get(position).getId();
				if (!stringIds.contains(itemId)) {
					stringIds = stringIds + itemId + ",";
					SharePreUtils.setSPString(mActivity, "item_news_read_id",
							stringIds);
				}
				//mNewsAdapter.notifyDataSetChanged();
				changeReadedState(view);//ʵ�־ֲ�item��ɫ���
			}
		});
		return view;
	}

	
	protected void changeReadedState(View view) {
		TextView tvTitle = (TextView) view.findViewById(R.id.tv_news_title);
		tvTitle.setTextColor(Color.GRAY);
	}

	@Override
	public void initData() {
		// �ӷ�������ȡ����
		getDataFromServer();
	}

	/**
	 * �ӷ�������ȡ����
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				praseJsonData(result, false);

				// ����ˢ�����֮������
				mListViewNews.onRefreashComplete();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, 0).show();
				error.printStackTrace();

				// ����ˢ�����֮������
				mListViewNews.onRefreashComplete();
			}
		});
	}

	/**
	 * �ӷ������л�ȡ���ظ��������
	 */
	private void getMoreDataFromeServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mLodingMoreUrl,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						praseJsonData(result, true);

						// ����ˢ�����֮������
						mListViewNews.onRefreashComplete();
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, msg, 0).show();
						error.printStackTrace();

						// ����ˢ�����֮������
						mListViewNews.onRefreashComplete();
					}
				});
	}

	/**
	 * ����Json����
	 * 
	 * @param result
	 * @param isLoadingMore
	 */
	private void praseJsonData(String result, boolean isLoadingMore) {
		Gson gson = new Gson();
		mTabNewsDetailData = gson.fromJson(result, TabNewsDetailData.class);
		// ��ȡ���ظ�������:�������ҳ����߼�
		String more = mTabNewsDetailData.getData().getMore();
		if (!TextUtils.isEmpty(more)) {
			mLodingMoreUrl = GlobalContacts.BASE_URL + more;
		} else {
			mLodingMoreUrl = null;
		}

		if (isLoadingMore) {
			// ���ظ���
			ArrayList<DetailNewsData> newsList = mTabNewsDetailData.getData()
					.getNews();
			mNewsList.addAll(newsList);// ��ԭ���ļ�������Ӹ��������
			mNewsAdapter.notifyDataSetChanged();// ֪ͨ�����޸�

		} else {
			// �����������ݣ�ͷ���������ݺ���������
			mTopNewsList = mTabNewsDetailData.getData().getTopnews();// ����ͷ������
			mNewsList = mTabNewsDetailData.getData().getNews();// ��������

			if (!mTopNewsList.isEmpty()) {
				// ����ʼͷ�����ñ���
				mTvTopNewsTitle.setText(mTopNewsList.get(0).getTitle());

				// ��ͷ��ViewPager����������
				mViewPager.setAdapter(new TopDetaiAdapter());

				// ����ͷ��������ʾָʾ��
				indicator.onPageSelected(0);// ����Ĭ��ѡ���һ����
				indicator.setViewPager(mViewPager);
				indicator.setSnap(true);
				// ��ViewPager���ü��¼�
				indicator.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						// �Ѵӷ�������ȡ���������ݣ�ͷ�����ű������õ�������
						mTvTopNewsTitle.setText(mTopNewsList.get(position)
								.getTitle());
					}

					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {

					}

					@Override
					public void onPageScrollStateChanged(int state) {

					}
				});
			}

			if (!mNewsList.isEmpty()) {
				mNewsAdapter = new NewsAdapter();
				mListViewNews.setAdapter(mNewsAdapter);
			}
		}
	}

	/**
	 * ListView���������б�
	 * 
	 * @author Administrator
	 * 
	 */
	class NewsAdapter extends BaseAdapter {
		private BitmapUtils utils;

		public NewsAdapter() {
			utils = new BitmapUtils(mActivity);
			// utils.configDefaultLoadingImage(R.drawable.news_default_icon);
		}

		@Override
		public int getCount() {
			return mNewsList.size();
		}

		@Override
		public Object getItem(int position) {
			return mNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(mActivity,
						R.layout.item_tab_news_detail, null);
				holder = new ViewHolder();
				holder.ivImage = (ImageView) convertView
						.findViewById(R.id.iv_news_image);
				holder.tvDate = (TextView) convertView
						.findViewById(R.id.tv_news_date);
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_news_title);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tvTitle.setText(mNewsList.get(position).getTitle());
			holder.tvDate.setText(mNewsList.get(position).getPubdate());
			// utils.display(holder.ivImage, mNewsList.get(position)
			// .getListimage());
			
			String stringIds = SharePreUtils.getSPString(mActivity,
					"item_news_read_id", "");
			if (stringIds.contains(mNewsList.get(position).getId())) {
				 holder.tvTitle.setTextColor(0x99000000);
			}else{
				holder.tvTitle.setTextColor(Color.BLACK);
			}
			return convertView;
		}

	}

	class ViewHolder {
		private TextView tvTitle;
		private TextView tvDate;
		private ImageView ivImage;
	}

	/**
	 * ����ͷ����ViewPager
	 * 
	 * @author Administrator
	 * 
	 */
	class TopDetaiAdapter extends PagerAdapter {

		private BitmapUtils utils;

		public TopDetaiAdapter() {
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.topnews_item_default);// ���ü���Ĭ��ͼƬ
		}

		@Override
		public int getCount() {
			return mTabNewsDetailData.data.getTopnews().size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView image = new ImageView(mActivity);
			image.setScaleType(ScaleType.FIT_XY);
			image.setBackgroundResource(R.drawable.news_default_icon);
			String topUrl = mTabNewsDetailData.getData().getTopnews()
					.get(position).getTopimage();
			// utils.display(image, topUrl);
			container.addView(image);
			return image;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}
}
