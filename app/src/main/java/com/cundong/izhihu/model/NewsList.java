package com.cundong.izhihu.model;

import java.util.ArrayList;

/**
 * Created by lee on 15/7/18.
 */
public class NewsList {
    /**
     * top_stories : [{"id":7033763,"title":"我从常青藤退学，进了社区大学，人生才重新走上正轨","ga_prefix":"080513","image":"http://pic4.zhimg.com/87244fee100a8ce52ce9423ae4cbc0e7.jpg","type":0},{"id":7031407,"title":"每天傍晚都下场雨好舒服，以北京为例告诉你这是怎么回事","ga_prefix":"080508","image":"http://pic4.zhimg.com/c4ef8ba8cdfce361fbd428586b9b7983.jpg","type":0},{"id":7034041,"title":"1 亿美元，布什假装没收到","ga_prefix":"080507","image":"http://pic3.zhimg.com/75be1457ceb10e8ab1111067701aca2a.jpg","type":0},{"id":7033125,"title":"今晚的修破斯哒是 · 堺雅人","ga_prefix":"080419","image":"http://pic4.zhimg.com/4143590bd8bdfe9ba32ca7e72779a9e7.jpg","type":0},{"id":7033081,"title":"20 年了，为什么它还是很多人心里中国电影的巅峰？（多图）","ga_prefix":"080417","image":"http://pic4.zhimg.com/b6a5946fdea9d0d6636986619e978d73.jpg","type":0}]
     * stories : [{"id":7035364,"multipic":true,"title":"热化了热化了，能住这种不开空调也凉快的房子就好了（多图）","ga_prefix":"080514","images":["http://pic2.zhimg.com/9e2443245303388737994572d0588311.jpg"],"type":0},{"id":7033763,"title":"我从常青藤退学，进了社区大学，人生才重新走上正轨","ga_prefix":"080513","images":["http://pic3.zhimg.com/9c120d092dbbef7536a4964fae0b0b0a.jpg"],"type":0},{"id":7035029,"title":"最近大火的新三板，可能有三个风险\u2014\u2014给热潮里的冷静人","ga_prefix":"080512","images":["http://pic2.zhimg.com/6396229e6d89f5333782cd0f6ac48e15.jpg"],"type":0},{"id":7034896,"title":"拦腰斩断了一看，怪不得棉是穿着这么舒服的材料","ga_prefix":"080511","images":["http://pic2.zhimg.com/3cfee1b0a9cbdf9d26d174f91163a199.jpg"],"type":0},{"id":7034351,"title":"你以为你们很亲密，其实你们很疏远","ga_prefix":"080510","images":["http://pic1.zhimg.com/33128abcac9f9777c27f1f1e64aa3008.jpg"],"type":0},{"id":7026796,"title":"家庭暴力一旦开始就很难停止，请记住这样自救","ga_prefix":"080509","images":["http://pic2.zhimg.com/e4c744f6ccdc42643ec70f3690348c49.jpg"],"type":0},{"id":7031407,"title":"每天傍晚都下场雨好舒服，以北京为例告诉你这是怎么回事","ga_prefix":"080508","images":["http://pic3.zhimg.com/939f8c439fe573f878389f8e54848a9e.jpg"],"type":0},{"id":7032786,"title":"给黑科技一次机会，火箭发射成本真能降低超级多","ga_prefix":"080507","images":["http://pic4.zhimg.com/3626d9e63b53d357e802441cf4341dfb.jpg"],"type":0},{"id":7034041,"title":"1 亿美元，布什假装没收到","ga_prefix":"080507","images":["http://pic3.zhimg.com/5ac82bd172c269e9a424f578ef15dee2.jpg"],"type":0},{"id":7034183,"title":"你在虚拟生活里投入的精力，也可以是真实的","ga_prefix":"080507","images":["http://pic4.zhimg.com/536c4dc05dc6fc1d1fb687d0457440ab.jpg"],"type":0},{"id":7033320,"title":"瞎扯 · 如何正确地吐槽","ga_prefix":"080506","images":["http://pic1.zhimg.com/6930ba772878316e8841bf68ad649308.jpg"],"type":0},{"id":7033547,"title":"这里是广告 · 上帝的宝藏（下篇）","ga_prefix":"080506","images":["http://pic1.zhimg.com/dd079ca1882d54441b553a6267b268f4.jpg"],"type":0}]
     * date : 20150805
     */
    private ArrayList<NewsModel> top_stories;
    private ArrayList<NewsModel> stories;
    private String date;

    public void setTop_stories(ArrayList<NewsModel> top_stories) {
        this.top_stories = top_stories;
    }

    public void setStories(ArrayList<NewsModel> stories) {
        this.stories = stories;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<NewsModel> getTop_stories() {
        return top_stories;
    }

    public ArrayList<NewsModel> getStories() {
        return stories;
    }

    public String getDate() {
        return date;
    }
}
