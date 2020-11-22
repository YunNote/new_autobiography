package com.example.study.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment {

    private Context context;
    private static String LOG_NAME = "MainFragment :: ";
    private FragmentMainBinding binding;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private ActionBar actionBar;
    private Gson gson;
    public List<Integer> familyList = Arrays.asList(R.drawable.family_01, R.drawable.family_02,R.drawable.family_03,R.drawable.family_04,R.drawable.family_05,R.drawable.family_06,R.drawable.family_07,R.drawable.family_08 );
    public List<Integer> friendList = Arrays.asList(R.drawable.friend_01, R.drawable.friend_02, R.drawable.friend_03, R.drawable.friend_04, R.drawable.friend_05);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        binding.setMainFragmentLayout(this);
        gson = new Gson();

        View root = binding.getRoot();

        TextView fragmentTitle = container.getRootView().findViewById(R.id.toolbar_title);
        ImageView fragmentTitleImage = container.getRootView().findViewById(R.id.toolbar_title_image);
        fragmentTitle.setVisibility(View.GONE);
        fragmentTitleImage.setVisibility(View.VISIBLE);


        return root;
    }


    public void familyContentDetail(View v) {

        ContentDetailFragment contentDetailFragment = new ContentDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("thumbnailList" , gson.toJson(familyList));
        bundle.putBoolean("flag" , true);
        contentDetailFragment.setArguments(bundle);

        fragmentManager = getActivity().getSupportFragmentManager();
        String fragmentTag = fragmentManager.getClass().getSimpleName();
        transaction = fragmentManager.beginTransaction();
        getActivity().getSupportFragmentManager().popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.addToBackStack(fragmentTag);
        transaction.replace(R.id.frameLayout, contentDetailFragment).commitAllowingStateLoss();
    }

    public void friendContentDetail(View v) {

        ContentDetailFragment contentDetailFragment = new ContentDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("thumbnailList" , gson.toJson(friendList));
        bundle.putBoolean("flag" , false);

        contentDetailFragment.setArguments(bundle);

        fragmentManager = getActivity().getSupportFragmentManager();
        String fragmentTag = fragmentManager.getClass().getSimpleName();
        transaction = fragmentManager.beginTransaction();
        getActivity().getSupportFragmentManager().popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.addToBackStack(fragmentTag);
        transaction.replace(R.id.frameLayout, contentDetailFragment).commitAllowingStateLoss();
    }


}
