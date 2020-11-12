package com.example.study.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View root = binding.getRoot();
        
        TextView viewById = container.getRootView().findViewById(R.id.toolbar_title);
        viewById.setText("아이템샵");

        return root;
    }
}
