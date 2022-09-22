package com.example.projectfinal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectfinal.R;
import com.example.projectfinal.adapter.CartAdapter;
import com.example.projectfinal.api.BookAPI;
import com.example.projectfinal.api.CartAPI;
import com.example.projectfinal.entity.Book;
import com.example.projectfinal.entity.Cart;
import com.example.projectfinal.entity.User;
import com.example.projectfinal.my_interface.IGetListBook;
import com.example.projectfinal.my_interface.IGetTotalPrice;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements IGetTotalPrice, IGetListBook {


    @Override
    public void getCartBookTotalPrice(float totalPrice) {
        txtCartTotalPrice = findViewById(R.id.txtCartTotalPrice);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtCartTotalPrice.setText(decimalFormat.format(totalPrice) + " VNĐ");
        mTotalPrice = totalPrice;
    }

    @Override
    public void getListBook(List<Book> listBook) {
        mLstBook = listBook;
    }

    public interface IGetListCart {
        void getListCart(List<Cart> lstCart);
    }

    private RecyclerView mRcvCart;
    private CartAdapter mCartAdapter;
    private User mUser;
    private List<Book> mLstBook = new ArrayList<>();
    private List<Cart> mLstCart = new ArrayList<>();
    private TextView txtCartTotalPrice;
    private float mTotalPrice;
    private Button mBtnPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Bundle bundle = getIntent().getExtras();
        mUser = (User) bundle.get("object_user");
        mRcvCart = findViewById(R.id.rcvCartItem);

        getCarts(new IGetListCart() {
            @Override
            public void getListCart(List<Cart> lstCart) {
                mLstCart = lstCart;
                mCartAdapter = new CartAdapter(CartActivity.this, mLstCart, mUser);
                mRcvCart.setAdapter(mCartAdapter);
                mRcvCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                mRcvCart.addItemDecoration(new DividerItemDecoration(CartActivity.this, DividerItemDecoration.HORIZONTAL));
            }
        });

        mBtnPayment = findViewById(R.id.btnToPayment);
        mBtnPayment.setOnClickListener(listenerToPayment);
    }


    public void getCarts(final IGetListCart callback) {
        CartAPI.cartApi.getBookCartByUserId(mUser.getId()).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                List<Cart> lstCart = response.body();
                callback.getListCart(lstCart);
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View.OnClickListener listenerToPayment = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mLstCart == null){
                Toast.makeText(CartActivity.this, "Không có sản phẩm trong giỏ hàng, không thể thanh toán", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_user", mUser);
                intent.putExtras(bundle);
                intent.putExtra("listBook", (Serializable) mLstBook);
                intent.putExtra("listCart", (Serializable) mLstCart);
                intent.putExtra("totalPrice", mTotalPrice);
                startActivity(intent);

            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CartActivity.this, MainActivity.class);
        startActivity(intent);
    }
}