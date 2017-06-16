package com.zzh.zhbj.fragment;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzh.zhbj.R;
import com.zzh.zhbj.domain.NewsJsonData;
import com.zzh.zhbj.domain.NewsJsonData.NewsJsonMenu;

/**
 * 左侧菜单Fragment
 * 
 * @author Administrator
 * 
 */
public class LeftMenuFragment extends BaseFragment {

	private ListView lvLeftMenu;

	private ArrayList<NewsJsonMenu> mMenuList;

	private int mCurrentPostion;// 点击ListView的item位置;

	private LeftMenuAdpater mMenuAdapte;

	@Override
	public View initFragmentView() {
		View view = View.inflate(mActivity, R.layout.fragment_menu_left, null);
		lvLeftMenu = (ListView) view.findViewById(R.id.lv_left_menu);
		
		lvLeftMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("这个方法调用了吗？");
				mCurrentPostion = position;
				System.out.println(mCurrentPostion);
				// 中心刷新Listview数据
				mMenuAdapte.notifyDataSetChanged();
				Toast.makeText(mActivity, "你点击了:"+position, 0).show();
			}
			
		});
		return view;
	}

	@Override
	public void initData() {
		
	}

	class LeftMenuAdpater extends BaseAdapter {

		@Override
		public int getCount() {
			return mMenuList.size();
		}

		@Override
		public Object getItem(int position) {
			return mMenuList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity, R.layout.item_left_menu_lv,
					null);
			TextView tvTitle = (TextView) view
					.findViewById(R.id.tv_left_menu_title);
			NewsJsonMenu menu = mMenuList.get(position);

			tvTitle.setText(menu.getTitle());

			if (mCurrentPostion == position) {
				tvTitle.setEnabled(true);
			} else {
				tvTitle.setEnabled(false);
			}
			return view;
		}

	}

	// 给LeftMenu传送Json数据
	public void setMenuJsonData(NewsJsonData newsData) {
		mMenuList = newsData.data;
		mMenuAdapte = new LeftMenuAdpater();
		lvLeftMenu.setAdapter(mMenuAdapte);
	}

}
