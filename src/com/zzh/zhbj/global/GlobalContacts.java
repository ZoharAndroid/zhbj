package com.zzh.zhbj.global;

public interface GlobalContacts {
	public static final String FIRST_GUIDE = "is_guide_first";// sp引导页记录的key

	// baseURL
	public static final String BASE_URL = "http://192.168.1.6:8080/zhbj";
	//public static final String BASE_URL = "http://127.0.0.1:8080/zhbj";
	public static final String CATEGORIES_URL = BASE_URL + "/categories.json";
	
	//两个Fragment的标签TAG
	public static final String TAG_LEFT_MENU_FRAGMENT ="tag_left_menu_fragment";
	public static final String TAG_CONTENT_FRAGMENT="tag_content_fragment";
	
	//新闻内容加载地址
	public static final String NEWS_URL = "news_url";
}
