package com.example.projectfinal.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class AdminAddAccountActivity extends AppCompatActivity {
    private List<User> mLstUser;
    private List<City> mLstCity;
    private Spinner mSpCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_account);

        loadCities();
        Button btnAddAccount = findViewById(R.id.btnFormAccountAdd);
        Button btnUpdateAccount = findViewById(R.id.btnFormAccountUpdate);
        btnAddAccount.setVisibility(View.VISIBLE);
        btnUpdateAccount.setVisibility(View.GONE);

        btnAddAccount.setOnClickListener(listenerAdd);
    }

    private void loadCities() {
        mLstCity = new ArrayList<>();
        mSpCity = findViewById(R.id.spnFormAccountCity);
        CityAPI.cityApi.getListCities().enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if(response.isSuccessful()){
                    mLstCity = response.body();
                    ArrayAdapter<City> adapter = new ArrayAdapter<>(AdminAddAccountActivity.this, android.R.layout.simple_list_item_1, mLstCity);
                    mSpCity.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Toast.makeText(AdminAddAccountActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View.OnClickListener listenerAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rdoAdmin = findViewById(R.id.rdoFormAccountAdmin);
            RadioButton rdoUser = findViewById(R.id.rdoFormAccountUser);

            String name = ((EditText) findViewById(R.id.edtFormAccountName)).getText().toString();
            String email = ((EditText) findViewById(R.id.edtFormAccountEmail)).getText().toString();
            String password = ((EditText) findViewById(R.id.edtFormAccountPassword)).getText().toString();
            String cPassword = ((EditText) findViewById(R.id.edtFormAccountCPassword)).getText().toString();
            String phone = ((EditText) findViewById(R.id.edtFormAccountPhone)).getText().toString();
            String address = ((EditText) findViewById(R.id.edtFormAccountAddress)).getText().toString();
            int city = ((City) mSpCity.getSelectedItem()).getId();
            String role = "";
            if(rdoAdmin.isChecked()){
                role = "admin";
            }else if(rdoUser.isChecked()){
                role = "user";
            }
            
            if(password.equals(cPassword)){
                User u = new User(name, phone, password, email, city, address, role);
                UserAPI.userApi.insertUser(u).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(AdminAddAccountActivity.this, "Thêm tài khoản mới thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminAddAccountActivity.this, AdminAccountActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(AdminAddAccountActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(AdminAddAccountActivity.this, "Mật khẩu không trùng, xin thử lại", Toast.LENGTH_SHORT).show();
            }
        }
    };
}