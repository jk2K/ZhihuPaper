package com.cundong.izhihu.activity;

import android.content.Intent;
import android.os.Bundle;

import com.cundong.izhihu.R;
import com.cundong.izhihu.fragment.NewsDetailFragment;

/**
 * Created by lee on 15/8/4.
 */
public class NewsDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putLong("newsId", intent.getLongExtra("newsId", 0));

        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        newsDetailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, newsDetailFragment)
                .commit();

        super.setupToolbar();
    }
}
