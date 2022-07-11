package com.example.solarbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solarbook.R;
import com.example.solarbook.adapter.CategoryAdapter;
import com.example.solarbook.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private CategoryAdapter mCategoryAdapter;
    private List<Category> mLstCategory = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        //set item lên Recycler View Category
        getListCategory();
        mCategoryAdapter = new CategoryAdapter(getActivity(), mLstCategory);
        RecyclerView rcvCategoryList = view.findViewById(R.id.rcvCategoryList);
        rcvCategoryList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rcvCategoryList.setAdapter(mCategoryAdapter);
        return view;
    }

    private void getListCategory(){
        mLstCategory.add(new Category("Sách thiếu nhi"));
        mLstCategory.add(new Category("Sách dành cho giới trẻ"));
        mLstCategory.add(new Category("Sách Chính trị - Xã hội"));
        mLstCategory.add(new Category("Tủ sách gia đình"));
        mLstCategory.add(new Category("Sách Giáo dục"));
        mLstCategory.add(new Category("Sách Kinh tế"));
        mLstCategory.add(new Category("Sách ngoại ngữ"));
        mLstCategory.add(new Category("Sách Khoa học - Công nghệ"));
        mLstCategory.add(new Category("Sách Văn học - Nghệ thuật"));
    }
}
