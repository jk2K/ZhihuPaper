package com.jk2K.ZhiHuDaily;

public final class Constants {
	
	// 默认模板路径
	public static final String TEMPLATE_DEF_URL = "www/template.html";
	
	public static final String PROJECT_URL = "https://github.com/jk2K/ZhihuPaper";
	
	public static final class Url {
        // 启动页图片
        public static final String URL_SPLASH = "http://news-at.zhihu.com/api/4/start-image/1080*1776";

		// 分享链接
		public static final String URL_SHARE = "http://daily.zhihu.com/story/";

		// 获取最新新闻
		public static final String URL_LATEST = "http://news-at.zhihu.com/api/4/news/latest";

		// 获取新闻详情
		public static final String URL_DETAIL = "http://news-at.zhihu.com/api/4/news/";

		// 获取过往新闻
		public static final String URL_BEFORE = "http://news.at.zhihu.com/api/4/news/before/";
	}
	
	public static final int NEWS_LIST = 1;
	public static final int NEWS_DETAIL = 2;
	
}