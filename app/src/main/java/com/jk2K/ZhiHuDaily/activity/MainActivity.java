package com.jk2K.ZhiHuDaily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jk2K.ZhiHuDaily.R;
import com.jk2K.ZhiHuDaily.fragment.NewsListFragment;

/**
 * 类说明： 	主页面Activity
 *
 * @version 1.0
 * @date 2014-9-20
 */
public class MainActivity extends BaseActivity {

    private static final int REQUESTCODE_SETTING = 8009;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new NewsListFragment())
                    .commit();
        }

        super.setupToolbar();
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
            case R.id.action_setting:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
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