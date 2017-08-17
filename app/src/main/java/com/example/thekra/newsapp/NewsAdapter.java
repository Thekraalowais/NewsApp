package com.example.thekra.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<News> news;

    public NewsAdapter(Context context, List<News> news) {
        this.context = context;
        this.news = news;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.new_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final News currentNew = news.get(position);
        holder.title.setText(currentNew.getTitle());
        holder.section.setText(currentNew.getSectionName());
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri newsUrl = Uri.parse(currentNew.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUrl);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView section;
        private View click;

        public ViewHolder(View view) {
            super(view);
            click = view;
            title = (TextView) view.findViewById(R.id.title);
            section = (TextView) view.findViewById(R.id.section);
        }
    }

    public void clear() {
        int size = news.size();
        news.clear();
        notifyItemRangeRemoved(0, size);

    }

    public void AddAll(List<News> data) {
        news.clear();
        news.addAll(data);
        notifyDataSetChanged();
    }
}
