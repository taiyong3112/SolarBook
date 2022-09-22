package com.example.projectfinal.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfinal.R;
import com.example.projectfinal.entity.Book;
import com.example.projectfinal.entity.News;

import java.io.File;
import java.text.SimpleDateFormat;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        TextView edtName = findViewById(R.id.news_name);
        TextView edtDescription = findViewById(R.id.news_description);
        TextView edtDetail = findViewById(R.id.news_detail);
        ImageView edtPicture = findViewById(R.id.news_picture);
        TextView edtCreatedDate = findViewById(R.id.news_createdDate);

        News mNews = (News) bundle.get("object_news");

        edtName.setText(mNews.getName());
        edtDescription.setText(mNews.getDescription());
        edtDetail.setText(mNews.getDetail());
        //hiển thị ảnh
        File imgFile = new File(mNews.getPicture());
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        edtPicture.setImageBitmap(myBitmap);

        //chuyen kieu du lieu Date sang kieu String
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        edtCreatedDate.setText(formatter.format(mNews.getCreatedDate()));

    }
}