package com.cundong.izhihu.model;

import java.util.ArrayList;

/**
 * Created by lee on 15/7/16.
 */
public class NewsModel {
    /**
     * id : 7035364
     * multipic : true
     * title : 热化了热化了，能住这种不开空调也凉快的房子就好了（多图）
     * ga_prefix : 080514
     * images : ["http://pic2.zhimg.com/9e2443245303388737994572d0588311.jpg"]
     * type : 0
     */
    private long id;
    private boolean multipic;
    private String title;
    private String ga_prefix;
    private ArrayList<String> images;
    private int type;

    public void setId(int id) {
        this.id = id;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public String getTitle() {
        return title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public long getType() {
        return type;
    }
}
