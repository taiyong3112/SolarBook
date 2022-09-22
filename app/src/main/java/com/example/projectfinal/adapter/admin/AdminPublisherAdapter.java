package com.example.projectfinal.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectfinal.R;
import com.example.projectfinal.entity.Publisher;

import java.util.List;

public class AdminPublisherAdapter extends ArrayAdapter<Publisher> {

    private Context mCtx;
    private List<Publisher> mLst;

    public AdminPublisherAdapter(@NonNull Context context, List<Publisher> objects) {
        super(context, R.layout.item_admin_publisher, objects);
        this.mCtx = context;
        this.mLst = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = convertView;
        if (item == null) {
            item = LayoutInflater.from(this.mCtx).inflate(R.layout.item_admin_category, null);
        }

        TextView txtId = item.findViewById(R.id.category_id);
        TextView txtName = item.findViewById(R.id.admin_category_name);

        Publisher p = mLst.get(position);

        txtId.setText(p.getId() + "");
        txtName.setText(p.getName());
        return item;
    }
}
