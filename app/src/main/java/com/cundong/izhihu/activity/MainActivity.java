package com.cundong.izhihu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.Snackbar;
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
import com.cundong.izhihu.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 类说明： 	主页面Activity
 *
 * @version 1.0
 * @date 2014-9-20
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUESTCODE_SETTING = 8009;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new NewsListFragment())
                    .commit();
        }
//        // WIFI下自动开启离线
//        if (NetWorkHelper.isWifi(this) && !PreferencesUtils.getBoolean(mInstance, DateUtils.getCurrentDate(), false)) {
//

//            startOfflineDownload();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
//                startActivity(new Intent(this, FavoriteActivity.class));
                return true;
            case R.id.action_setting:

                startActivity(new Intent(this, SettingActivity.class));
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
//                    startActivityForResult(new Intent(this, PrefsActivity.class), REQUESTCODE_SETTING);
//                } else {
//                    startActivityForResult(new Intent(this, OtherPrefsActivity.class), REQUESTCODE_SETTING);
//                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class NewsListFragment extends Fragment {
        private NewsAdapter mAdapter;
        private RecyclerView mRecyclerView;

        public NewsListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_news_list, container, false);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            mRecyclerView = (RecyclerView) getView().findViewById(R.id.news_list);
            mRecyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            fetch();
        }

        private void fetch() {
            JsonObjectRequest request = new JsonObjectRequest(
                    Constants.Url.URL_LATEST,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                NewsListModel newsListModels = parse(jsonObject);

                                mAdapter = new NewsAdapter(newsListModels, getActivity());
                                mRecyclerView.setAdapter(mAdapter);
                            }
                            catch (JSONException e) {
                                Snackbar.make(getView(), "Unable to parse data", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Snackbar.make(getView(), "Unable to fetch data", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
            );
            ZhihuApplication.getInstance().getRequestQueue().add(request);
        }

        private NewsListModel parse(JSONObject json) throws JSONException {
            String date = json.getString("date");
            JSONArray jsonStories = json.getJSONArray("stories");
            ArrayList<NewsModel> models = new ArrayList<NewsModel>();
            int total = jsonStories.length();

            for(int i = 0; i < total; i++) {
                JSONObject story = jsonStories.getJSONObject(i);
                String title = story.getString("title");
                String ga_prefix = story.getString("ga_prefix");
                long id = story.getInt("id");
                int type = story.getInt("type");

                ArrayList<String> images = new ArrayList<String>();
                JSONArray jsonImages = story.getJSONArray("images");
                int imagesTotal = jsonImages.length();
                for(int j = 0; j < imagesTotal; j++) {
                    String imageUrl = jsonImages.get(j).toString();
                    images.add(imageUrl);
                }

                NewsModel model = new NewsModel(title, ga_prefix, id, type, images);
                models.add(model);
            }

            return new NewsListModel(date, models);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTCODE_SETTING) {

//            //Activity关闭后，如果改变了主题，则需要recreate this Activity
//            SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(this);
//            if (isDarkTheme != mPerferences.getBoolean("dark_theme?", false)) {
//                recreateActivity();
//            }
        }
    }

    @Override
    protected void onDestroy() {
//        Crouton.clearCroutonsForActivity(this);
        super.onDestroy();
    }

    /**
     * 开始离线下载
     */
    private void startOfflineDownload() {
//        String getLatestUrl = Constants.Url.URL_LATEST;
//        String todayUrl = Constants.Url.URLDEFORE + DateUtils.getCurrentDate(DateUtils.YYYYMMDD);
//        String yesterdayUrl = Constants.Url.URLDEFORE + DateUtils.getYesterdayDate(DateUtils.YYYYMMDD);
//        String theDayBeforeYesterdayUrl = Constants.Url.URLDEFORE + DateUtils.getTheDayBeforeYesterday(DateUtils.YYYYMMDD);
//
//        String[] urls = {getLatestUrl, todayUrl, yesterdayUrl, theDayBeforeYesterdayUrl};
//        for (String url : urls) {

//            ResponseListener listener = url.equals(getLatestUrl) ? this : null;
//            new OfflineDownloadTask(this, listener).executeOnExecutor(MyAsyncTask.DOWNLOAD_THREAD_POOL_EXECUTOR, url);
//        }
    }
}