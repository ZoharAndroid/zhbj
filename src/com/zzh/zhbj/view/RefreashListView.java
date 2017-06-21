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
 * ����ˢ�µ�ListView
 * 
 * @author Administrator
 * 
 */
public class RefreashListView extends ListView implements android.widget.AdapterView.OnItemClickListener {
	public static final int STATE_REFRESH_PULL = 0;// ����ˢ��״̬
	public static final int STATE_REFRESH_RELEASE = 1;// �ͷ�����ˢ��״̬
	public static final int STATE_REFRESH_ING = 2;// ����ˢ��

	private int mCurrentState = STATE_REFRESH_PULL;// ��ǰˢ��״̬Ϊ����ˢ��

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
	 * ��ʼ��ͷ����
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

		// ����ˢ�µĲ���
		mHeaderView.measure(0, 0);
		mMeasuredHeight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -mMeasuredHeight, 0, 0);

		refreashImageAnim();// ��ʼˢ�¶���

		// ��ʼ����ˢ��ʱ��
		tvRefreashDate.setText("���ˢ��:" + getCurrentTime());
	}

	private boolean isLoadingMore = false;// Ĭ�ϼ��ظ�����Ϊfalse

	/**
	 * ��ʼ���Ų���
	 */
	public void initFooterView() {
		mFooterView = View.inflate(getContext(),
				R.layout.item_refreash_footer_list, null);
		// ListView��ӽŲ���
		this.addFooterView(mFooterView);
		// ���ؽǲ���
		mFooterView.measure(0, 0);
		mFooterHeigt = mFooterView.getMeasuredHeight();
		mFooterView.setPadding(0, -mFooterHeigt, 0, 0);

		// ��������״̬
		this.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// �������״̬Ϊ��ֹͣ�������ſ��ٻ���ʱ
				if (scrollState == SCROLL_STATE_IDLE
						|| scrollState == SCROLL_STATE_FLING) {
					if (getLastVisiblePosition() == (getCount() - 1)
							&& !isLoadingMore) {
						// ��������һ��λ�ã�����ʾ�ǲ���
						mFooterView.setPadding(0, 0, 0, 0);
						// ��ͷ����ֱ����ʾ�����һ��
						setSelection(getCount() - 1);
						// ���ѱ�����ó�true
						isLoadingMore = true;
						
						if (mRefreashListener!=null) {
							mRefreashListener.onLoadingMore();//���ظ���
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
		case MotionEvent.ACTION_DOWN:// ����
			startY = (int) ev.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:// �ƶ�
			if (startY == -1) {// ȷ����ȡ��Y���ƶ�ֵ
				startY = (int) ev.getRawY();
			}

			if (mCurrentState == STATE_REFRESH_ING) {
				break;
			}

			int endY = (int) ev.getRawY();

			int dy = endY - startY;
			if (dy > 0 && getFirstVisiblePosition() == 0) {// ȷ�����»����͵�һ����ʾ��λ�õ�ʱ��ų�������ˢ��
				int padding = dy - mMeasuredHeight;
				mHeaderView.setPadding(0, padding, 0, 0);

				if (padding > 0 && mCurrentState != STATE_REFRESH_RELEASE) {// ���������padding
																			// �������0��˵�����ͷ�ˢ��
					// �ı�״̬
					mCurrentState = STATE_REFRESH_RELEASE;
					changeRefreshState();
				} else if (padding < 0 && mCurrentState != STATE_REFRESH_PULL) {// ����ˢ��״̬
					mCurrentState = STATE_REFRESH_PULL;
					changeRefreshState();
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:// ̧��
			// ����startY
			startY = -1;

			if (mCurrentState == STATE_REFRESH_PULL) {// �����������ˢ��״̬
				// ��������ˢ���б�
				mHeaderView.setPadding(0, -mMeasuredHeight, 0, 0);
			} else if (mCurrentState == STATE_REFRESH_RELEASE) {// ��������ͷ�ˢ��״̬
				mCurrentState = STATE_REFRESH_ING;
				// ��ʾ����ˢ���б�
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
	 * �ı�ˢ��״̬��һЩ����
	 * 
	 */
	private void changeRefreshState() {
		switch (mCurrentState) {
		case STATE_REFRESH_PULL:
			tvRefreashState.setText("����ˢ��");
			ivRefreashImage.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			ivRefreashImage.startAnimation(imageDownAnim);
			break;
		case STATE_REFRESH_RELEASE:
			tvRefreashState.setText("�ɿ�ˢ��");
			ivRefreashImage.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			ivRefreashImage.startAnimation(imageUpAnim);
			break;
		case STATE_REFRESH_ING:
			tvRefreashState.setText("����ˢ��...");
			ivRefreashImage.clearAnimation();// �����������������������
			ivRefreashImage.setVisibility(View.INVISIBLE);
			pbProgress.setVisibility(View.VISIBLE);

			if (mRefreashListener != null) {
				mRefreashListener.onRefreash();// ����ˢ��
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
	 * ����ˢ����ɺ�Ĳ���
	 */
	public void onRefreashComplete() {
		if (isLoadingMore) {
			// �ǲ��֣����ظ���
			// ���ؽǲ���
			mFooterView.setPadding(0, -mFooterHeigt, 0, 0);
			isLoadingMore = false;
		} else {
			mHeaderView.setPadding(0, -mMeasuredHeight, 0, 0);// ���ر�����
			// ����ʾ���ֺ�ͼƬ������
			tvRefreashState.setText("����ˢ��");
			ivRefreashImage.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			// �ı䵱ǰ״̬
			mCurrentState = STATE_REFRESH_PULL;

			tvRefreashDate.setText("���ˢ��:" + getCurrentTime());
		}
	}

	/**
	 * ��ȡ��ǰʱ��
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
		public void onRefreash();// ˢ��

		public void onLoadingMore();// �������ظ���
	}
	
	/**
	 * ��������Ŀ�¼���position
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
