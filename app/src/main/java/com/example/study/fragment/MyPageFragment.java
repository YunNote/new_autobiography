package com.example.study.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView fragmentTitle = container.getRootView().findViewById(R.id.toolbar_title);
        ImageView fragmentTitleImage = container.getRootView().findViewById(R.id.toolbar_title_image);
        fragmentTitle.setText("마이페이지");
        fragmentTitle.setVisibility(View.VISIBLE);
        fragmentTitleImage.setVisibility(View.GONE);

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

//        binding.setNotification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "알림 설정이 되었습니다.", Toast.LENGTH_LONG).show();
//            }
//        });

        binding.inviteAllowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.inviteAllowed.setBackgroundResource(R.drawable.invite_left_radius_check);
                binding.inviteAllowed.setTextColor(Color.parseColor("#fb2855"));
                binding.inviteNotAllowed.setBackgroundResource(R.drawable.invite_right_radius);
                binding.inviteNotAllowed.setTextColor(Color.parseColor("#666666"));

            }
        });

        binding.inviteNotAllowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.inviteAllowed.setBackgroundResource(R.drawable.invite_left_radius);
                binding.inviteAllowed.setTextColor(Color.parseColor("#666666"));
                binding.inviteNotAllowed.setBackgroundResource(R.drawable.invite_right_radius_check);
                binding.inviteNotAllowed.setTextColor(Color.parseColor("#fb2855"));

            }
        });

        binding.alarmAllowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "알림 설정이 되었습니다.", Toast.LENGTH_SHORT).show();
                binding.alarmAllowed.setBackgroundResource(R.drawable.invite_left_radius_check);
                binding.alarmAllowed.setTextColor(Color.parseColor("#fb2855"));
                binding.alarmNotAllowed.setBackgroundResource(R.drawable.invite_right_radius);
                binding.alarmNotAllowed.setTextColor(Color.parseColor("#666666"));

            }
        });

        binding.alarmNotAllowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "알림 설정이 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                binding.alarmAllowed.setBackgroundResource(R.drawable.invite_left_radius);
                binding.alarmAllowed.setTextColor(Color.parseColor("#666666"));
                binding.alarmNotAllowed.setBackgroundResource(R.drawable.invite_right_radius_check);
                binding.alarmNotAllowed.setTextColor(Color.parseColor("#fb2855"));
            }
        });

        DecimalFormat formatter = new DecimalFormat("###,###");

        binding.userCash.setText(formatter.format(user.getCash()));

        return fragment;
    }
}
