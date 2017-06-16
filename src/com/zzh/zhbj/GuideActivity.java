package com.zzh.zhbj;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.zzh.zhbj.global.GlobalContacts;
import com.zzh.zhbj.utils.SharePreUtils;

public class GuideActivity extends Activity {

	// 引导页面图片资源id
	private static final int[] imageIds = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };
	// 存储ImageView
	private ArrayList<ImageView> mImageViewList = new ArrayList<ImageView>();

	private ViewPager vpGuide;
	private Button btnStart;
	private LinearLayout llPointGroup;
	private View guideRedPonit;// guide红点

	private int ponitDistance;// 两个引导点的距离

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 消除标题栏
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		vpGuide = (ViewPager) findViewById(R.id.vp_activity_guide);
		btnStart = (Button) findViewById(R.id.btn_guide_activity_start);
		llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group_guide_activity);
		guideRedPonit = findViewById(R.id.vw_read_point_guide);

		// 初始化ViewPager数据
		initView();
		// ViewPager设置适配器
		vpGuide.setAdapter(new GuideAdapter());
		// 想要引导页移动小红点就必须要监听ViewPager的滑动时间
		vpGuide.setOnPageChangeListener(new GuideViewPagerListenr());
		// 开始体验按钮
		btnStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//跳转到主界面并把并记录引导页面的次数写入到sharepreference中
				SharePreUtils.setSPBoolean(GuideActivity.this, GlobalContacts.FIRST_GUIDE, true);
				
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
			}
		});
	}

	// 初始化ViewPager数据
	private void initView() {
		for (int i = 0; i < imageIds.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(imageIds[i]);
			mImageViewList.add(image);
		}

		// 动态加入滑动记录的小圆点
		for (int i = 0; i < imageIds.length; i++) {
			View point = new View(this);
			point.setBackgroundResource(R.drawable.point_gary_guide_activity_shape);
			LayoutParams params = new LayoutParams(10, 10);
			if (i > 0) {// 除第一原点以外，每个原点相隔开
				params.leftMargin = 10;
			}
			point.setLayoutParams(params);// 设置原点参数
			llPointGroup.addView(point);
		}

		/*
		 * 获取引导页面两个 点的间隔距离:
		 * 在oncreate的方法中获取距离，会出现问题，获取的两个点的距离为0。因为在显示布局文件是，系统会有三个步骤 measure（测量）
		 * layout（位置） ondraw（绘图），layout还没有弄完，就开始测量，会测量不出结果。
		 * 故，使用getViewTreeObserver（）视图树观察者中的Layout监听方法即可。
		 */
		llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// 系统Layout结束就会调用该方法
						ponitDistance = llPointGroup.getChildAt(1).getLeft()
								- llPointGroup.getChildAt(0).getLeft();
						// 因为onGlobalLayout()这个方法会调用三个，所以在调用一次的时候就结束监听
						llPointGroup.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						// llPointGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);//API16以上可以使用这个
					}
				});
	}

	/**
	 * ViewPager的滑动监听事件
	 * 
	 * @author Administrator
	 * 
	 */
	class GuideViewPagerListenr implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			/**
			 * ViewPager的页面滑动,三个参数： position: ViewPager当前页的index：0开始 float
			 * positionOffset:滑动的百分比0~1 positionOffsetPixels：偏移像素
			 */
			// 获取小红点参数
			android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) guideRedPonit
					.getLayoutParams();
			// 改变小红点的参数:修改距离
			params.leftMargin = (int) (ponitDistance * positionOffset + position
					* ponitDistance);
			guideRedPonit.setLayoutParams(params);
		}

		@Override
		public void onPageSelected(int position) {
			// 选中最后一个页面显示开始体验按钮
			if (position == imageIds.length - 1) {
				btnStart.setVisibility(View.VISIBLE);
			} else {
				btnStart.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * GuideActivity的ViewPager的适配器
	 * 
	 * @author Administrator
	 * 
	 */
	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageIds.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mImageViewList.get(position));
			return mImageViewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

}
