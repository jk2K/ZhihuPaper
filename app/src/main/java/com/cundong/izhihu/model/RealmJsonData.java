package com.cundong.izhihu.model;

import io.realm.RealmObject;

/**
 * Created by lee on 15/8/17.
 */
public class RealmJsonData extends RealmObject{
    private String date;
    private String jsonString;
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
