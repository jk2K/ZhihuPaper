package com.cundong.izhihu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.Snackbar;

import com.cundong.izhihu.Constants;
import com.cundong.izhihu.R;
import com.cundong.izhihu.task.MyAsyncTask;
//import com.cundong.izhihu.task.OfflineDownloadTask;
import com.cundong.izhihu.util.DateUtils;
import com.cundong.izhihu.util.NetWorkHelper;
import com.cundong.izhihu.util.PreferencesUtils;

/**
 * 类说明： 	主页面Activity
 *
 * @version 1.0
 * @date 2014-9-20
 */
public class MainActivity extends BaseActivity implements ResponseListener {

    private static final int REQUESTCODE_SETTING = 8009;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // WIFI下自动开启离线
        if (NetWorkHelper.isWifi(this) && !PreferencesUtils.getBoolean(mInstance, DateUtils.getCurrentDate(), false)) {

//            startOfflineDownload();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:
                startOfflineDownload();
                return true;
            case R.id.action_favorite:
//                startActivity(new Intent(this, FavoriteActivity.class));
                return true;
            case R.id.action_setting:

//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
//                    startActivityForResult(new Intent(this, PrefsActivity.class), REQUESTCODE_SETTING);
//                } else {
//                    startActivityForResult(new Intent(this, OtherPrefsActivity.class), REQUESTCODE_SETTING);
//                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPreExecute() {
//        Crouton.makeText(this, R.string.offline_download_doing, Style.INFO).show();
        final View coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        Snackbar
                .make(coordinatorLayoutView, R.string.offline_download_doing, Snackbar.LENGTH_SHORT)
                .show();

    }

    @Override
    public void onPostExecute(String content) {

        if (!TextUtils.isEmpty(content) && content.equals("success")) {

            PreferencesUtils.putBoolean(mInstance, DateUtils.getCurrentDate(), true);

//            Crouton.makeText(this, R.string.offline_download_done, Style.INFO)
//                    .show();
        } else {
//            Crouton.makeText(this, R.string.offline_download_fail, Style.ALERT)
//                    .show();
        }
    }

    @Override
    public void onFail(final Exception e) {
        if (!isFinishing()) {
//            Crouton.makeText(this, R.string.offline_download_fail, Style.ALERT).show();
        }
    }

    @Override
    public void onProgressUpdate(String value) {

    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTCODE_SETTING) {

            //Activity关闭后，如果改变了主题，则需要recreate this Activity
            SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(this);
            if (isDarkTheme != mPerferences.getBoolean("dark_theme?", false)) {
                recreateActivity();
            }
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
        String getLatestUrl = Constants.Url.URL_LATEST;
        String todayUrl = Constants.Url.URLDEFORE + DateUtils.getCurrentDate(DateUtils.YYYYMMDD);
        String yesterdayUrl = Constants.Url.URLDEFORE + DateUtils.getYesterdayDate(DateUtils.YYYYMMDD);
        String theDayBeforeYesterdayUrl = Constants.Url.URLDEFORE + DateUtils.getTheDayBeforeYesterday(DateUtils.YYYYMMDD);

        String[] urls = {getLatestUrl, todayUrl, yesterdayUrl, theDayBeforeYesterdayUrl};
        for (String url : urls) {

            ResponseListener listener = url.equals(getLatestUrl) ? this : null;
//            new OfflineDownloadTask(this, listener).executeOnExecutor(MyAsyncTask.DOWNLOAD_THREAD_POOL_EXECUTOR, url);
        }
    }
}