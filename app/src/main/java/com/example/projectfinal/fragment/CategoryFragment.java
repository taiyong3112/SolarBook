package com.example.projectfinal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal.R;
import com.example.projectfinal.activity.MainActivity;
import com.example.projectfinal.activity.admin.AdminCategoryActivity;
import com.example.projectfinal.adapter.CategoryAdapter;
import com.example.projectfinal.adapter.admin.AdminCategoryAdapter;
import com.example.projectfinal.api.CategoryAPI;
import com.example.projectfinal.entity.Category;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private CategoryAdapter mCategoryAdapter;
    private List<Category> mLstCategory = new ArrayList<>();
    private RecyclerView rcvCategoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        //set item lên Recycler View Category
        getList();
        rcvCategoryList = view.findViewById(R.id.rcvCategoryList);
        rcvCategoryList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return view;
    }

    //Lay du lieu Category qua API
    private void getList() {
        CategoryAPI.categoryAPI.getCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    mLstCategory = response.body();
                    mCategoryAdapter = new CategoryAdapter(getActivity(), mLstCategory);
                    rcvCategoryList.setAdapter(mCategoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
