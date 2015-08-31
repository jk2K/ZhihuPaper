package com.jk2K.ZhiHuDaily.model;

import io.realm.RealmObject;

/**
 * Created by lee on 15/8/17.
 */
public class RealmJsonData extends RealmObject{
    private String date;
    private String jsonString;
    private long newsId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }
}
