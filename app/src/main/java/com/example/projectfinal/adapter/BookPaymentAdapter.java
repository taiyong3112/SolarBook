package com.example.projectfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal.R;
import com.example.projectfinal.api.BookAPI;
import com.example.projectfinal.entity.Book;
import com.example.projectfinal.entity.Cart;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookPaymentAdapter extends RecyclerView.Adapter<BookPaymentAdapter.BookPaymentViewHolder> {
    private List<Cart> mLstCart;
    private Context mCtx;

    public BookPaymentAdapter(List<Cart> mLstCart, Context mCtx) {
        this.mLstCart = mLstCart;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public BookPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_payment_book, parent, false);
        BookPaymentViewHolder bvh = new BookPaymentViewHolder(view);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookPaymentViewHolder holder, int position) {
        Cart c = mLstCart.get(position);
        BookAPI.bookAPI.detailBook(c.getBookId()).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Book b = response.body();
                holder.bookName.setText(b.getName() + " X " + c.getBookCount());
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                float totalPrice = 0;
                if(b.getSalePrice() != 0){
                    totalPrice = c.getBookCount() * b.getSalePrice();
                    holder.bookPrice.setText(decimalFormat.format(totalPrice) + "đ");
                }else{
                    totalPrice = c.getBookCount() * b.getPrice();
                    holder.bookPrice.setText(decimalFormat.format(totalPrice) + "đ");
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(mCtx, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLstCart.size();
    }

    public static class BookPaymentViewHolder extends RecyclerView.ViewHolder {
        private TextView bookName, bookPrice;
        public BookPaymentViewHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.itemPaymentBookName);
            bookPrice = itemView.findViewById(R.id.itemPaymentBookPrice);
        }
    }
}
