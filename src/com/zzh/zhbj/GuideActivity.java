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

	// ����ҳ��ͼƬ��Դid
	private static final int[] imageIds = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };
	// �洢ImageView
	private ArrayList<ImageView> mImageViewList = new ArrayList<ImageView>();

	private ViewPager vpGuide;
	private Button btnStart;
	private LinearLayout llPointGroup;
	private View guideRedPonit;// guide���

	private int ponitDistance;// ����������ľ���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		vpGuide = (ViewPager) findViewById(R.id.vp_activity_guide);
		btnStart = (Button) findViewById(R.id.btn_guide_activity_start);
		llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group_guide_activity);
		guideRedPonit = findViewById(R.id.vw_read_point_guide);

		// ��ʼ��ViewPager����
		initView();
		// ViewPager����������
		vpGuide.setAdapter(new GuideAdapter());
		// ��Ҫ����ҳ�ƶ�С���ͱ���Ҫ����ViewPager�Ļ���ʱ��
		vpGuide.setOnPageChangeListener(new GuideViewPagerListenr());
		// ��ʼ���鰴ť
		btnStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//��ת�������沢�Ѳ���¼����ҳ��Ĵ���д�뵽sharepreference��
				SharePreUtils.setSPBoolean(GuideActivity.this, GlobalContacts.FIRST_GUIDE, true);
				
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
			}
		});
	}

	// ��ʼ��ViewPager����
	private void initView() {
		for (int i = 0; i < imageIds.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(imageIds[i]);
			mImageViewList.add(image);
		}

		// ��̬���뻬����¼��СԲ��
		for (int i = 0; i < imageIds.length; i++) {
			View point = new View(this);
			point.setBackgroundResource(R.drawable.point_gary_guide_activity_shape);
			LayoutParams params = new LayoutParams(10, 10);
			if (i > 0) {// ����һԭ�����⣬ÿ��ԭ�������
				params.leftMargin = 10;
			}
			point.setLayoutParams(params);// ����ԭ�����
			llPointGroup.addView(point);
		}

		/*
		 * ��ȡ����ҳ������ ��ļ������:
		 * ��oncreate�ķ����л�ȡ���룬��������⣬��ȡ��������ľ���Ϊ0����Ϊ����ʾ�����ļ��ǣ�ϵͳ������������ measure��������
		 * layout��λ�ã� ondraw����ͼ����layout��û��Ū�꣬�Ϳ�ʼ��������������������
		 * �ʣ�ʹ��getViewTreeObserver������ͼ���۲����е�Layout�����������ɡ�
		 */
		llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// ϵͳLayout�����ͻ���ø÷���
						ponitDistance = llPointGroup.getChildAt(1).getLeft()
								- llPointGroup.getChildAt(0).getLeft();
						// ��ΪonGlobalLayout()�����������������������ڵ���һ�ε�ʱ��ͽ�������
						llPointGroup.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						// llPointGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);//API16���Ͽ���ʹ�����
					}
				});
	}

	/**
	 * ViewPager�Ļ��������¼�
	 * 
	 * @author Administrator
	 * 
	 */
	class GuideViewPagerListenr implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			/**
			 * ViewPager��ҳ�滬��,���������� position: ViewPager��ǰҳ��index��0��ʼ float
			 * positionOffset:�����İٷֱ�0~1 positionOffsetPixels��ƫ������
			 */
			// ��ȡС������
			android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) guideRedPonit
					.getLayoutParams();
			// �ı�С���Ĳ���:�޸ľ���
			params.leftMargin = (int) (ponitDistance * positionOffset + position
					* ponitDistance);
			guideRedPonit.setLayoutParams(params);
		}

		@Override
		public void onPageSelected(int position) {
			// ѡ�����һ��ҳ����ʾ��ʼ���鰴ť
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
	 * GuideActivity��ViewPager��������
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
