package com.cundong.izhihu.model;

import java.util.ArrayList;

/**
 * Created by lee on 15/7/16.
 */
public class NewsModel {
    private String title;
    private String gaPrefix;
    private long id;
    private int type;
    private ArrayList<String> images;

    public NewsModel(String title, String ga_prefix, long id, int type, ArrayList<String> images) {
        this.title = title;
        this.gaPrefix = ga_prefix;
        this.id = id;
        this.type = type;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public long getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public ArrayList<String> getImages() {
        return images;
    }
}
