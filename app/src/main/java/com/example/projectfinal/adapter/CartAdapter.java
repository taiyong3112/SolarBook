package com.example.projectfinal.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal.R;
import com.example.projectfinal.activity.CartActivity;
import com.example.projectfinal.api.BookAPI;
import com.example.projectfinal.api.CartAPI;
import com.example.projectfinal.entity.Book;
import com.example.projectfinal.entity.Cart;
import com.example.projectfinal.entity.User;
import com.example.projectfinal.my_interface.ICartItemClickListener;
import com.example.projectfinal.my_interface.IGetListBook;
import com.example.projectfinal.my_interface.IGetTotalPrice;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context mCtx;
    private List<Cart> mLst;
    private User mUser;
    private IGetTotalPrice iGetTotalPrice;
    private IGetListBook iGetListBook;
    private List<Book> mLstBook;
    float totalPrice = 0;


    public CartAdapter(Context mCtx, List<Cart> mLst, User user) {
        this.mCtx = mCtx;
        this.mLst = mLst;
        this.mUser = user;
        try{
            this.iGetTotalPrice = (IGetTotalPrice) mCtx;
            this.iGetListBook = (IGetListBook) mCtx;
        }catch (ClassCastException e){
            throw new ClassCastException(e.getMessage());
        }
    }



    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_cart, parent, false);
        CartViewHolder cvh = new CartViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart c = mLst.get(position);
        mLstBook = new ArrayList<>();
        BookAPI.bookAPI.detailBook(c.getBookId()).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Book b = response.body();
                mLstBook.add(b);
                File imgFile = new File(b.getPicture());
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.bookImg.setImageBitmap(myBitmap);
                holder.bookName.setText(b.getName());
                holder.bookNumber.setText(String.valueOf(c.getBookCount()));

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                if(b.getSalePrice() != 0){
                    holder.bookPrice.setText(decimalFormat.format(b.getSalePrice()) + "đ");
                    totalPrice = totalPrice + (c.getBookCount() * b.getSalePrice());
                }else{
                    holder.bookPrice.setText(decimalFormat.format(b.getPrice()) + "đ");
                    totalPrice = totalPrice +  (c.getBookCount() * b.getPrice());
                }
                holder.setListener(new ICartItemClickListener() {
                    @Override
                    public void onButtonClick(View view, int position, int value) {
                        if(value == 1){
                            if(c.getBookCount() > 1){
                                int newCount = c.getBookCount() - 1;
                                c.setBookCount(newCount);

                                if(b.getSalePrice() != 0){
                                    totalPrice = totalPrice - b.getSalePrice();
                                }else{
                                    totalPrice = totalPrice - b.getPrice();
                                }
                            }
                            iGetTotalPrice.getCartBookTotalPrice(totalPrice);
                        }else if(value == 2){
                            if(c.getBookCount() < b.getNumber()){
                                int newCount = c.getBookCount() + 1;
                                c.setBookCount(newCount);

                                if(b.getSalePrice() != 0){
                                    totalPrice = totalPrice + b.getSalePrice();
                                }else{
                                    totalPrice = totalPrice + b.getPrice();
                                }
                            }
                            iGetTotalPrice.getCartBookTotalPrice(totalPrice);
                        }else if(value == 3){
                            AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                            builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng không ?")
                                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            CartAPI.cartApi.deleteBookFromCartByBookId(mUser.getId(), c.getBookId()).enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    if(b.getSalePrice() != 0){
                                                        totalPrice = totalPrice - (b.getSalePrice() * c.getBookCount());
                                                    }else{
                                                        totalPrice = totalPrice - (b.getPrice() * c.getBookCount());
                                                    }
                                                    iGetTotalPrice.getCartBookTotalPrice(totalPrice);
                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    Toast.makeText(mCtx, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Intent intent = new Intent(mCtx, CartActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("object_user", mUser);
                                            intent.putExtras(bundle);
                                            mCtx.startActivity(intent);
                                            Toast.makeText(mCtx, "Bạn đã xóa thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }
                        holder.bookNumber.setText(String.valueOf(c.getBookCount()));
                    }
                });
                iGetTotalPrice.getCartBookTotalPrice(totalPrice);
                iGetListBook.getListBook(mLstBook);
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(mCtx, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return mLst.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView bookImg;
        private TextView bookName;
        private ImageButton btnMinus;
        private ImageButton btnAdd;
        private ImageButton btnDelete;
        private TextView bookNumber;
        private TextView bookPrice;
        private ICartItemClickListener listener;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImg = itemView.findViewById(R.id.itemCartBookImage);
            bookName = itemView.findViewById(R.id.itemCartBookName);
            btnMinus = itemView.findViewById(R.id.itemCartMinus);
            btnAdd = itemView.findViewById(R.id.itemCartAdd);
            bookNumber = itemView.findViewById(R.id.itemCartBookNumber);
            bookPrice = itemView.findViewById(R.id.itemCartBookPrice);
            btnDelete = itemView.findViewById(R.id.itemCartDelete);

            btnMinus.setOnClickListener(this);
            btnAdd.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }

        public void setListener(ICartItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (v == btnMinus){
                listener.onButtonClick(v, getAdapterPosition(), 1);
            }else if(v == btnAdd){
                listener.onButtonClick(v, getAdapterPosition(), 2);
            }else if(v == btnDelete){
                listener.onButtonClick(v, getAdapterPosition(), 3);
            }
        }
    }
}
