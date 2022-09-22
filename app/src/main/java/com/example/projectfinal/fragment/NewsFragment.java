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

import com.example.projectfinal.adapter.NewsAdapter;
import com.example.projectfinal.adapter.admin.AdminCategoryAdapter;
import com.example.projectfinal.api.NewsAPI;
import com.example.projectfinal.entity.News;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    private NewsAdapter mNewsAdapter;
    private List<News> mLstNews = new ArrayList<>();
    private RecyclerView rcvNewsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        //set item lên Recycler View News
        getList();
        rcvNewsList = view.findViewById(R.id.rcvNewsList);
        rcvNewsList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return view;
    }

    //Lay du lieu tin tuc qua API
    private void getList() {
        NewsAPI.newsAPI.getNesws().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful()) {
                    mLstNews = response.body();
                    mNewsAdapter = new NewsAdapter(getActivity(), mLstNews);
                    rcvNewsList.setAdapter(mNewsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
