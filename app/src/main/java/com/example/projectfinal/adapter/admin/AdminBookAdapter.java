package com.example.projectfinal.adapter.admin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectfinal.R;
import com.example.projectfinal.activity.admin.AdminCategoryActivity;
import com.example.projectfinal.api.CategoryAPI;
import com.example.projectfinal.api.PublisherAPI;
import com.example.projectfinal.entity.Book;
import com.example.projectfinal.entity.Category;
import com.example.projectfinal.entity.Publisher;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBookAdapter extends ArrayAdapter<Book> {

    private Context mCtx;
    private List<Book> mListBook;

    public AdminBookAdapter(@NonNull Context context, List<Book> objects) {
        super(context, R.layout.item_admin_book, objects);
        this.mCtx = context;
        this.mListBook = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Book c = mListBook.get(position);

        View item = convertView;
        if (item == null) {
            item = LayoutInflater.from(this.mCtx).inflate(R.layout.item_admin_book, null);
        }

        ImageView imgBook = item.findViewById(R.id.img_book);
        TextView txtName = item.findViewById(R.id.book_name);
        TextView txtAuthor = item.findViewById(R.id.author_name);
        TextView txtPrice = item.findViewById(R.id.price);
        TextView txtSale = item.findViewById(R.id.sale_price);
        TextView txtNumber = item.findViewById(R.id.book_number);

        File imgFile = new File(c.getPicture());
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        imgBook.setImageBitmap(myBitmap);

        txtName.setText(c.getName());
        txtAuthor.setText(c.getAuthor());
        txtPrice.setText(String.format("%.0f", c.getPrice()));
        txtSale.setText(String.format("%.0f", c.getSalePrice()));
        txtNumber.setText(String.valueOf(c.getNumber()));
        return item;
    }
}