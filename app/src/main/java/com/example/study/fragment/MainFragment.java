package com.example.study.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.study.R;
import com.example.study.adapter.ImagePagerAdapter;
import com.example.study.databinding.FragmentMainBinding;
import com.example.study.databinding.FragmentMypageBinding;

public class MainFragment extends Fragment {

    private Context context;
    private static String LOG_NAME = "MainFragment :: ";
    private FragmentMainBinding binding;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private ActionBar actionBar;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        binding.setMainFragmentLayout(this);

        View root = binding.getRoot();

        TextView viewById = container.getRootView().findViewById(R.id.toolbar_title);
        viewById.setText("행복e조");

        return root;
    }


    public void contentDetail(View v) {

        fragmentManager = getActivity().getSupportFragmentManager();
        String fragmentTag = fragmentManager.getClass().getSimpleName();
        transaction = fragmentManager.beginTransaction();
        getActivity().getSupportFragmentManager().popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.addToBackStack(fragmentTag);
        transaction.replace(R.id.frameLayout, new ContentDetailFragment()).commitAllowingStateLoss();
    }


}
