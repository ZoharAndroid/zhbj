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
 * ������ʾ
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
		//��ʼ��ShareSDK
		MobSDK.init(getApplicationContext(), "1ee1f98ace5c8", "b286a8de9d4b90159dc8ad777a91457f");
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_news);

		ibBack = (ImageButton) findViewById(R.id.btn_back);
		ibTextSize = (ImageButton) findViewById(R.id.btn_text_size);
		ibShare = (ImageButton) findViewById(R.id.btn_news_share);
		wbNews = (WebView) findViewById(R.id.wv_show_news);
		pbLoading = (ProgressBar) findViewById(R.id.pb_loading_news);

		// ��ȡ������������
		String newsUrl = getIntent().getStringExtra(GlobalContacts.NEWS_URL);
		WebSettings settings = wbNews.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setUseWideViewPort(true);// ����˫��

		wbNews.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// ���Լ���WebView����url
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// ��ʼ��ҳ����
				super.onPageStarted(view, url, favicon);
				pbLoading.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// ��������
				super.onPageFinished(view, url);
				pbLoading.setVisibility(View.INVISIBLE);
			}
		});

		// ����ҳ��
		wbNews.loadUrl("http://www.baidu.com");

		// ���ؼ�
		ibBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// �޸����尴ť
		ibTextSize.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseSizeText();
			}
		});
		// ����ť
		ibShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showShare();
			}
		});
	}

	protected void showShare() {
		OnekeyShare oks = new OnekeyShare();
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();
		// title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ��QQ�ռ�ʹ��
		oks.setTitle("����");
		// titleUrl�Ǳ�����������ӣ�����Linked-in,QQ��QQ�ռ�ʹ��
		oks.setTitleUrl("http://sharesdk.cn");
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText("���Ƿ����ı�");
		// ��������ͼƬ������΢����������ͼƬ��Ҫͨ����˺�����߼�д��ӿڣ�������ע�͵���������΢��
		oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
		// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		// oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		// url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		oks.setUrl("http://sharesdk.cn");
		// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		oks.setComment("���ǲ��������ı�");
		// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		oks.setSite("ShareSDK");
		// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		oks.setSiteUrl("http://sharesdk.cn");

		// ��������GUI
		oks.show(this);

	}

	private int mSelectItemPositon;// ��¼�û�ѡ�е�item
	private int mCurrentItem = 2;// ��¼��ǰ��item��Ĭ��Ϊ��׼

	/**
	 * ������ʾ�����С
	 */
	private void chooseSizeText() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("�޸������С");
		String[] items = new String[] { "�����", "���", "��׼", "С��", "��С��" };
		builder.setSingleChoiceItems(items, mCurrentItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mSelectItemPositon = which;
					}
				});
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				WebSettings settings = wbNews.getSettings();
				switch (mSelectItemPositon) {
				case 0:
					settings.setTextSize(TextSize.LARGEST);// ���������
					break;
				case 1:
					settings.setTextSize(TextSize.LARGER);// �������
					break;
				case 2:
					settings.setTextSize(TextSize.NORMAL);// ��׼����
					break;
				case 3:
					settings.setTextSize(TextSize.SMALLER);// С������
					break;
				case 4:
					settings.setTextSize(TextSize.SMALLEST);// ��С������
					break;
				default:
					break;
				}
				mCurrentItem = mSelectItemPositon;// ��ѡ���itemλ�ñ�������
			}
		});

		builder.setNegativeButton("ȡ��", null);
		builder.show();
	}
}
