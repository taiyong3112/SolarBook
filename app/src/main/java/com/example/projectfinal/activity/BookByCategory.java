package com.example.projectfinal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.projectfinal.R;
import com.example.projectfinal.adapter.BookAdapter;
import com.example.projectfinal.api.BookAPI;
import com.example.projectfinal.entity.Book;
import com.example.projectfinal.entity.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookByCategory extends AppCompatActivity {
    private List<Book> mLstBook = new ArrayList<>();
    private BookAdapter mBookAdapter;
    private RecyclerView rcvListBook;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_by_category);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Category c = (Category) bundle.get("object_category");
        categoryId = c.getId();
        rcvListBook = findViewById(R.id.rcvBookByCategory);
        getListBookCa();
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
                        if (b.isStatus() == false || b.getCategoryId() != categoryId) {
                            i = i - 1;
                            mLstBook.remove(b);
                        }
                    }
                    mBookAdapter = new BookAdapter(BookByCategory.this, mLstBook);
                    rcvListBook.setLayoutManager(new GridLayoutManager(BookByCategory.this, 2));
                    rcvListBook.setAdapter(mBookAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(BookByCategory.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}