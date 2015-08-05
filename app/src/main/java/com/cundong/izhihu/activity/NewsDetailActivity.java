package com.cundong.izhihu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cundong.izhihu.fragment.NewsDetailFragment;

/**
 * Created by lee on 15/8/4.
 */
public class NewsDetailActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putLong("newsId", intent.getLongExtra("newsId", 0));

        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        newsDetailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, newsDetailFragment)
                .commit();
    }
}
