package com.example.solarbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solarbook.R;
import com.example.solarbook.entity.News;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context mCtx;
    private List<News> mLst;

    public NewsAdapter(Context mCtx, List<News> mLst) {
        this.mCtx = mCtx;
        this.mLst = mLst;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_news, parent, false);
        NewsViewHolder nvh = new NewsViewHolder(view);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News n = mLst.get(position);
        if(n == null){
            return;
        }
        holder.imgNews.setImageResource(n.getImg());
        holder.txtNewsTitle.setText(n.getTitle());
    }

    @Override
    public int getItemCount() {
        if (mLst != null){
            if (mLst.size() > 3){
                return 3;
            }
            return mLst.size();
        }
        return 0;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgNews;
        private TextView txtNewsTitle;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgNews = itemView.findViewById(R.id.itemNewsImg);
            txtNewsTitle = itemView.findViewById(R.id.itemNewsTitle);
        }
    }
}
