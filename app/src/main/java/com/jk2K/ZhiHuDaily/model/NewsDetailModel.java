package com.jk2K.ZhiHuDaily.model;

import java.util.List;

/**
 * Created by lee on 15/8/5.
 */
public class NewsDetailModel {

    /**
     * id : 7032108
     * body : aa
     * title : 事实就是，我们需要无聊
     * ga_prefix : 080411
     * share_url : http://daily.zhihu.com/story/7032108
     * js : []
     * image : http://pic4.zhimg.com/3d8031186ab3162e07f4220b5144bf6f.jpg
     * type : 0
     * css : ["http://news.at.zhihu.com/css/news_qa.auto.css?v=016bb"]
     * image_source : Josh / CC BY
     */
    private int id;
    private String body;
    private String title;
    private String ga_prefix;
    private String share_url;
    private List<?> js;
    private String image;
    private int type;
    private List<String> css;
    private String image_source;

    public void setId(int id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public String getShare_url() {
        return share_url;
    }

    public List<?> getJs() {
        return js;
    }

    public String getImage() {
        return image;
    }

    public int getType() {
        return type;
    }

    public List<String> getCss() {
        return css;
    }

    public String getImage_source() {
        return image_source;
    }
}
