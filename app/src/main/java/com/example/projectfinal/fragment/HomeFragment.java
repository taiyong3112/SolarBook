package com.example.projectfinal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal.R;
import com.example.projectfinal.activity.MainActivity;
import com.example.projectfinal.activity.SearchByName;
import com.example.projectfinal.adapter.BookAdapter;
import com.example.projectfinal.adapter.HomeNewsAdapter;
import com.example.projectfinal.api.BookAPI;
import com.example.projectfinal.api.NewsAPI;
import com.example.projectfinal.entity.Book;
import com.example.projectfinal.entity.News;
import com.example.projectfinal.entity.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private MainActivity mMainActivity;
    private BookAdapter mBookAdapter;
    private HomeNewsAdapter mHomeNewsAdapter;
    private List<Book> mLstBook = new ArrayList<>();
    private List<News> mLstNews = new ArrayList<>();
    private User mUser;
    private RecyclerView rcvHomeNews, rcvBook, rcvPopularBook, rcvDiscountBook;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mMainActivity = (MainActivity) getActivity();
        mUser = mMainActivity.getmUser();

        rcvBook = view.findViewById(R.id.rcvNewBook);
        rcvPopularBook = view.findViewById(R.id.rcvPopularBook);
        rcvDiscountBook = view.findViewById(R.id.rcvDiscountBook);
        searchView = view.findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String strSearch = searchView.getQuery().toString();
                Intent intent = new Intent(getActivity(), SearchByName.class);
                intent.putExtra("searchName", strSearch);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //Lấy tất cả sách
        getListBook();

        //Lấy sách có category = 1
        getListBookCa();
        //set dữ liệu lên RecycleView New Book

        //Lấy sách có giả giám
        getListBookSale();
        //set dữ liệu lên RecycleView New Book

        getListNews();
        rcvHomeNews = view.findViewById(R.id.rcvHomeNews);
        //set dữ liệu lên recycler view
        rcvHomeNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvHomeNews.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return view;

    }

    //Lay du lieu tin tuc qua API
    private void getListNews() {
        NewsAPI.newsAPI.getNesws().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful()) {
                    mLstNews = response.body();
                    mHomeNewsAdapter = new HomeNewsAdapter(getActivity(), mLstNews);
                    rcvHomeNews.setAdapter(mHomeNewsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Tất cả
    private void getListBook() {
        BookAPI.bookAPI.getBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    mLstBook = response.body();
                    mBookAdapter = new BookAdapter(getActivity(), mLstBook, mUser);
                    for (int i = 0; i < mLstBook.size(); i++) {
                        Book b = mLstBook.get(i);
                        if (b.isStatus() == false) {
                            mLstBook.remove(i);
                        }
                    }
                    //set dữ liệu lên RecycleView New Book
                    rcvBook.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    rcvBook.setAdapter(mBookAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Lấy book có categoryId = 1
    private void getListBookCa() {
        BookAPI.bookAPI.getBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    mLstBook = response.body();
                    for (int i = 0; i < mLstBook.size(); i++) {
                        Book b = mLstBook.get(i);
                        if (b.isStatus() == false || b.getCategoryId() != 3) {
                            i = i - 1;
                            mLstBook.remove(b);
                        }
                    }
                    mBookAdapter = new BookAdapter(getActivity(), mLstBook, mUser);
                    rcvPopularBook.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    rcvPopularBook.setAdapter(mBookAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Lấy book có giảm giá
    private void getListBookSale() {
        BookAPI.bookAPI.getBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    mLstBook = response.body();
                    for (int i = 0; i < mLstBook.size(); i++) {
                        Book b = mLstBook.get(i);
                        if (b.isStatus() == false || b.getSalePrice() == 0) {
                            i = i - 1;
                            mLstBook.remove(b);
                        }
                    }
                    mBookAdapter = new BookAdapter(getActivity(), mLstBook, mUser);
                    rcvDiscountBook.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    rcvDiscountBook.setAdapter(mBookAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
