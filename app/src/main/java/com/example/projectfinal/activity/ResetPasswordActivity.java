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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Bundle bundle = getIntent().getExtras();
        if(bundle.isEmpty()){
            return;
        }

        mUser = (User) bundle.get("resetPassword");

        EditText edtNewPass = findViewById(R.id.edtResetNewPassword);
        EditText edtCNewPass = findViewById(R.id.edtResetNewCPassword);
        Button btnResetPass = findViewById(R.id.btnResetPassword);

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = edtNewPass.getText().toString();
                String cNewPass = edtCNewPass.getText().toString();

                if(newPass.equals(cNewPass)){
                    mUser.setPassword(newPass);
                    UserAPI.userApi.updateUser(mUser).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(ResetPasswordActivity.this, "Đặt lại mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(ResetPasswordActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(ResetPasswordActivity.this, "Mật khẩu không trùng, xin thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}