package com.example.projectfinal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectfinal.R;
import com.example.projectfinal.api.CartAPI;
import com.example.projectfinal.entity.Book;
import com.example.projectfinal.entity.Cart;
import com.example.projectfinal.entity.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailActivity extends AppCompatActivity {

    private List<Cart> mLstCart = new ArrayList<>();
    private int mCartQuantity;
    private int mBookNumber = 0;
    private ImageButton mBtnMinus;
    private TextView mTxtBookNumber;
    private Button mBtnAddToCart;
    private MenuItem mMenuItem;
    private Book mBook;
    private User mUser;
    private TextView mCartNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        mBook = (Book) bundle.get("object_book");
        mUser = (User) bundle.get("object_user");
        TextView tvName = findViewById(R.id.book_name);
        tvName.setText(mBook.getName());
        ImageView imgView = findViewById(R.id.book_image);
        //Hien thi anh
        File imgFile = new File(mBook.getPicture());
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        imgView.setImageBitmap(myBitmap);
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setRating(mBook.getRating());
        TextView tvAuthor = findViewById(R.id.book_author);
        tvAuthor.setText(mBook.getAuthor());
        TextView tvPrice = findViewById(R.id.book_price);
        tvPrice.setText(mBook.getPrice().toString());
        TextView tvSalePrice = findViewById(R.id.sale_price);
        tvSalePrice.setText(mBook.getSalePrice().toString());
        TextView tvDescription = findViewById(R.id.book_description);
        tvDescription.setText(mBook.getDescription());

        //xử lý nút + - số lượng sách
        mTxtBookNumber = findViewById(R.id.txtBookNumber);
        mTxtBookNumber.setText(String.valueOf(mBookNumber));
        ImageButton btnAdd = findViewById(R.id.btnBookNumberAdd);
        mBtnMinus = findViewById(R.id.btnBookNumberMinus);
        mBtnMinus.setVisibility(View.INVISIBLE);
        btnAdd.setOnClickListener(listenerAddNumber);
        mBtnMinus.setOnClickListener(listenerMinusNumber);

        //xử lý nút thêm giỏ hàng
        mBtnAddToCart = findViewById(R.id.btnAddtoCart);
        mBtnAddToCart.setEnabled(false);
        mBtnAddToCart.setOnClickListener(listenerAddToCart);

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
                Toast.makeText(BookDetailActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCart:
                Intent intent = new Intent(BookDetailActivity.this, CartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_user", mUser);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener listenerAddToCart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mBookNumber == 0){
                Toast.makeText(BookDetailActivity.this, "Vui lòng chọn số lượng sách bạn muốn mua", Toast.LENGTH_SHORT).show();
            }
            //set số lượng sách chọn vào Cart
            mCartQuantity = mCartQuantity + mBookNumber;
            View actionView = mMenuItem.getActionView();
            mCartNumber = actionView.findViewById(R.id.cart_number);
            mCartNumber.setText(String.valueOf(mCartQuantity));

            Cart c = new Cart(mBookNumber, mBook.getId(), mUser.getId());
            CartAPI.cartApi.insertCart(c).enqueue(new Callback<Cart>() {
                @Override
                public void onResponse(Call<Cart> call, Response<Cart> response) {
                    Toast.makeText(BookDetailActivity.this, "Bạn đã thêm " + mBookNumber + " sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Cart> call, Throwable t) {
                    Toast.makeText(BookDetailActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private View.OnClickListener listenerAddNumber = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mBookNumber = mBookNumber + 1;
            mTxtBookNumber.setText(String.valueOf(mBookNumber));
            mBtnMinus.setVisibility(View.VISIBLE);
            mBtnAddToCart.setEnabled(true);
        }
    };

    private View.OnClickListener listenerMinusNumber = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mBookNumber = mBookNumber - 1;
            if (mBookNumber == 0){
                mBtnMinus.setVisibility(View.INVISIBLE);
                mBtnAddToCart.setEnabled(false);
            }
            mTxtBookNumber.setText(String.valueOf(mBookNumber));
        }
    };
}