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
 * 新闻中心Tab标签
 * 
 * @author Administrator
 * 
 */
public class TabMenuDetail extends BaseMenuDeitalPager {

	private NewsJsonTab mNewsTab;

	private ViewPager mViewPager;

	private TextView mTvTopNewsTitle;// 头条新闻标题

	private String url;// 请求新闻数据链接

	public TabNewsDetailData mTabNewsDetailData;// 解析到的真个新闻数据

	private ArrayList<TopNewsData> mTopNewsList;// 头条新闻数据List

	private CirclePageIndicator indicator;// 头条新闻标题显示指示器

	private RefreashListView mListViewNews;// 新闻列表

	private ArrayList<DetailNewsData> mNewsList;// 新闻数据

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

		// 给自定义ListView设置下拉刷新
		mListViewNews.setOnRefreashListener(new OnRefreashListener() {

			@Override
			public void onRefreash() {
				// 重新从服务器中获取数据
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

		// ListView条目点击事件
		mListViewNews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 记录点击的新闻id
				String stringIds = SharePreUtils.getSPString(mActivity,
						"item_news_read_id", "");
				String itemId = mNewsList.get(position).getId();
				if (!stringIds.contains(itemId)) {
					stringIds = stringIds + itemId + ",";
					SharePreUtils.setSPString(mActivity, "item_news_read_id",
							stringIds);
				}
				//mNewsAdapter.notifyDataSetChanged();
				changeReadedState(view);//实现局部item颜色变幻
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
		// 从服务器获取数据
		getDataFromServer();
	}

	/**
	 * 从服务器获取数据
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				praseJsonData(result, false);

				// 下拉刷新完成之后隐藏
				mListViewNews.onRefreashComplete();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, 0).show();
				error.printStackTrace();

				// 下拉刷新完成之后隐藏
				mListViewNews.onRefreashComplete();
			}
		});
	}

	/**
	 * 从服务器中获取加载更多的数据
	 */
	private void getMoreDataFromeServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mLodingMoreUrl,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						praseJsonData(result, true);

						// 下拉刷新完成之后隐藏
						mListViewNews.onRefreashComplete();
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, msg, 0).show();
						error.printStackTrace();

						// 下拉刷新完成之后隐藏
						mListViewNews.onRefreashComplete();
					}
				});
	}

	/**
	 * 解析Json数据
	 * 
	 * @param result
	 * @param isLoadingMore
	 */
	private void praseJsonData(String result, boolean isLoadingMore) {
		Gson gson = new Gson();
		mTabNewsDetailData = gson.fromJson(result, TabNewsDetailData.class);
		// 获取加载更多连接:处理更多页面的逻辑
		String more = mTabNewsDetailData.getData().getMore();
		if (!TextUtils.isEmpty(more)) {
			mLodingMoreUrl = GlobalContacts.BASE_URL + more;
		} else {
			mLodingMoreUrl = null;
		}

		if (isLoadingMore) {
			// 加载更多
			ArrayList<DetailNewsData> newsList = mTabNewsDetailData.getData()
					.getNews();
			mNewsList.addAll(newsList);// 在原来的集合中添加更多的数据
			mNewsAdapter.notifyDataSetChanged();// 通知数据修改

		} else {
			// 分类新闻数据：头条新闻数据和新闻数据
			mTopNewsList = mTabNewsDetailData.getData().getTopnews();// 新闻头条数据
			mNewsList = mTabNewsDetailData.getData().getNews();// 新闻数据

			if (!mTopNewsList.isEmpty()) {
				// 给初始头条设置标题
				mTvTopNewsTitle.setText(mTopNewsList.get(0).getTitle());

				// 给头条ViewPager设置适配器
				mViewPager.setAdapter(new TopDetaiAdapter());

				// 设置头条新闻显示指示器
				indicator.onPageSelected(0);// 设置默认选择第一个点
				indicator.setViewPager(mViewPager);
				indicator.setSnap(true);
				// 给ViewPager设置监事件
				indicator.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						// 把从服务器获取下来的数据：头条新闻标题设置到标题栏
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
	 * ListView新闻数据列表
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
	 * 新闻头条的ViewPager
	 * 
	 * @author Administrator
	 * 
	 */
	class TopDetaiAdapter extends PagerAdapter {

		private BitmapUtils utils;

		public TopDetaiAdapter() {
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.topnews_item_default);// 设置加载默认图片
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
