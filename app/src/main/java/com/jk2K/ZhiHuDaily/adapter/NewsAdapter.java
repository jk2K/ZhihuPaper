package com.jk2K.ZhiHuDaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jk2K.ZhiHuDaily.R;
import com.jk2K.ZhiHuDaily.activity.NewsDetailActivity;
import com.jk2K.ZhiHuDaily.model.NewsList;
import com.jk2K.ZhiHuDaily.model.NewsModel;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by lee on 15/7/17.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private NewsList newsList;
    private Context mContext;
    public enum ITEM_TYPE {
        ITEM_TYPE_DATE,
        ITEM_TYPE_NEWS
    }

    public NewsAdapter(NewsList newsList, Context context) {
        this.newsList = newsList;
        this.mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_DATE.ordinal()) {
            return new DateViewHolder(View.inflate(parent.getContext(), R.layout.list_date_item, null));
        } else {
            return new NewsViewHolder(View.inflate(parent.getContext(), R.layout.list_news_item, null));
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DateViewHolder) {
            ((DateViewHolder) holder).dateTextView.setText(newsList.getDate());
        } else {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            NewsModel newsModel = newsList.getStories().get(position - 1);

            // 设置新闻标题
            ((NewsViewHolder) holder).titleTextView.setText(newsModel.getTitle());

            // 设置新闻图片
            // 新闻图片有可能没有
            if (newsModel.getImages() != null) {
                String imageUrl = newsModel.getImages().get(0);
                ((NewsViewHolder) holder).imageSimpleDraweeView.setImageURI(Uri.parse(imageUrl));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE.ITEM_TYPE_DATE.ordinal() : ITEM_TYPE.ITEM_TYPE_NEWS.ordinal();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newsList.getStories().size() + 1;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;

        public DateViewHolder(View v) {
            super(v);

            dateTextView = (TextView) v.findViewById(R.id.date_text);
        }
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleTextView;
        public SimpleDraweeView imageSimpleDraweeView;

        public NewsViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);

            titleTextView = (TextView) v.findViewById(R.id.list_news_title);
            imageSimpleDraweeView = (SimpleDraweeView) v.findViewById(R.id.list_news_image);
        }

        public void onClick(View view) {
            int position = getLayoutPosition();
            NewsModel newsModel = newsList.getStories().get(position - 1);
            Intent intent = new Intent(mContext, NewsDetailActivity.class);
            intent.putExtra("newsId", newsModel.getId());
            mContext.startActivity(intent);
        }
    }
}
