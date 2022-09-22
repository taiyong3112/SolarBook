package com.example.projectfinal.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectfinal.activity.LoginActivity;
import com.example.projectfinal.activity.MainActivity;
import com.example.projectfinal.R;
import com.example.projectfinal.activity.admin.AdminActivity;
import com.example.projectfinal.entity.User;

public class AccountFragment extends Fragment {
    private MainActivity mMainActivity;
    private SharedPreferences sharedPreferences;
    private TextView mTxtUserName;
    private TextView mTxtUserId;
    private User mUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        sharedPreferences = getContext().getSharedPreferences("UserInfo", MODE_PRIVATE);
        mMainActivity = (MainActivity) getActivity();
        mUser = mMainActivity.getmUser();

        mTxtUserName = view.findViewById(R.id.txtUserName);
        mTxtUserId = view.findViewById(R.id.txtUserId);

        mTxtUserName.setText(mUser.getName());


        Button btnUserDetail = view.findViewById(R.id.btnUserDetail);
        Button btnHistoryOder = view.findViewById(R.id.btnUserHistoryOrder);
        Button btnToAdmin = view.findViewById(R.id.btnToAdminPage);
        Button btnLogout = view.findViewById(R.id.btnLogout);

        //nếu được chuyển từ AdminActivity qua
        if(mUser.getRole().equals("admin")){
            btnUserDetail.setVisibility(View.GONE);
            btnHistoryOder.setVisibility(View.GONE);
            btnToAdmin.setVisibility(View.VISIBLE);
        }



        btnLogout.setOnClickListener(listenerLogout);
        btnUserDetail.setOnClickListener(listenerUserDetail);
        btnToAdmin.setOnClickListener(listenerToAdmin);
        return view;
    }

    private View.OnClickListener listenerLogout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //clear dữ liệu trong SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Toast.makeText(mMainActivity, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener listenerUserDetail = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener listenerToAdmin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AdminActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("userInfo", mUser);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
}
