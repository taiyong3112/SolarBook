package com.example.projectfinal.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal.R;
import com.example.projectfinal.adapter.admin.AdminNewsAdapter;
import com.example.projectfinal.api.NewsAPI;
import com.example.projectfinal.entity.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminNewsActivity extends AppCompatActivity {
    private List<News> mLstNews = new ArrayList<>();
    private AdminNewsAdapter mAdminNewsAdapter;
    RecyclerView rcvNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_news);

        getList();
        rcvNewsList = findViewById(R.id.rcvAdminNews);
        //set dữ liệu lên recycler view
        rcvNewsList.setLayoutManager(new LinearLayoutManager(this));
        rcvNewsList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //chuyen sang form them moi
        TextView txtAdd = findViewById(R.id.add_news);
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminNewsActivity.this, AddNewsActivity.class);
                startActivity(intent);
            }
        });
    }

    //Lay du lieu tin tuc qua API
    private void getList() {
        NewsAPI.newsAPI.getNesws().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful()) {
                    mLstNews = response.body();
                    mAdminNewsAdapter = new AdminNewsAdapter(AdminNewsActivity.this, mLstNews);
                    rcvNewsList.setAdapter(mAdminNewsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Toast.makeText(AdminNewsActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //khi nhấn giữ một trường bản ghi thì sẽ hiển thị
    //một menu pop-up lên để chọn sửa hoặc xóa bản ghi ấy
    //bắt sự kiện khi click lên menu pop-up
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        News n = mLstNews.get(item.getOrder());
        switch (item.getItemId()) {
            case 101:
                Intent intent = new Intent(AdminNewsActivity.this, UpdateNewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("updateNews", n);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 111:
                NewsAPI.newsAPI.deleteNews(n.getId()).enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AdminNewsActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminNewsActivity.this, AdminNewsActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {
                        Toast.makeText(AdminNewsActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminNewsActivity.this, AdminActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}