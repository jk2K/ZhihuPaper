package com.cundong.izhihu.model;

import java.util.ArrayList;

/**
 * Created by lee on 15/7/18.
 */
public class NewsListModel {
    private String date;
    private ArrayList<NewsModel> stories;

    public NewsListModel(String date, ArrayList stories) {
        this.date = date;
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<NewsModel> getStories() {
        return stories;
    }
}
