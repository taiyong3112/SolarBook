package com.example.projectfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal.R;
import com.example.projectfinal.activity.BookDetailActivity;
import com.example.projectfinal.entity.Book;
import com.example.projectfinal.entity.User;


import java.io.File;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context mCtx;
    private List<Book> mLst;
    private User mUser;


    public BookAdapter(Context context, List<Book> list, User user) {
        this.mCtx = context;
        this.mLst = list;
        this.mUser = user;
    }

    public BookAdapter(Context context, List<Book> list) {
        this.mCtx = context;
        this.mLst = list;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_book, parent, false);
        BookViewHolder bvh = new BookViewHolder(view);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        final Book b = mLst.get(position);
        //Hien thi anh
        File imgFile = new File(b.getPicture());
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        holder.bookImg.setImageBitmap(myBitmap);

        holder.bookName.setText(b.getName());
        holder.bookAuthor.setText(b.getAuthor());
        holder.price.setText(String.format("%.0f",b.getPrice()));
        holder.salePrice.setText(String.format("%.0f",b.getSalePrice()));
        holder.ratingBar.setRating(b.getRating());
        //khi ấn vào hình quyển sách
        holder.layoutDetailBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetailBook(b);
            }
        });
    }

    //kịch bản khi nhấn vào hình quyển sách
    private void onClickGoToDetailBook(Book b) {
        Intent intent = new Intent(mCtx, BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_book", b);
        bundle.putSerializable("object_user", mUser);
        intent.putExtras(bundle);
        mCtx.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mLst != null) {
            return mLst.size();
        }
        return 0;
    }

    //Khai báo các thành phần trong view
    public static class BookViewHolder extends RecyclerView.ViewHolder {

        private TextView bookName;
        private ImageView bookImg;
        private TextView bookAuthor;
        private RatingBar ratingBar;
        private TextView price;
        private TextView salePrice;
        private TextView category;
        private TextView description;
        private LinearLayout layoutDetailBook;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.book_name);
            bookImg = itemView.findViewById(R.id.book_image);
            bookAuthor = itemView.findViewById(R.id.book_author);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            price = itemView.findViewById(R.id.book_price);
            salePrice = itemView.findViewById(R.id.sale_price);
            category = itemView.findViewById(R.id.book_category);
            description = itemView.findViewById(R.id.book_description);
            layoutDetailBook = itemView.findViewById(R.id.layout_detail_book);
        }
    }
}