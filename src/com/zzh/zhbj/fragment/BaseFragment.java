package com.zzh.zhbj.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment�Ļ���
 * 
 * @author Administrator
 * 
 */
public abstract class BaseFragment extends Fragment {
	public Activity mActivity;
	// fragment����
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	// ����fragment����
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initFragmentView();
	}

	// ����Activity���
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		initData();
	}
	
	//��ʼ��Fragment����
	public abstract View initFragmentView();
	
	//��ʼ������
	public void initData(){
		
	}
}
