package com.example.solarbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solarbook.R;
import com.example.solarbook.entity.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    private Context mCtx;
    private List<Book> mLst;

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
        Book b = mLst.get(position);
        if(b == null){
            return;
        }
        holder.imgBook.setImageResource(b.getImg());
        holder.txtBookName.setText(b.getName());
    }

    @Override
    public int getItemCount() {
        if(mLst != null){
            if(mLst.size() > 4){
                return 4;
            }
            return mLst.size();
        }
        return 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgBook;
        private TextView txtBookName;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBook = itemView.findViewById(R.id.itemBookImg);
            txtBookName = itemView.findViewById(R.id.itemBookName);
        }
    }
}
