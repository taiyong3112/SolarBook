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

public class AdminUpdateAccountActivity extends AppCompatActivity {
    private List<City> mLstCity;
    private User mUser;
    private Spinner mSpCity;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtCPassword;
    private EditText edtPhone;
    private EditText edtAddress;
    private RadioButton rdoAdmin;
    private RadioButton rdoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_account);

        //khai báo các thành phần trên form
        edtName = findViewById(R.id.edtFormAccountName);
        edtEmail = findViewById(R.id.edtFormAccountEmail);
        edtPassword = findViewById(R.id.edtFormAccountPassword);
        edtCPassword = findViewById(R.id.edtFormAccountCPassword);
        edtPhone = findViewById(R.id.edtFormAccountPhone);
        edtAddress = findViewById(R.id.edtFormAccountAddress);
        rdoAdmin = findViewById(R.id.rdoFormAccountAdmin);
        rdoUser = findViewById(R.id.rdoFormAccountUser);
        Button btnAdd = findViewById(R.id.btnFormAccountAdd);
        Button btnUpdate = findViewById(R.id.btnFormAccountUpdate);

        //ẩn hiện thị các thành phần mong muốn
        edtPassword.setVisibility(View.GONE);
        edtCPassword.setVisibility(View.GONE);
        btnAdd.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.VISIBLE);

        mLstCity = new ArrayList<>();
        mSpCity = findViewById(R.id.spnFormAccountCity);
        CityAPI.cityApi.getListCities().enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                mLstCity = response.body();
                ArrayAdapter<City> adapter = new ArrayAdapter<>(AdminUpdateAccountActivity.this, android.R.layout.simple_list_item_1, mLstCity);
                mSpCity.setAdapter(adapter);
                //set dữ liệu Thành Phố của User lên Spinner
                int posCity = 0;
                for (int i = 0; i < mLstCity.size(); i++) {
                    if (mLstCity.get(i).getId() == mUser.getCityId()) {
                        posCity = i;
                        break;
                    }
                }
                mSpCity.setSelection(posCity);
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Toast.makeText(AdminUpdateAccountActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });

        //lấy object được truyền từ AdminAccountActivity
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        mUser = (User) bundle.get("updateUser");

        //load dữ liệu của User lên các thành phần trên form
        edtName.setText(mUser.getName());
        edtEmail.setText(mUser.getEmail());
        edtPhone.setText(mUser.getPhone());
        edtAddress.setText(mUser.getAddress());
        if (mUser.getRole() == "admin") {
            rdoAdmin.setChecked(true);
        } else {
            rdoUser.setChecked(true);
        }

        btnUpdate.setOnClickListener(listenerUpdate);
    }

    //Hàm load dữ liệu lên Spinner City
    private void loadCities() {

    }

    //Hàm gọi sự kiện Update
    private View.OnClickListener listenerUpdate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            String phone = edtPhone.getText().toString();
            String address = edtAddress.getText().toString();
            String role = "";
            if (rdoAdmin.isChecked()) {
                role = "admin";
            } else if (rdoUser.isChecked()) {
                role = "user";
            }
            City c = (City) mSpCity.getSelectedItem();

            User u = new User(mUser.getId(), name, phone, email, c.getId(), address, role);
            UserAPI.userApi.updateUser(u).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AdminUpdateAccountActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminUpdateAccountActivity.this, AdminAccountActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(AdminUpdateAccountActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}