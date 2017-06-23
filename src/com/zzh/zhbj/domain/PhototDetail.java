package com.zzh.zhbj.domain;

import java.util.ArrayList;

/**
 * ×éÍ¼
 * 
 * @author Administrator
 * 
 */
public class PhototDetail {
	private int retcode;
	private PhotosInfo data;

	public class PhotosInfo {
		private String title;
		private ArrayList<PhotoInfo> news;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public ArrayList<PhotoInfo> getNews() {
			return news;
		}

		public void setNews(ArrayList<PhotoInfo> news) {
			this.news = news;
		}

	}

	public class PhotoInfo {
		private String id;
		private String largeimage;
		private String listimage;
		private String pubdate;
		private String smallimage;
		private String title;
		private String type;
		private String url;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getLargeimage() {
			return largeimage;
		}

		public void setLargeimage(String largeimage) {
			this.largeimage = largeimage;
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

		public String getSmallimage() {
			return smallimage;
		}

		public void setSmallimage(String smallimage) {
			this.smallimage = smallimage;
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

	}

	public int getRetcode() {
		return retcode;
	}

	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}

	public PhotosInfo getData() {
		return data;
	}

	public void setData(PhotosInfo data) {
		this.data = data;
	}

}
