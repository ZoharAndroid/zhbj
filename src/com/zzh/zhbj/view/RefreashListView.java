package com.zzh.zhbj.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zzh.zhbj.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 下拉刷新的ListView
 * 
 * @author Administrator
 * 
 */
public class RefreashListView extends ListView implements android.widget.AdapterView.OnItemClickListener {
	public static final int STATE_REFRESH_PULL = 0;// 下拉刷新状态
	public static final int STATE_REFRESH_RELEASE = 1;// 释放下拉刷新状态
	public static final int STATE_REFRESH_ING = 2;// 正在刷新

	private int mCurrentState = STATE_REFRESH_PULL;// 当前刷新状态为下拉刷新

	private ImageView ivRefreashImage;
	private ProgressBar pbProgress;
	private TextView tvRefreashState;
	private TextView tvRefreashDate;

	private int startY = -1;
	private View mHeaderView;
	private int mMeasuredHeight;
	private RotateAnimation imageUpAnim;
	private RotateAnimation imageDownAnim;

	private View mFooterView;
	private int mFooterHeigt;

	public RefreashListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeaderView();
		initFooterView();
	}

	public RefreashListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
	}

	public RefreashListView(Context context) {
		super(context);
		initHeaderView();
		initFooterView();
	}

	/**
	 * 初始化头布局
	 */
	private void initHeaderView() {
		mHeaderView = View.inflate(getContext(),
				R.layout.item_refreash_header_list, null);
		this.addHeaderView(mHeaderView);

		ivRefreashImage = (ImageView) mHeaderView
				.findViewById(R.id.iv_down_up_icon);
		pbProgress = (ProgressBar) mHeaderView.findViewById(R.id.pb_progress);
		tvRefreashState = (TextView) mHeaderView
				.findViewById(R.id.tv_refreash_state);
		tvRefreashDate = (TextView) mHeaderView
				.findViewById(R.id.tv_refreash_date);

		// 隐藏刷新的布局
		mHeaderView.measure(0, 0);
		mMeasuredHeight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -mMeasuredHeight, 0, 0);

		refreashImageAnim();// 初始刷新动画

		// 初始设置刷新时间
		tvRefreashDate.setText("最后刷新:" + getCurrentTime());
	}

	private boolean isLoadingMore = false;// 默认加载更多标记为false

	/**
	 * 初始化脚布局
	 */
	public void initFooterView() {
		mFooterView = View.inflate(getContext(),
				R.layout.item_refreash_footer_list, null);
		// ListView添加脚布局
		this.addFooterView(mFooterView);
		// 隐藏角布局
		mFooterView.measure(0, 0);
		mFooterHeigt = mFooterView.getMeasuredHeight();
		mFooterView.setPadding(0, -mFooterHeigt, 0, 0);

		// 监听滑动状态
		this.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 如果滑动状态为：停止滑动活着快速滑动时
				if (scrollState == SCROLL_STATE_IDLE
						|| scrollState == SCROLL_STATE_FLING) {
					if (getLastVisiblePosition() == (getCount() - 1)
							&& !isLoadingMore) {
						// 如果是最后一个位置，就显示角布局
						mFooterView.setPadding(0, 0, 0, 0);
						// 把头布局直接显示在最后一项
						setSelection(getCount() - 1);
						// 并把标记设置成true
						isLoadingMore = true;
						
						if (mRefreashListener!=null) {
							mRefreashListener.onLoadingMore();//加载更多
						}
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:// 按下
			startY = (int) ev.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:// 移动
			if (startY == -1) {// 确保获取到Y的移动值
				startY = (int) ev.getRawY();
			}

			if (mCurrentState == STATE_REFRESH_ING) {
				break;
			}

			int endY = (int) ev.getRawY();

			int dy = endY - startY;
			if (dy > 0 && getFirstVisiblePosition() == 0) {// 确保向下滑动和第一个显示的位置的时候才出现下拉刷新
				int padding = dy - mMeasuredHeight;
				mHeaderView.setPadding(0, padding, 0, 0);

				if (padding > 0 && mCurrentState != STATE_REFRESH_RELEASE) {// 如果滑动的padding
																			// 距离大于0：说明是释放刷新
					// 改变状态
					mCurrentState = STATE_REFRESH_RELEASE;
					changeRefreshState();
				} else if (padding < 0 && mCurrentState != STATE_REFRESH_PULL) {// 下拉刷新状态
					mCurrentState = STATE_REFRESH_PULL;
					changeRefreshState();
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:// 抬起
			// 重置startY
			startY = -1;

			if (mCurrentState == STATE_REFRESH_PULL) {// 如果等于下拉刷新状态
				// 隐藏下拉刷新列表
				mHeaderView.setPadding(0, -mMeasuredHeight, 0, 0);
			} else if (mCurrentState == STATE_REFRESH_RELEASE) {// 如果等于释放刷新状态
				mCurrentState = STATE_REFRESH_ING;
				// 显示下拉刷新列表
				mHeaderView.setPadding(0, 0, 0, 0);
				changeRefreshState();
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 改变刷新状态的一些操作
	 * 
	 */
	private void changeRefreshState() {
		switch (mCurrentState) {
		case STATE_REFRESH_PULL:
			tvRefreashState.setText("下拉刷新");
			ivRefreashImage.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			ivRefreashImage.startAnimation(imageDownAnim);
			break;
		case STATE_REFRESH_RELEASE:
			tvRefreashState.setText("松开刷新");
			ivRefreashImage.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			ivRefreashImage.startAnimation(imageUpAnim);
			break;
		case STATE_REFRESH_ING:
			tvRefreashState.setText("正在刷新...");
			ivRefreashImage.clearAnimation();// 必须先清除动画，才能隐藏
			ivRefreashImage.setVisibility(View.INVISIBLE);
			pbProgress.setVisibility(View.VISIBLE);

			if (mRefreashListener != null) {
				mRefreashListener.onRefreash();// 调用刷新
			}
			break;
		default:
			break;
		}
	}

	private void refreashImageAnim() {
		imageUpAnim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		imageUpAnim.setDuration(200);
		imageUpAnim.setFillAfter(true);

		imageDownAnim = new RotateAnimation(-180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		imageDownAnim.setDuration(200);
		imageDownAnim.setFillAfter(true);

	}

	/**
	 * 下拉刷新完成后的操作
	 */
	public void onRefreashComplete() {
		if (isLoadingMore) {
			// 角布局：加载更多
			// 隐藏角布局
			mFooterView.setPadding(0, -mFooterHeigt, 0, 0);
			isLoadingMore = false;
		} else {
			mHeaderView.setPadding(0, -mMeasuredHeight, 0, 0);// 隐藏标题栏
			// 把显示的字和图片隐藏了
			tvRefreashState.setText("下拉刷新");
			ivRefreashImage.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			// 改变当前状态
			mCurrentState = STATE_REFRESH_PULL;

			tvRefreashDate.setText("最后刷新:" + getCurrentTime());
		}
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	OnRefreashListener mRefreashListener;

	public void setOnRefreashListener(OnRefreashListener onRefreashListener) {
		mRefreashListener = onRefreashListener;
	}

	public interface OnRefreashListener {
		public void onRefreash();// 刷新

		public void onLoadingMore();// 下拉加载更多
	}
	
	/**
	 * 处理点击条目事件的position
	 */
	OnItemClickListener mItemClickListener;
	@Override
	public void setOnItemClickListener(
			android.widget.AdapterView.OnItemClickListener listener) {
		// TODO Auto-generated method stub
		super.setOnItemClickListener(this);
		mItemClickListener = listener;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (mItemClickListener!=null) {
			mItemClickListener.onItemClick(parent, view, position-getHeaderViewsCount(), id);
		}
	}
}
