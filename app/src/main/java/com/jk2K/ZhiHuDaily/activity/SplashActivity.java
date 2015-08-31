package com.jk2K.ZhiHuDaily.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jk2K.ZhiHuDaily.Constants;
import com.jk2K.ZhiHuDaily.R;
import com.jk2K.ZhiHuDaily.ZhiHuApplication;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lee on 15/8/19.
 */
public class SplashActivity extends FragmentActivity {
    private SimpleDraweeView mSimpleDraweeView;
    private TextView mTextView;
    private final long SPLASH_TIME_OUT = 3000;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        loadImage();
    }

    /**
     * 加载 启动图
     */
    private void loadImage() {
        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.splashImage);
        mTextView = (TextView) findViewById(R.id.author);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String author = sharedPref.getString(SettingActivity.KEY_PREF_SPLASH_AUTHOR, "");
        String imgUrl = sharedPref.getString(SettingActivity.KEY_PREF_SPLASH_IMAGE, "");

        // 加载图片和图片作者
        if (author.length() == 0) {
            // 没有图片就直接进入首页
            startActivity();
        } else {
            mTextView.setText(author);
            mSimpleDraweeView.setImageURI(Uri.parse(imgUrl));
            // 显示动画
            Animation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); // 将图片放大1.2倍，从中心开始缩放
            animation.setDuration(SPLASH_TIME_OUT); // 动画持续时间
            animation.setFillAfter(true); // 动画结束后停留在结束的位置
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    startActivity();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mSimpleDraweeView.startAnimation(animation);
        }
    }

    /**
     * 进入首页
     */
    private void startActivity() {
        // 获取启动页数据
        JsonObjectRequest request = new JsonObjectRequest(
                Constants.Url.URL_SPLASH,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String imgUrl = jsonObject.getString("img");
                            String author = jsonObject.getString("text");

                            // 储存数据
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(SettingActivity.KEY_PREF_SPLASH_AUTHOR, author);
                            editor.putString(SettingActivity.KEY_PREF_SPLASH_IMAGE, imgUrl);
                            editor.apply();
                        } catch (JSONException e) {
                            // json 解析出错
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // 请求启动图出错
                    }
                }
        );
        ZhiHuApplication.getInstance().getRequestQueue().add(request);

        // 进入首页
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finish();
    }
}
