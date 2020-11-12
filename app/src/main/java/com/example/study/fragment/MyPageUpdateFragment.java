package com.example.study.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.study.MainActivity;
import com.example.study.R;
import com.example.study.databinding.FragmentMyPageUpdateBinding;
import com.example.study.databinding.FragmentMypageBinding;
import com.example.study.helper.DataBaseHelper;
import com.example.study.utils.SQLiteUtils;
import com.example.study.utils.SharedPreferencesUtils;
import com.example.study.vo.UserVO;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class MyPageUpdateFragment extends Fragment {

    private static final int REQUEST_CODE = 0;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private static String LOG_NAME = "MyPageUpdateFragment :: ";
    private FragmentMyPageUpdateBinding binding;
    private Context context;
    private Gson gson;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        gson = new Gson();
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_page_update, container, false);
        binding.setFragmentLayoutMypageUpdate(this);
        View fragment = binding.getRoot();

        String stringData = SharedPreferencesUtils.getStringData(context, getString(R.string.user_info));
        UserVO user = gson.fromJson(stringData, UserVO.class);

        binding.updateUserName.setText(user.getName());
        binding.updateUserAddress.setText(user.getAddress());
        binding.updateUserBirth.setText(String.join(".", user.getBirth().split("-")));
        binding.updateUserEmail.setText(user.getEmail());

        ActionBar actionBar = ((AppCompatActivity)context).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        container.getRootView().findViewById(R.id.drawer_button).setVisibility(View.INVISIBLE);
        container.getRootView().findViewById(R.id.write_btn).setVisibility(View.INVISIBLE);
        container.getRootView().findViewById(R.id.search_btn).setVisibility(View.INVISIBLE);

        binding.updateCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        binding.updateSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return fragment;
    }


    public void selectUserAvatar(View v) {
        Log.i("TEST :", "Helloworld");

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent();
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }

            return;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                InputStream is = null;
                Bitmap bm = null;
                try {
                    is = getActivity().getContentResolver().openInputStream(data.getData());
                     bm = BitmapFactory.decodeStream(is);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {

                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.updateUserAvatar.setImageBitmap(bm);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }
}