package com.example.study.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.study.R;
import com.example.study.databinding.FragmentItemshopBinding;
import com.google.gson.Gson;

public class ItemShopFragment extends Fragment {

    private FragmentItemshopBinding binding;
    private static String LOG_NAME = "ItemShopFragment :: ";
    private Context context;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_itemshop, container, false);
        binding.setItemShopFragmentLayout(this);
        View root = binding.getRoot();

        TextView viewById = container.getRootView().findViewById(R.id.toolbar_title);
        viewById.setText("아이템샵");

        return root;
    }

    public void preparing(View v) {
        Toast.makeText(context, "준비중 입니다.", Toast.LENGTH_SHORT).show();
    }

    public void service1Toggle(View v) {
        if ("true".equals(binding.service1.getTag())) {
            Animation animation = new AlphaAnimation(0, 1);
            animation.setDuration(200);
            binding.service1Detail.setVisibility(View.VISIBLE);
            binding.service1Detail.setAnimation(animation);
            binding.service1.setTag("false");

        } else {
            binding.service1Detail.setVisibility(View.GONE);
            binding.service1.setTag("true");
        }
    }

    public void service2Toggle(View v) {
        if ("true".equals(binding.service2.getTag())) {
            Animation animation = new AlphaAnimation(0, 1);
            animation.setDuration(200);
            binding.service2Detail.setVisibility(View.VISIBLE);
            binding.service2Detail.setAnimation(animation);
            binding.service2.setTag("false");
        } else {
            binding.service2Detail.setVisibility(View.GONE);
            binding.service2.setTag("true");
        }

    }

    public void service3Toggle(View v) {

        if ("true".equals(binding.service3.getTag())) {
            Animation animation = new AlphaAnimation(0, 1);
            animation.setDuration(200);
            binding.service3Detail.setVisibility(View.VISIBLE);
            binding.service3Detail.setAnimation(animation);
            binding.service3.setTag("false");
        } else {
            binding.service3Detail.setVisibility(View.GONE);
            binding.service3.setTag("true");
        }
    }
}
