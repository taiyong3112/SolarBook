package com.example.projectfinal.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectfinal.R;
import com.example.projectfinal.api.PublisherAPI;
import com.example.projectfinal.entity.Publisher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPublisherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publisher);

        Button btnAdd = findViewById(R.id.btn_add_publisher);
        btnAdd.setOnClickListener(listenerAddPublisher);
    }

    private View.OnClickListener listenerAddPublisher = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String cName;
            EditText edtName = findViewById(R.id.edit_publisher_name);
            if (edtName.getText().toString().equals("")) {
                Toast.makeText(AddPublisherActivity.this, "Chưa nhập tên nhà xuất bản", Toast.LENGTH_SHORT).show();
                edtName.requestFocus();
                return;
            } else {
                cName = edtName.getText().toString();
            }

            Publisher c = new Publisher(cName);
            PublisherAPI.publisherAPI.addPublisher(c).enqueue(new Callback<Publisher>() {
                @Override
                public void onResponse(Call<Publisher> call, Response<Publisher> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AddPublisherActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddPublisherActivity.this, AdminPublisherActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Publisher> call, Throwable throwable) {
                    Toast.makeText(AddPublisherActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddPublisherActivity.this, AdminPublisherActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}