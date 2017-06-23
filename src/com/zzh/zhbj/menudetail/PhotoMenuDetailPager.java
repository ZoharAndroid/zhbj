package com.zzh.zhbj.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zzh.zhbj.R;
import com.zzh.zhbj.domain.PhototDetail;
import com.zzh.zhbj.domain.PhototDetail.PhotoInfo;
import com.zzh.zhbj.global.GlobalContacts;
import com.zzh.zhbj.utils.CacheUtils;

/**
 * �໬�˵�����ҳ--��ͼ
 * 
 * @author Administrator
 * 
 */
public class PhotoMenuDetailPager extends BaseMenuDeitalPager {

	private ListView lvListPhoto;
	private GridView gvGridPhoto;
	private PhotoAdatepr mPhotoAdapter;
	private ArrayList<PhotoInfo> mPhotoNewsList;
	
	private ImageButton btnPhotoShow;

	public PhotoMenuDetailPager(Activity activity, ImageButton btnPhotoStype) {
		super(activity);
		this.btnPhotoShow = btnPhotoStype;
		
		btnPhotoShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeShow();
			}
		});
	}

	private boolean isListViewShow = true;
	protected void changeShow() {
		if (isListViewShow) {
			//�����ListView��ʾ,ͼ����
			isListViewShow = false;
			lvListPhoto.setVisibility(View.GONE);
			gvGridPhoto.setVisibility(View.VISIBLE);
			btnPhotoShow.setImageResource(R.drawable.icon_pic_list_type);
			
		}else{
			isListViewShow=true;
			lvListPhoto.setVisibility(View.VISIBLE);
			gvGridPhoto.setVisibility(View.GONE);
			
			btnPhotoShow.setImageResource(R.drawable.icon_pic_grid_type);
		}
	}

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.photo_pager_list, null);
		lvListPhoto = (ListView) view.findViewById(R.id.lv_photo_list);
		gvGridPhoto = (GridView) view.findViewById(R.id.gv_photo_list);

		return view;
	}

	@Override
	public void initData() {
		String cache = CacheUtils
				.getCache(mActivity, GlobalContacts.PHOTOS_URL);
		if (!TextUtils.isEmpty(cache)) {
			praseJsonData(cache);
		}
		getDataFromServer();
	}

	/**
	 * �ӷ�������ȡ����
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalContacts.PHOTOS_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						praseJsonData(result);
						// ���û���
						CacheUtils.setCache(mActivity,
								GlobalContacts.PHOTOS_URL, result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, msg, 0).show();
						error.printStackTrace();
					}

				});
	}

	/**
	 * ��������
	 * 
	 * @param result
	 */
	protected void praseJsonData(String result) {
		Gson gson = new Gson();
		PhototDetail phototDetail = gson.fromJson(result, PhototDetail.class);
		mPhotoNewsList = phototDetail.getData().getNews();
		
		if (mPhotoNewsList != null) {
			mPhotoAdapter = new PhotoAdatepr();
			lvListPhoto.setAdapter(mPhotoAdapter);
			gvGridPhoto.setAdapter(mPhotoAdapter);
		}
	}

	/**
	 * ��ͼ�����б�Adapter
	 * 
	 * @author Administrator
	 * 
	 */
	class PhotoAdatepr extends BaseAdapter {
		
		private BitmapUtils utils;

		public PhotoAdatepr(){
			utils = new BitmapUtils(mActivity);
			
		}

		@Override
		public int getCount() {
			return mPhotoNewsList.size();
		}

		@Override
		public Object getItem(int position) {
			return mPhotoNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder =null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(mActivity, R.layout.item_photo_menu, null);
				
				holder.ivPhoto = (ImageView) convertView.findViewById(R.id.iv_photo_show);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_photo_title);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.tvTitle.setText(mPhotoNewsList.get(position).getTitle());
			//utils.display(holder.ivPhoto, mPhotoNewsList.get(position).getListimage());
			holder.ivPhoto.setBackgroundResource(R.drawable.news_default_icon);
			return convertView;
		}

	}
	
	class ViewHolder {
		ImageView ivPhoto;
		TextView tvTitle;
	}

}
