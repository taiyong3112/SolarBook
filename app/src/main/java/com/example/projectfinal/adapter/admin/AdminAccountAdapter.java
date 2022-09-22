package com.example.projectfinal.adapter.admin;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal.R;
import com.example.projectfinal.entity.User;

import java.util.List;

public class AdminAccountAdapter extends RecyclerView.Adapter<AdminAccountAdapter.AdminAccountViewHolder> {

    private Context mCtx;
    private List<User> mLst;


    public AdminAccountAdapter(Context mCtx, List<User> mLst) {
        this.mCtx = mCtx;
        this.mLst = mLst;
    }

    @NonNull
    @Override
    public AdminAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_admin_account, parent, false);
        AdminAccountViewHolder avh = new AdminAccountViewHolder(view);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAccountViewHolder holder, int position) {
        final User u = mLst.get(position);
        if (u == null) {
            return;
        }

        holder.userId.setText(String.valueOf(u.getId()));
        holder.userName.setText(u.getName());
        holder.userEmail.setText(u.getEmail());
        holder.userRole.setText(u.getRole());
    }

    @Override
    public int getItemCount() {
        if (mLst != null) {
            return mLst.size();
        }
        return 0;
    }

    public static class AdminAccountViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView userId;
        private TextView userName;
        private TextView userEmail;
        private TextView userRole;
        private LinearLayout layoutAccount;

        public AdminAccountViewHolder(@NonNull View itemView) {
            super(itemView);

            userId = itemView.findViewById(R.id.txtAdminAcountId);
            userName = itemView.findViewById(R.id.txtAdminAccountName);
            userEmail = itemView.findViewById(R.id.txtAdminAccountEmail);
            userRole = itemView.findViewById(R.id.txtAdminAccountRole);
            layoutAccount = itemView.findViewById(R.id.layoutAdminAccount);
            layoutAccount.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Lựa chọn");
            menu.add(0, 101, getAdapterPosition(), "Cập nhật");
            menu.add(0, 111, getAdapterPosition(), "Xóa");
            menu.add(0, 121, getAdapterPosition(), "Đổi mật khẩu");
        }


    }


}
