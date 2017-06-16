package com.zzh.zhbj.domain;

import java.util.ArrayList;

public class NewsJsonData {
	private int retcode;//·µ»ØÂë
	private ArrayList<NewsJsonMenu> data;//dataÊý×é
	
	
	
	@Override
	public String toString() {
		return "NewsJsonData [data=" + data + "]";
	}

	class NewsJsonMenu{
		private String id;
		private String title;
		private int type;
		private String url;
		private ArrayList<NewsJsonTab> children;
		@Override
		public String toString() {
			return "NewsJsonMenu [title=" + title + ", url=" + url + "]";
		}
		
	}
	
	class NewsJsonTab{
		private String id;
		private String title;
		private int type;
		private String url;
		
		@Override
		public String toString() {
			return "NewsJsonTab [title=" + title + "]";
		}
		
		
	}
}
