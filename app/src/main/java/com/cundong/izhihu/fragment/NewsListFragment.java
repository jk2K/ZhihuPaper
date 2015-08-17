package com.cundong.izhihu.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cundong.izhihu.Constants;
import com.cundong.izhihu.R;
import com.cundong.izhihu.ZhihuApplication;
import com.cundong.izhihu.adapter.NewsAdapter;
import com.cundong.izhihu.model.NewsListModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import io.realm.Realm;

/**
 * Created by lee on 15/8/5.
 */
public class NewsListFragment extends Fragment {
    private NewsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Realm realm;
    private String mCurrentDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        // 下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        fetch();
                    }
                }
        );

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.newsList);
        mRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 上拉加载
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                    loadMore();
//                    if(isLoadingMore){
//                        Log.d(TAG,"ignore manually update!");
//                    } else{
//                        loadPage();//这里多线程也要手动控制isLoadingMore
//                        isLoadingMore = false;
//                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fetch();
    }

    private void fetch() {
        JsonObjectRequest request = new JsonObjectRequest(
                Constants.Url.URL_LATEST,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("lfdjlas", "onResponse 11");
                        // 填充数据
                        Gson gson = new Gson();
                        NewsListModel newsListModel = gson.fromJson(jsonObject.toString(), NewsListModel.class);
                        mCurrentDate = newsListModel.getDate();
                        mAdapter = new NewsAdapter(newsListModel, getActivity());
                        mRecyclerView.setAdapter(mAdapter);

                        // 停止刷新
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Snackbar.make(getView(), "Unable to fetch data", Snackbar.LENGTH_SHORT)
                                .show();
                        // 停止刷新
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        ZhihuApplication.getInstance().getRequestQueue().add(request);
    }

    private void loadMore() {
        JsonObjectRequest request = new JsonObjectRequest(
                Constants.Url.URL_BEFORE + mCurrentDate,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // 填充数据
                        Gson gson = new Gson();
                        NewsListModel newsListModel = gson.fromJson(jsonObject.toString(), NewsListModel.class);
                        mAdapter = new NewsAdapter(newsListModel, getActivity());
                        mCurrentDate = newsListModel.getDate();
                        mRecyclerView.setAdapter(mAdapter);

                        // 停止刷新
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Snackbar.make(getView(), "Unable to fetch data", Snackbar.LENGTH_SHORT)
                                .show();
                        // 停止刷新
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        ZhihuApplication.getInstance().getRequestQueue().add(request);
    }
}
