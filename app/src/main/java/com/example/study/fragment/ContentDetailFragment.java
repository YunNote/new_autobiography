package com.example.study.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.study.R;
import com.example.study.databinding.FragmentContentDetailBinding;
import com.example.study.databinding.FragmentMainBinding;
import com.example.study.listener.OnSwipeTouchListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ContentDetailFragment extends Fragment {

    private Context context;
    private static String LOG_NAME = "ContentDetailFragment :: ";
    private FragmentContentDetailBinding binding;
    private Gson gson;
    private int index = 1;
    private int totalCount = 0;
    List<Integer> thumbnailList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        gson = new Gson();
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_detail, container, false);
        binding.setContentDetailFragmentLayout(this);
        View root = binding.getRoot();

        ActionBar actionBar = ((AppCompatActivity)context).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        container.getRootView().findViewById(R.id.drawer_button).setVisibility(View.INVISIBLE);
        container.getRootView().findViewById(R.id.write_btn).setVisibility(View.INVISIBLE);
        container.getRootView().findViewById(R.id.search_btn).setVisibility(View.INVISIBLE);

        TextView fragmentTitle = container.getRootView().findViewById(R.id.toolbar_title);
        ImageView fragmentTitleImage = container.getRootView().findViewById(R.id.toolbar_title_image);

        fragmentTitle.setVisibility(View.VISIBLE);
        fragmentTitleImage.setVisibility(View.GONE);


        Bundle bundle = getArguments();
        String thumbnailListString = bundle.getString("thumbnailList");
        boolean flag = bundle.getBoolean("flag");

        thumbnailList = gson.fromJson(thumbnailListString, new TypeToken<List<Integer>>(){}.getType());
        totalCount = thumbnailList.size();
        changeImageCount(1 , totalCount);

        binding.detailMainImage.setImageResource(thumbnailList.get(0));

        for (int i = 0; i< thumbnailList.size(); i++) {
            ImageView thumbnail = new ImageView(context);  // 새로 추가할 imageView 생성
            int marginRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
            LinearLayout.LayoutParams imageViewLayout = new LinearLayout.LayoutParams(width,height);
            imageViewLayout.rightMargin = marginRight;

            thumbnail.setTag(i + 1);
            thumbnail.setLayoutParams(imageViewLayout);
            thumbnail.setImageResource(thumbnailList.get(i));
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thumbnailImageClick(v);
                }
            });

            binding.horizontalScrollViewList.addView(thumbnail);
        }

        binding.commentSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat format = new SimpleDateFormat ( "yyyy년 MM월 dd일 a KK:mm");
                Date time = new Date();

                String currentTime = format.format(time);

                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout rootLayout = binding.commentList;
                LinearLayout inflate = (LinearLayout)inflater.inflate(R.layout.dynamic_comment_layout, rootLayout, false);

                TextView commentText = inflate.findViewById(R.id.comment_text);
                commentText.setText(binding.commentWrite.getText());

                TextView commentTime = inflate.findViewById(R.id.comment_time);
                commentTime.setText(currentTime);

                binding.commentList.addView(inflate);


                Toast.makeText(context, " 댓글이 저장되었습니다." , Toast.LENGTH_SHORT).show();
                binding.commentLayout.setVisibility(View.GONE);
                binding.commentAddLayout.setVisibility(View.VISIBLE);
            }
        });



        binding.detailMainImage.setOnTouchListener(new OnSwipeTouchListener(context) {
            private View v;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                this.v = v;
                return super.onTouch(v, event);
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                leftChangeImage(v);
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                rightChangeImage(v);

            }
        });

        if(flag) {
            fragmentTitle.setText(R.string.family_title);
            binding.contentDetailComment.setVisibility(View.GONE);
            binding.familyTag.setVisibility(View.VISIBLE);
            binding.detailTitle.setText(R.string.family_title);
            binding.detailText.setText(R.string.family_text);

        }else {
            fragmentTitle.setText(R.string.friend_title);
            binding.friendTag.setVisibility(View.VISIBLE);
            binding.detailTitle.setText(R.string.friend_title);
            binding.detailText.setText(R.string.friend_text);
        }

        return root;
    }


    public void leftChangeImage(View v){

        if (index <= 1 ) {
            index = totalCount;
            changeImageCount(index,totalCount);
            binding.detailMainImage.setImageResource(thumbnailList.get(index-1));
        }else {
            index = index - 1;
            changeImageCount(index,totalCount);
            binding.detailMainImage.setImageResource(thumbnailList.get(index-1));
        }
    }

    public void rightChangeImage(View v ){
        if(index <= totalCount - 1) {
            binding.detailMainImage.setImageResource(thumbnailList.get(index));
            index = index +1;
            changeImageCount(index,totalCount);
        }else {
            index = 1;
            binding.detailMainImage.setImageResource(thumbnailList.get(index-1));
            changeImageCount(index , totalCount);

        }
    }

    public void changeImageCount(int i , int total) {
        binding.contentCount.setText(i + "/" + total);
        if(i == 1) {
            binding.imageLeft.setVisibility(View.GONE);
            binding.imageRight.setVisibility(View.VISIBLE);
        }else if (i == total) {
            binding.imageLeft.setVisibility(View.VISIBLE);
            binding.imageRight.setVisibility(View.GONE);
        }else {
            binding.imageLeft.setVisibility(View.VISIBLE);
            binding.imageRight.setVisibility(View.VISIBLE);
        }
    }

    public void showCommentLayout(View v) {
        binding.commentAddLayout.setVisibility(View.GONE);
        binding.commentLayout.setVisibility(View.VISIBLE);
    }

    public void setHorizontalScrollViewSmoothScrollEvent(View v) {
        binding.horizontalScrollView.smoothScrollTo(v.getLeft(), 0);
    }

    public void setMainImageResource(View v) {
        ImageView image = (ImageView)v;
        binding.detailMainImage.setImageDrawable(image.getDrawable());
        index = Integer.parseInt(v.getTag().toString());
        changeImageCount(index , totalCount);
    }

    public void thumbnailImageClick(View v) {
        setHorizontalScrollViewSmoothScrollEvent(v);
        setMainImageResource(v);

        binding.detailParentScrollView.scrollTo(0,0);
    }
}
