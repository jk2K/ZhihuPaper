package com.jk2K.ZhiHuDaily;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

public class ZhiHuApplication extends Application {

    private static ZhiHuApplication sInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);

        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
    }

    public static synchronized ZhiHuApplication getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}