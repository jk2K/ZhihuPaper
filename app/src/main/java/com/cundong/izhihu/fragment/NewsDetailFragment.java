package com.cundong.izhihu.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cundong.izhihu.Constants;
import com.cundong.izhihu.R;
import com.cundong.izhihu.ZhihuApplication;
import com.cundong.izhihu.model.NewsDetailModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lee on 15/8/4.
 */
public class NewsDetailFragment extends Fragment{
    private WebView mWebView;
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
        mWebView = (WebView) rootView.findViewById(R.id.webView);

        return rootView;
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
