package com.zzh.zhbj.domain;

import java.util.ArrayList;

public class NewsJsonData {
	private int retcode;//·µ»ØÂë
	
	public ArrayList<NewsJsonMenu> data;//dataÊý×é
	
	
	public int getRetcode() {
		return retcode;
	}

	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}

	public ArrayList<NewsJsonMenu> getData() {
		return data;
	}

	public void setData(ArrayList<NewsJsonMenu> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "NewsJsonData [data=" + data + "]";
	}

	public class NewsJsonMenu{
		private String id;
		private String title;
		private int type;
		private String url;
		private ArrayList<NewsJsonTab> children;
		
		
		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
		}


		public int getType() {
			return type;
		}


		public void setType(int type) {
			this.type = type;
		}


		public String getUrl() {
			return url;
		}


		public void setUrl(String url) {
			this.url = url;
		}


		public ArrayList<NewsJsonTab> getChildren() {
			return children;
		}


		public void setChildren(ArrayList<NewsJsonTab> children) {
			this.children = children;
		}


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
		
		
		
		public String getId() {
			return id;
		}



		public void setId(String id) {
			this.id = id;
		}



		public String getTitle() {
			return title;
		}



		public void setTitle(String title) {
			this.title = title;
		}



		public int getType() {
			return type;
		}



		public void setType(int type) {
			this.type = type;
		}



		public String getUrl() {
			return url;
		}



		public void setUrl(String url) {
			this.url = url;
		}



		@Override
		public String toString() {
			return "NewsJsonTab [title=" + title + "]";
		}
		
		
	}
}
