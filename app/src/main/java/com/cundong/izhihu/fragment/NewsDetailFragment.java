package com.cundong.izhihu.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cundong.izhihu.Constants;
import com.cundong.izhihu.R;
import com.cundong.izhihu.ZhihuApplication;
import com.cundong.izhihu.model.NewsDetailModel;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lee on 15/8/4.
 */
public class NewsDetailFragment extends Fragment {
    private ObservableWebView mWebView;
    private long mNewsId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mNewsId = bundle.getLong("newsId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mWebView = (ObservableWebView) rootView.findViewById(R.id.webView);
        mWebView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int i, boolean b, boolean b1) {
            }

            @Override
            public void onDownMotionEvent() {
            }

            @Override
            public void onUpOrCancelMotionEvent(ScrollState scrollState) {
                if (scrollState == ScrollState.UP) {
                    enterOrNotFullScreen(true);
                } else if (scrollState == ScrollState.DOWN) {
                    enterOrNotFullScreen(false);
                }
            }
        });

        return rootView;
    }

    @TargetApi(19)
    /**
     * 进不进入全屏模式, 全屏模式可以让用户专注于内容, 避免干扰
     * @param Boolean fullScreen
     */
    private void enterOrNotFullScreen(Boolean fullScreen) {
        if (Build.VERSION.SDK_INT >= 19) {
            if (fullScreen) {
                mWebView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                | View.SYSTEM_UI_FLAG_IMMERSIVE);
            } else {
                mWebView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fetch();
    }

    private void fetch() {
        JsonObjectRequest request = new JsonObjectRequest(
                Constants.Url.URL_DETAIL + mNewsId,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        NewsDetailModel newsDetailModel = gson.fromJson(jsonObject.toString(), NewsDetailModel.class);
                        setWebView(newsDetailModel);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Snackbar.make(getView(), "unable to fetch data", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );
        ZhihuApplication.getInstance().getRequestQueue().add(request);
    }

    private void setWebView(NewsDetailModel newsDetailModel) {
        String html = "";
        try {
            StringBuilder buf = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getResources().getAssets().open(Constants.TEMPLATE_DEF_URL))
            );
            String str;
            while ((str = reader.readLine()) != null) {
                buf.append(str);
            }
            reader.close();
            html = buf.toString().replace("{content}", newsDetailModel.getBody());
        } catch (IOException e) {
            // log the exception
        }
        // 显示顶部图片
        String imgDiv = "<div class=\"img-wrap\">"
                + "<h1 class=\"headline-title\">"
                + newsDetailModel.getTitle() + "</h1>"
                + "<span class=\"img-source\">"
                + newsDetailModel.getImage_source() + "</span>"
                + "<img src=\"" + newsDetailModel.getImage()
                + "\" alt=\"\">"
                + "<div class=\"img-mask\"></div>";
        html = html.replace("<div class=\"img-place-holder\">", imgDiv);

        mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }
}
