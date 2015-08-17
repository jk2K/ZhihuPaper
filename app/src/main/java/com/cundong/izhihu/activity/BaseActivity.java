package com.cundong.izhihu.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cundong.izhihu.R;

/**
 * Created by lee on 15/8/14.
 */
public class BaseActivity extends AppCompatActivity {
    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
