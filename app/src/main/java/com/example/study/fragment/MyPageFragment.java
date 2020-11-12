package com.example.study.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.study.LoginActivity;
import com.example.study.R;
import com.example.study.databinding.FragmentMypageBinding;
import com.example.study.helper.DataBaseHelper;
import com.example.study.utils.SQLiteUtils;
import com.example.study.utils.SharedPreferencesUtils;
import com.example.study.vo.UserVO;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class MyPageFragment extends Fragment {

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private static String LOG_NAME = "MyPageFragment :: ";
    private FragmentMypageBinding binding;
    private DataBaseHelper helper;
    private SQLiteUtils sqlite;
    private Context context;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        gson = new Gson();
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage, container, false);
        View fragment = binding.getRoot();

        TextView viewById = container.getRootView().findViewById(R.id.toolbar_title);
        viewById.setText("마이페이지");

        helper = new DataBaseHelper(context, getString(R.string.app_db), null, 1);
        sqlite = new SQLiteUtils(helper);

        ActionBar actionBar = ((AppCompatActivity)context).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        container.getRootView().findViewById(R.id.write_btn).setVisibility(View.VISIBLE);
        container.getRootView().findViewById(R.id.search_btn).setVisibility(View.VISIBLE);

        String stringData = SharedPreferencesUtils.getStringData(context, getString(R.string.user_info));
        UserVO user = gson.fromJson(stringData, UserVO.class);
        Log.i(LOG_NAME + " UserVO ", user.toString());
        binding.userName.setText(user.getName());
        binding.userBirth.setText(String.join(".", user.getBirth().split("-")));
        binding.userEmail.setText(user.getEmail());
        binding.userAddress.setText(user.getAddress());
        binding.userGrade.setText(user.getGrade());
        binding.userUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FragmentManager
                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.frameLayout, new MyPageUpdateFragment()).commitAllowingStateLoss();
            }
        });

        binding.inviteAllowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.inviteAllowed.setBackgroundResource(R.drawable.invite_left_radius_check);
                binding.inviteNotAllowed.setBackgroundResource(R.drawable.invite_right_radius);
            }
        });

        binding.inviteNotAllowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.inviteAllowed.setBackgroundResource(R.drawable.invite_left_radius);
                binding.inviteNotAllowed.setBackgroundResource(R.drawable.invite_right_radius_check);
            }
        });

        DecimalFormat formatter = new DecimalFormat("###,###");

        binding.userCash.setText(formatter.format(user.getCash()));

        return fragment;
    }
}
