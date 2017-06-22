package com.zzh.zhbj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.mob.MobSDK;
import com.zzh.zhbj.global.GlobalContacts;

/**
 * 新闻显示
 * 
 * @author Administrator
 * 
 */
public class NewsDetailShowActivity extends Activity {

	private ImageButton ibBack;
	private ImageButton ibTextSize;
	private ImageButton ibShare;
	private WebView wbNews;
	private ProgressBar pbLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//初始化ShareSDK
		MobSDK.init(getApplicationContext(), "1ee1f98ace5c8", "b286a8de9d4b90159dc8ad777a91457f");
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_news);

		ibBack = (ImageButton) findViewById(R.id.btn_back);
		ibTextSize = (ImageButton) findViewById(R.id.btn_text_size);
		ibShare = (ImageButton) findViewById(R.id.btn_news_share);
		wbNews = (WebView) findViewById(R.id.wv_show_news);
		pbLoading = (ProgressBar) findViewById(R.id.pb_loading_news);

		// 获取传过来的数据
		String newsUrl = getIntent().getStringExtra(GlobalContacts.NEWS_URL);
		WebSettings settings = wbNews.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setUseWideViewPort(true);// 可以双击

		wbNews.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 让自己的WebView加载url
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// 开始网页加载
				super.onPageStarted(view, url, favicon);
				pbLoading.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// 结束加载
				super.onPageFinished(view, url);
				pbLoading.setVisibility(View.INVISIBLE);
			}
		});

		// 加载页面
		wbNews.loadUrl("http://www.baidu.com");

		// 返回键
		ibBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 修改字体按钮
		ibTextSize.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseSizeText();
			}
		});
		// 分享按钮
		ibShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showShare();
			}
		});
	}

	protected void showShare() {
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		// title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
		oks.setTitle("标题");
		// titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
		oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite("ShareSDK");
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		oks.show(this);

	}

	private int mSelectItemPositon;// 记录用户选中的item
	private int mCurrentItem = 2;// 记录当前的item，默认为标准

	/**
	 * 设置显示字体大小
	 */
	private void chooseSizeText() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("修改字体大小");
		String[] items = new String[] { "超大号", "大号", "标准", "小号", "超小号" };
		builder.setSingleChoiceItems(items, mCurrentItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mSelectItemPositon = which;
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				WebSettings settings = wbNews.getSettings();
				switch (mSelectItemPositon) {
				case 0:
					settings.setTextSize(TextSize.LARGEST);// 超大号字体
					break;
				case 1:
					settings.setTextSize(TextSize.LARGER);// 大号字体
					break;
				case 2:
					settings.setTextSize(TextSize.NORMAL);// 标准字体
					break;
				case 3:
					settings.setTextSize(TextSize.SMALLER);// 小号字体
					break;
				case 4:
					settings.setTextSize(TextSize.SMALLEST);// 超小号字体
					break;
				default:
					break;
				}
				mCurrentItem = mSelectItemPositon;// 把选择的item位置保存下来
			}
		});

		builder.setNegativeButton("取消", null);
		builder.show();
	}
}
