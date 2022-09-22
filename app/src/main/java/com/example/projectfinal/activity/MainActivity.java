package com.example.projectfinal.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.projectfinal.R;
import com.example.projectfinal.adapter.ViewPagerAdapter;
import com.example.projectfinal.api.CartAPI;
import com.example.projectfinal.api.UserAPI;
import com.example.projectfinal.entity.Book;
import com.example.projectfinal.entity.Cart;
import com.example.projectfinal.entity.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavigationView;
    private ViewPager mViewPager;
    private SharedPreferences sharedPreferences;
    private User mUser;

    private List<Cart> mLstCart = new ArrayList<>();
    private int mCartQuantity;
    private MenuItem mMenuItem;
    private TextView mCartNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.bottomNav);
        mViewPager = findViewById(R.id.viewPager);
        Bundle bundle = getIntent().getExtras();
        setUpViewPager();
        mNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuHome:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.menuCategory:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.menuNews:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.menuAccount:
                        mViewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });


        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        //lấy tên và id người dùng

        if (sharedPreferences.contains("uInfo")) {
            Gson gson = new Gson();
            String uJson = sharedPreferences.getString("uInfo", null);
            mUser = gson.fromJson(uJson, User.class);
        } else{
            if(bundle.containsKey("userInfo")){
                mUser = (User) bundle.get("userInfo");
            }else{
                mUser = (User) bundle.get("adInfo");
            }
        }


    }

    //hàm thiết lập ViewPager
    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mNavigationView.getMenu().findItem(R.id.menuHome).setChecked(true);
                        break;
                    case 1:
                        mNavigationView.getMenu().findItem(R.id.menuCategory).setChecked(true);
                        break;
                    case 2:
                        mNavigationView.getMenu().findItem(R.id.menuNews).setChecked(true);
                        break;
                    case 3:
                        mNavigationView.getMenu().findItem(R.id.menuAccount).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);

        CartAPI.cartApi.getBookCartByUserId(mUser.getId()).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                mLstCart = response.body();
                mMenuItem = menu.findItem(R.id.menuCart);
                View actionView = mMenuItem.getActionView();
                mCartNumber = actionView.findViewById(R.id.cart_number);
                if(mLstCart == null){
                    mCartQuantity = 0;
                    mCartNumber.setText(String.valueOf(mCartQuantity));
                }else{
                    for (Cart c: mLstCart) {
                        mCartQuantity = mCartQuantity + c.getBookCount();
                    }
                    mCartNumber.setText(String.valueOf(mCartQuantity));
                }

                actionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOptionsItemSelected(mMenuItem);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCart:
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_user", mUser);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public User getmUser() {
        return mUser;
    }

}