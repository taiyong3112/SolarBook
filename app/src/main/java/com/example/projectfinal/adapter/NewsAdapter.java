package com.example.projectfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal.R;
import com.example.projectfinal.activity.BookDetailActivity;
import com.example.projectfinal.activity.NewsDetailActivity;
import com.example.projectfinal.entity.Book;
import com.example.projectfinal.entity.News;

import java.io.File;
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
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_news_grid_layout, parent, false);
        NewsViewHolder nvh = new NewsViewHolder(view);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News n = mLst.get(position);
        if (n == null) {
            return;
        }

        //Hien thi anh
//        holder.imgNews.setImageResource(getImageId(mCtx, n.getPicture()));
        File imgFile = new File(n.getPicture());
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        holder.imgNews.setImageBitmap(myBitmap);

        holder.txtNewsTitle.setText(n.getDescription());
        //khi ấn vào tin tức
        holder.layoutDetailNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetailNews(n);
            }
        });
    }

    //kịch bản khi nhấn vào một tin tức
    private void onClickGoToDetailNews(News n) {
        Intent intent = new Intent(mCtx, NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_news", n);
        intent.putExtras(bundle);
        mCtx.startActivity(intent);
    }

//    private int getImageId(Context context, String imageName) {
//        return context.getResources().getIdentifier("drawable/"
//                + imageName, null, context.getPackageName());
//    }

    @Override
    public int getItemCount() {
        if (mLst != null) {
            if (mLst.size() > 10) {
                return 10;
            }
            return mLst.size();
        }
        return 0;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgNews;
        private TextView txtNewsTitle;
        private LinearLayout layoutDetailNews;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgNews = itemView.findViewById(R.id.itemNewsImg);
            txtNewsTitle = itemView.findViewById(R.id.itemNewsTitle);
            layoutDetailNews = itemView.findViewById(R.id.layout_detail_news);
        }
    }
}
