package com.example.projectfinal.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectfinal.R;
import com.example.projectfinal.adapter.admin.AdminCategoryAdapter;
import com.example.projectfinal.api.CategoryAPI;
import com.example.projectfinal.entity.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCategoryActivity extends AppCompatActivity {
    private List<Category> mLstCategory = new ArrayList<>();
    private AdminCategoryAdapter mAdminCategoryAdapter;
    ListView listViewCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        getList();
        listViewCategory = findViewById(R.id.list_view_category);
        // Cài đặt context menu cho ListView
        registerForContextMenu(listViewCategory);
        //chuyen sang form them moi
        TextView txtAdd = findViewById(R.id.add_category);
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AddCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    //Lay du lieu Category qua API
    private void getList() {
        CategoryAPI.categoryAPI.getCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    mLstCategory = response.body();
                    mAdminCategoryAdapter = new AdminCategoryAdapter(AdminCategoryActivity.this, mLstCategory);
                    listViewCategory.setAdapter(mAdminCategoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(AdminCategoryActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Tạo view sub menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.sub_menu, menu);
        menu.setHeaderTitle("Lựa chọn: ");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Lấy vị trí object đc chọn
        Category idCategory = mLstCategory.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);

        switch (item.getItemId()) {
            case R.id.menuUpdate:
                //chuyển activity
                Intent intent = new Intent(AdminCategoryActivity.this, UpdateCategoryActivity.class);
                //truyền object
                intent.putExtra("idCategory", idCategory);
                startActivity(intent);
                break;
            case R.id.menuDelete:
                CategoryAPI.categoryAPI.deleteCategory(idCategory.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AdminCategoryActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            getList();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AdminCategoryActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                    }
                });
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminCategoryActivity.this, AdminActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}