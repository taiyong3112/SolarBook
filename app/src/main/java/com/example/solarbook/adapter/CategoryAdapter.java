package com.example.solarbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solarbook.R;
import com.example.solarbook.entity.Category;

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
        if(c == null){
            return;
        }

        holder.txtCategoryName.setText(c.getName());
    }

    @Override
    public int getItemCount() {
        if(mLst != null){
            return mLst.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView txtCategoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.itemCategoryName);
        }
    }
}
