package com.example.study.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.study.R;
import com.example.study.databinding.FragmentMypageBinding;
import com.example.study.databinding.FragmentSearchBinding;
import com.example.study.helper.DataBaseHelper;
import com.example.study.utils.SQLiteUtils;
import com.example.study.utils.SharedPreferencesUtils;
import com.example.study.vo.UserVO;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private static String LOG_NAME = "SearchFragment :: ";
    private FragmentSearchBinding binding;
    private DataBaseHelper helper;
    private SQLiteUtils sqlite;
    private Context context;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        gson = new Gson();
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        View fragment = binding.getRoot();


        binding.searchContents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchResultLayout.setVisibility(View.VISIBLE);
                binding.bottomBanner.setVisibility(View.GONE);
                List<TextView> list = Arrays.asList(binding.firstContents, binding.secondContents, binding.thirdContents);

                for (TextView text : list) {
                    String content = text.getText().toString();
                    SpannableString spannableString = new SpannableString(content);

                    String word = binding.searchWord.getText().toString();


                    int start = content.indexOf(word);
                    int end = start + word.length();

                    if(start>0) {
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fb2855")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        text.setText(spannableString);
                    }

                    text.setText(spannableString);
                }
            }
        });


        ActionBar actionBar = ((AppCompatActivity)context).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        container.getRootView().findViewById(R.id.drawer_button).setVisibility(View.INVISIBLE);
        container.getRootView().findViewById(R.id.write_btn).setVisibility(View.INVISIBLE);
        container.getRootView().findViewById(R.id.search_btn).setVisibility(View.INVISIBLE);

        TextView fragmentTitle = container.getRootView().findViewById(R.id.toolbar_title);
        ImageView fragmentTitleImage = container.getRootView().findViewById(R.id.toolbar_title_image);
        fragmentTitle.setVisibility(View.GONE);
        fragmentTitleImage.setVisibility(View.VISIBLE);


        binding.searchWord.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    binding.searchResultLayout.setVisibility(View.VISIBLE);
                    binding.bottomBanner.setVisibility(View.GONE);
                    List<TextView> list = Arrays.asList(binding.firstContents, binding.secondContents, binding.thirdContents);

                    for (TextView text : list) {
                        String content = text.getText().toString();
                        SpannableString spannableString = new SpannableString(content);

                        String word = binding.searchWord.getText().toString();


                        int start = content.indexOf(word);
                        int end = start + word.length();

                        if(start>0) {
                            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#5d8fcf")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setText(spannableString);
                        }

                        text.setText(spannableString);
                    }
                }
                return false;
            }
        });

        return fragment;
    }
}
