package com.example.study.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.study.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImagePagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    private LayoutInflater inflater;
    private Context context;

    List<String> viewpagerList = Arrays.asList(
            "dummy_update_thumb", "dummy_update_movie"
    );


    public ImagePagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return viewpagerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == ((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewpager_activity, container , false);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(context.getResources().getIdentifier(viewpagerList.get(position),"drawable", context.getPackageName()));

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }
}
