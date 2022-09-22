package com.example.projectfinal.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.projectfinal.R;
import com.example.projectfinal.adapter.admin.AdminAccountAdapter;
import com.example.projectfinal.api.UserAPI;
import com.example.projectfinal.entity.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAccountActivity extends AppCompatActivity {
    private RecyclerView mRcvAccount;
    private AdminAccountAdapter mAdapter;
    private List<User> mLst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);

        mRcvAccount = findViewById(R.id.rcvAdminAccount);
        mLst = new ArrayList<>();

        //set dữ liệu lên recycler view
        mRcvAccount.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        getListUsers();
    }

    private void getListUsers() {
        UserAPI.userApi.getListUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    mLst = response.body();
                    mAdapter = new AdminAccountAdapter(AdminAccountActivity.this, mLst);
                    mRcvAccount.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(AdminAccountActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_account, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuAdminAccountAdd:
                Intent intent = new Intent(AdminAccountActivity.this, AdminAddAccountActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        User u = mLst.get(item.getOrder());
        switch(item.getItemId()){
            case 101:
                Intent intent = new Intent(AdminAccountActivity.this, AdminUpdateAccountActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("updateUser", u);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 111:
                UserAPI.userApi.deleteUser(u.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(AdminAccountActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminAccountActivity.this, AdminAccountActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AdminAccountActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }
}