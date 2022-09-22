package com.example.projectfinal.adapter.admin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal.R;
import com.example.projectfinal.entity.News;

import java.io.File;
import java.util.List;

public class AdminNewsAdapter extends RecyclerView.Adapter<AdminNewsAdapter.NewsViewHolder> {

    private Context mCtx;
    private List<News> mLst;

    public AdminNewsAdapter(Context mCtx, List<News> mLst) {
        this.mCtx = mCtx;
        this.mLst = mLst;
    }

    @NonNull
    @Override
    public AdminNewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_admin_news, parent, false);
        AdminNewsAdapter.NewsViewHolder nvh = new AdminNewsAdapter.NewsViewHolder(view);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNewsAdapter.NewsViewHolder holder, int position) {
        News n = mLst.get(position);
        if (n == null) {
            return;
        }
        //hien thi hinh anh
        File imgFile = new File(n.getPicture());
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        holder.imgNews.setImageBitmap(myBitmap);

        holder.txtNewsTitle.setText(n.getDescription());
    }

    @Override
    public int getItemCount() {
        if (mLst != null) {
            return mLst.size();
        }
        return 0;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private ImageView imgNews;
        private TextView txtNewsTitle;
        private LinearLayout layoutNews;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgNews = itemView.findViewById(R.id.itemNewsImg);
            txtNewsTitle = itemView.findViewById(R.id.itemNewsTitle);
            layoutNews = itemView.findViewById(R.id.layoutAdminNews);
            layoutNews.setOnCreateContextMenuListener(this);
        }

        //dinh nghia nhung truong hien thi trong menu pop-up
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Lựa chọn");
            menu.add(0, 101, getAdapterPosition(), "Cập nhật");
            menu.add(0, 111, getAdapterPosition(), "Xóa");
        }
    }
}
