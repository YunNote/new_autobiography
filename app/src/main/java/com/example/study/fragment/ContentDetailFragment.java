package com.example.study.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.study.R;
import com.example.study.databinding.FragmentContentDetailBinding;
import com.example.study.databinding.FragmentMainBinding;

public class ContentDetailFragment extends Fragment {

    private Context context;
    private static String LOG_NAME = "ContentDetailFragment :: ";
    private FragmentContentDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_detail, container, false);
        View root = binding.getRoot();

        ActionBar actionBar = ((AppCompatActivity)context).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        container.getRootView().findViewById(R.id.drawer_button).setVisibility(View.INVISIBLE);
        container.getRootView().findViewById(R.id.write_btn).setVisibility(View.INVISIBLE);
        container.getRootView().findViewById(R.id.search_btn).setVisibility(View.INVISIBLE);

        TextView viewById = container.getRootView().findViewById(R.id.toolbar_title);
        viewById.setText("유민학교 친구들 기억나니? ");

        return root;
    }
}
