package com.example.projectfinal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectfinal.R;
import com.example.projectfinal.api.UserAPI;
import com.example.projectfinal.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmEmailActivity extends AppCompatActivity {
    private List<User> mLstUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);

        EditText edtConfirmEmail = findViewById(R.id.edtConfirmEmail);

        getListUsers();
        Button btnConfirmEmail = findViewById(R.id.btnConfirmEmail);
        btnConfirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtConfirmEmail.getText().toString();
                for (User u : mLstUser) {
                    if (u.getEmail().equals(email)) {
                        Intent intent = new Intent(ConfirmEmailActivity.this, ResetPasswordActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("resetPassword", u);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    }else{
                        Toast.makeText(ConfirmEmailActivity.this, "Email không tồn tại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        edtConfirmEmail.setText("");
                    }
                }
            }
        });
    }

    private void getListUsers() {
        UserAPI.userApi.getListUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                mLstUser = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(ConfirmEmailActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}