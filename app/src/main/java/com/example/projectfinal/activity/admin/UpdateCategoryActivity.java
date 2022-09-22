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

public class UpdateCategoryActivity extends AppCompatActivity {

    private Category category = new Category();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);

        Button btnUpdate = findViewById(R.id.btn_update_category);
        btnUpdate.setOnClickListener(listenerUpdateCategory);
        //Ẩn nút
        Button btnAdd = findViewById(R.id.btn_add_category);
        btnAdd.setVisibility(View.GONE);
        //lấy object được truyền từ AdminCategoryActivity
        Bundle bundle = getIntent().getExtras();
        category = (Category) bundle.get("idCategory");
        //load dữ liệu
        EditText editText = findViewById(R.id.edit_category_name);
        editText.setText(category.getName());
    }

    private View.OnClickListener listenerUpdateCategory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String cName;
            EditText edtName = findViewById(R.id.edit_category_name);
            if (edtName.getText().toString().equals("")) {
                Toast.makeText(UpdateCategoryActivity.this, "Chưa nhập tên danh mục", Toast.LENGTH_SHORT).show();
                edtName.requestFocus();
                return;
            } else {
                cName = edtName.getText().toString();
            }

            Category c = new Category(category.getId(), cName);
            CategoryAPI.categoryAPI.updateCategory(c).enqueue(new Callback<Category>() {
                @Override
                public void onResponse(Call<Category> call, Response<Category> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(UpdateCategoryActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateCategoryActivity.this, AdminCategoryActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Category> call, Throwable throwable) {
                    Toast.makeText(UpdateCategoryActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateCategoryActivity.this, AdminCategoryActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}