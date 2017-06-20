package com.zzh.zhbj.domain;

import java.util.ArrayList;

/**
 * 11个页签详情页
 * 
 * @author Administrator
 * 
 */
public class TabNewsDetailData {
	private int retcode;
	public TabDetailData data;

	@Override
	public String toString() {
		return "TabNewsDetailData [retcode=" + retcode + ", data=" + data + "]";
	}

	public int getRetcode() {
		return retcode;
	}

	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}

	public TabDetailData getData() {
		return data;
	}

	public void setData(TabDetailData data) {
		this.data = data;
	}

	/**
	 * 每个页签的内容数据
	 * 
	 * @author Administrator
	 * 
	 */
	public class TabDetailData {
		private String title;
		private String more;
		private ArrayList<DetailNewsData> news;
		private ArrayList<TopNewsData> topnews;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getMore() {
			return more;
		}

		public void setMore(String more) {
			this.more = more;
		}

		public ArrayList<DetailNewsData> getNews() {
			return news;
		}

		public void setNews(ArrayList<DetailNewsData> news) {
			this.news = news;
		}

		public ArrayList<TopNewsData> getTopnews() {
			return topnews;
		}

		public void setTopnews(ArrayList<TopNewsData> topnews) {
			this.topnews = topnews;
		}

		@Override
		public String toString() {
			return "TabDetailData [title=" + title + "]";
		}

		/**
		 * 新闻列表
		 * 
		 * @author Administrator
		 * 
		 */
		public class DetailNewsData {
			private String id;
			private String listimage;
			private String pubdate;
			private String title;
			private String type;
			private String url;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getListimage() {
				return listimage;
			}

			public void setListimage(String listimage) {
				this.listimage = listimage;
			}

			public String getPubdate() {
				return pubdate;
			}

			public void setPubdate(String pubdate) {
				this.pubdate = pubdate;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
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
				return "DetailNewsData [id=" + id + ", title=" + title + "]";
			}

		}

		/**
		 * 头条新闻
		 * 
		 * @author Administrator
		 * 
		 */
		public class TopNewsData {
			private String id;
			private String topimage;
			private String pubdate;
			private String title;
			private String type;
			private String url;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getTopimage() {
				return topimage;
			}

			public void setTopimage(String topimage) {
				this.topimage = topimage;
			}

			public String getPubdate() {
				return pubdate;
			}

			public void setPubdate(String pubdate) {
				this.pubdate = pubdate;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
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
				return "TopNewsData [id=" + id + ", title=" + title + "]";
			}

		}
	}
}
