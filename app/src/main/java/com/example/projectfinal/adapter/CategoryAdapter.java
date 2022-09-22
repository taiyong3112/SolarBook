package com.example.projectfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projectfinal.R;
import com.example.projectfinal.activity.BookByCategory;
import com.example.projectfinal.entity.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context mCtx;
    private List<Category> mLst;

    public CategoryAdapter(Context mCtx, List<Category> mLst) {
        this.mCtx = mCtx;
        this.mLst = mLst;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_category, parent, false);
        CategoryViewHolder cvh = new CategoryViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category c = mLst.get(position);
        if (c == null) {
            return;
        }

        holder.txtCategoryName.setText(c.getName());
        //khi ấn vào hình quyển sách
        holder.layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetailBook(c);
            }
        });
    }

    private void onClickGoToDetailBook(Category c) {
        Intent intent = new Intent(mCtx, BookByCategory.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_category", c);
        intent.putExtras(bundle);
        mCtx.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mLst != null) {
            return mLst.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCategoryName;
        private LinearLayout layoutCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.itemCategoryName);
            layoutCategory = itemView.findViewById(R.id.layout_category);
        }
    }
}
