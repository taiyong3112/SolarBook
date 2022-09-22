package com.example.projectfinal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectfinal.R;
import com.example.projectfinal.adapter.BookAdapter;
import com.example.projectfinal.api.BookAPI;
import com.example.projectfinal.entity.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchByName extends AppCompatActivity {
    private String searchName;
    private List<Book> mLstBook;
    private BookAdapter mBookAdapter;
    private RecyclerView rcvBookByName;
    private TextView searchStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_name);

        Bundle bundle = getIntent().getExtras();
        searchName = bundle.getString("searchName");
        searchStr = findViewById(R.id.search_str);
        searchStr.setText("Tìm kiếm:  " + searchName);
        rcvBookByName = findViewById(R.id.rcvBookByName);
        getListBookSale(searchName);
    }

    //Lấy book có giảm giá
    private void getListBookSale(String searchName) {
        BookAPI.bookAPI.findByName(searchName).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    mLstBook = response.body();
                    for (int i = 0; i < mLstBook.size(); i++) {
                        Book b = mLstBook.get(i);
                        if (b.isStatus() == false) {
                            i = i - 1;
                            mLstBook.remove(b);
                        }
                    }
                    mBookAdapter = new BookAdapter(SearchByName.this, mLstBook);
                    rcvBookByName.setLayoutManager(new GridLayoutManager(SearchByName.this, 2));
                    rcvBookByName.setAdapter(mBookAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(SearchByName.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}