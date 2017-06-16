package com.zzh.zhbj.fragment;

import com.zzh.zhbj.R;
import com.zzh.zhbj.domain.NewsJsonData;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 左侧菜单Fragment
 * 
 * @author Administrator
 *
 */
public class LeftMenuFragment extends BaseFragment {
	
	private ListView lvLeftMenu;

	@Override
	public View initFragmentView() {
		View view  = View.inflate(mActivity, R.layout.fragment_menu_left, null);
		lvLeftMenu = (ListView) view.findViewById(R.id.lv_left_menu);
		return view ;
	}
	
	@Override
	public void initData() {
		//lvLeftMenu.setAdapter(new LeftMenuAdpater());
	}
	
	class LeftMenuAdpater extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity, R.layout.item_left_menu_lv, null);
			TextView tvTitle = (TextView) view.findViewById(R.id.tv_left_menu_title);
			
			return view;
		}
		
	}
	
	//给LeftMenu传送Json数据
	public void setMenuJsonData(NewsJsonData fromJson) {
		System.out.println("LeftMenuFragment:"+fromJson.toString());
	}

}
