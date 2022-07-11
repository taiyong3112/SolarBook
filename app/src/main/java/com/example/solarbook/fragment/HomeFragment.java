package com.example.solarbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solarbook.R;
import com.example.solarbook.adapter.BookAdapter;
import com.example.solarbook.adapter.NewsAdapter;
import com.example.solarbook.entity.Book;
import com.example.solarbook.entity.News;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private BookAdapter mBookAdapter;
    private NewsAdapter mNewsAdapter;
    private List<Book> mLstBook = new ArrayList<>();
    private List<News> mLstNews = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getListBook();
        mBookAdapter = new BookAdapter(getActivity(), mLstBook);
        //set dữ liệu lên RecycleView New Book
        RecyclerView rcvNewBook = view.findViewById(R.id.rcvNewBook);
        rcvNewBook.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rcvNewBook.setAdapter(mBookAdapter);

        //set dữ liệu lên RecycleView Popular Book
        RecyclerView rcvPopularBook = view.findViewById(R.id.rcvPopularBook);
        rcvPopularBook.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rcvPopularBook.setAdapter(mBookAdapter);

        //set dữ liệu lên RecycleView Discount Book
        RecyclerView rcvDiscountBook = view.findViewById(R.id.rcvDiscountBook);
        rcvDiscountBook.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rcvDiscountBook.setAdapter(mBookAdapter);

        //set dữ liệu lên RecycleView Homepage News
        getListNews();
        mNewsAdapter = new NewsAdapter(getActivity(), mLstNews);
        RecyclerView rcvHomeNews = view.findViewById(R.id.rcvHomeNews);
        rcvHomeNews.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rcvHomeNews.setAdapter(mNewsAdapter);
        return view;

    }

    private void getListBook(){
        mLstBook.add(new Book(R.drawable.book1, "Harry Potter và Bảo Bối Tử Thần"));
        mLstBook.add(new Book(R.drawable.book2, "Cuộc sống của bạn đã tốt đẹp chưa"));
        mLstBook.add(new Book(R.drawable.book3, "11 bí quyết giao tiếp để thành công"));
        mLstBook.add(new Book(R.drawable.book4, "Tiếng gọi nơi hoang dã"));
        mLstBook.add(new Book(R.drawable.book1, "Harry Potter và Bảo Bối Tử Thần"));
        mLstBook.add(new Book(R.drawable.book2, "Cuộc sống của bạn đã tốt đẹp chưa"));
    }

    private void getListNews(){
        mLstNews.add(new News(R.drawable.news1, "Nhìn thẳng, không né tránh những vấn đề của ngành sách"));
        mLstNews.add(new News(R.drawable.news2, "Sách nói phát triển mạnh ở nhiều quốc gia"));
        mLstNews.add(new News(R.drawable.news3, "Nhiều hoạt động hưởng ứng Ngày Sách và Văn hóa đọc"));
        mLstNews.add(new News(R.drawable.news1, "Nhìn thẳng, không né tránh những vấn đề của ngành sách"));
        mLstNews.add(new News(R.drawable.news2, "Sách nói phát triển mạnh ở nhiều quốc gia"));
    }
}
