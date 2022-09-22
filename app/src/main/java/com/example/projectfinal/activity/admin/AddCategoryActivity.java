package com.example.projectfinal.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfinal.R;
import com.example.projectfinal.api.CategoryAPI;
import com.example.projectfinal.entity.Category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Button btnAdd = findViewById(R.id.btn_add_category);
        btnAdd.setOnClickListener(listenerAddCategory);
        //Ẩn nút
        Button btnUpdate = findViewById(R.id.btn_update_category);
        btnUpdate.setVisibility(View.GONE);
    }

    private View.OnClickListener listenerAddCategory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String cName;
            EditText edtName = findViewById(R.id.edit_category_name);
            if (edtName.getText().toString().equals("")) {
                Toast.makeText(AddCategoryActivity.this, "Chưa nhập tên danh mục", Toast.LENGTH_SHORT).show();
                edtName.requestFocus();
                return;
            } else {
                cName = edtName.getText().toString();
            }

            Category c = new Category(cName);
            CategoryAPI.categoryAPI.addCategory(c).enqueue(new Callback<Category>() {
                @Override
                public void onResponse(Call<Category> call, Response<Category> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AddCategoryActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddCategoryActivity.this, AdminCategoryActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Category> call, Throwable throwable) {
                    Toast.makeText(AddCategoryActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddCategoryActivity.this, AdminCategoryActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}