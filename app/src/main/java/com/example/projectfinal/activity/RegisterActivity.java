package com.example.projectfinal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectfinal.R;
import com.example.projectfinal.api.CityAPI;
import com.example.projectfinal.api.UserAPI;
import com.example.projectfinal.entity.City;
import com.example.projectfinal.entity.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private List<City> mLstCity;
    private Spinner mSpCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mLstCity = new ArrayList<>();
        mSpCity = findViewById(R.id.formRegisterSpnCity);
        getListCities();

        Button btnRegister = findViewById(R.id.formRegisterBtnAccept);
        btnRegister.setOnClickListener(listenerRegister);
    }

    private void getListCities() {
        CityAPI.cityApi.getListCities().enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.isSuccessful()) {
                    mLstCity = response.body();
                    ArrayAdapter<City> adapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1, mLstCity);
                    mSpCity.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View.OnClickListener listenerRegister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText edtName = findViewById(R.id.formRegisterName);
            EditText edtEmail = findViewById(R.id.formRegisterEmail);
            EditText edtPhone = findViewById(R.id.formRegisterPhone);
            EditText edtPassword = findViewById(R.id.formRegisterPassword);
            EditText edtConfirmPassword = findViewById(R.id.formRegisterConfirmPassword);
            EditText edtAddress = findViewById(R.id.formRegisterAddress);
            Spinner spnCity = findViewById(R.id.formRegisterSpnCity);
            City c = (City) spnCity.getSelectedItem();

            String uName = edtName.getText().toString();
            String uEmail = edtEmail.getText().toString();
            String uPhone = edtPhone.getText().toString();
            String uPassword = edtPassword.getText().toString();
            String uCPassword = edtConfirmPassword.getText().toString();
            String uAddress = edtAddress.getText().toString();
            String uRole = "user";

            if (uPassword.equals(uCPassword)) {
                User u = new User(uName, uPhone, uPassword, uEmail, c.getId(), uAddress, uRole);
                UserAPI.userApi.insertUser(u).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng, xin vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        }
    };
}