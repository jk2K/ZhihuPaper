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
import com.cundong.izhihu.model.NewsList;
import com.cundong.izhihu.model.RealmJsonData;
import com.google.gson.Gson;

import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by lee on 15/8/5.
 */
public class NewsListFragment extends Fragment {
    private NewsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Realm realm;
    private String mCurrentDate;
    private boolean loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getActivity()).build();

        // Clear the realm from last time, only for debug
//        Realm.deleteRealm(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);
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
                    if (!loading) {
                        // 避免发起多次加载更多请求
                        loading = true;
                        loadMore();
                    }
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
                        // 填充数据
                        Gson gson = new Gson();
                        NewsList newsList = gson.fromJson(jsonObject.toString(), NewsList.class);
                        mCurrentDate = newsList.getDate();
                        mAdapter = new NewsAdapter(newsList, getActivity());
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
        // 读取json字符串
        RealmResults<RealmJsonData> result = realm.where(RealmJsonData.class)
                .equalTo("date", mCurrentDate)
                .findAll();
        if (result.size() == 0) {
//            Log.d("test", mCurrentDate + "发起请求");
            // 没有缓存过
            JsonObjectRequest request = new JsonObjectRequest(
                    Constants.Url.URL_BEFORE + mCurrentDate,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            // json 字符串
                            String jsonString = jsonObject.toString();

                            // 缓存 json 字符串
                            realm.beginTransaction();
                            RealmJsonData realmJsonData = realm.createObject(RealmJsonData.class); // Create a new object
                            realmJsonData.setDate(mCurrentDate);
                            realmJsonData.setJsonString(jsonString);
                            realm.commitTransaction();

                            // 填充数据
                            setAdapter(jsonString);
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
        } else {
            // 有json缓存
//            Log.d("test", "加载缓存");
//            Log.d("test", result.toString());
            // 填充数据
            setAdapter(result.get(0).getJsonString());
        }
    }

    private void setAdapter(String jsonString)
    {
        Gson gson = new Gson();
        NewsList newsList = gson.fromJson(jsonString, NewsList.class);
        mAdapter = new NewsAdapter(newsList, getActivity());
        mCurrentDate = newsList.getDate();
        mRecyclerView.setAdapter(mAdapter);

        // 停止刷新
        mSwipeRefreshLayout.setRefreshing(false);
        loading = false;
    }
}
