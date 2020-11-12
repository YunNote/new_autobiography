package com.example.study;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.study.adapter.CustomSpinnerAdapter;
import com.example.study.databinding.ActivityWriteBinding;
import com.google.android.flexbox.FlexboxLayout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class WriteActivity extends AppCompatActivity {

    private ActivityWriteBinding binding;
    private Context context;
    private int REQUEST_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        context = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write);
        binding.setWriteLayout(this);

        List<String> schools = Arrays.asList(getResources().getStringArray(R.array.school));
        List<String> categories = Arrays.asList(getResources().getStringArray(R.array.school_category));


        binding.writerSchool.setAdapter(new CustomSpinnerAdapter(context, schools));
        binding.writerCategory.setAdapter(new CustomSpinnerAdapter(context, categories));

        binding.addTag.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    if(binding.addTag.getText().toString().length() >0) {
                        addTag();
                        return true;
                    }else {

                        Toast.makeText(context, "태그를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });

    }

    public void addTag() {

        String tagName = binding.addTag.getText().toString();

        if(!tagName.startsWith("#")) {
            tagName = "#"  + tagName;
        }

        int marginLeftRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        int marginTomBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        FlexboxLayout layout = binding.tagList;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout tagLayout = new LinearLayout(this);
        layoutParams.rightMargin = marginLeftRight;
        layoutParams.leftMargin = marginLeftRight;
        tagLayout.setLayoutParams(layoutParams);
        tagLayout.setOrientation(LinearLayout.HORIZONTAL);
        tagLayout.setBackgroundResource(R.drawable.textview_radius);

        TextView tag = new TextView(this);  // 새로 추가할 imageView 생성
        LinearLayout.LayoutParams textViewLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textViewLayout.topMargin = marginTomBottom;
        textViewLayout.bottomMargin = marginTomBottom;
        textViewLayout.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());

        tag.setBackgroundColor(Color.TRANSPARENT);
        tag.setLayoutParams(textViewLayout);
        tag.setText(tagName);

        ImageView close = new ImageView(this);
        LinearLayout.LayoutParams imageLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageLayout.topMargin = marginTomBottom;
        imageLayout.bottomMargin = marginTomBottom;
        imageLayout.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        imageLayout.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        close.setLayoutParams(imageLayout);
        close.setImageResource(R.drawable.ic_close);

        tagLayout.addView(tag); // 기존 linearLayout에 imageView 추가
        tagLayout.addView(close);

        layout.addView(tagLayout);

        binding.addTag.setText("");
    }

    public void selectPhoto(View v) {
        Log.i("TEST :", "Helloworld");

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), REQUEST_CODE);
    }

    public void finishActivity(View v) {
        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                InputStream is = null;
                Bitmap bm = null;
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 78, getResources().getDisplayMetrics());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 78, getResources().getDisplayMetrics());
                int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

                FlexboxLayout layout = binding.imageList;
                FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(width,height);
                layoutParams.topMargin = margin;
                layoutParams.bottomMargin= margin;
                layoutParams.leftMargin = margin;
                layoutParams.rightMargin = margin;

                try {
                    if (data.getData() != null) {
                        is = context.getContentResolver().openInputStream(data.getData());
                        bm = BitmapFactory.decodeStream(is);
                        ImageView iv = new ImageView(this);  // 새로 추가할 imageView 생성
                        iv.setScaleType(ImageView.ScaleType.FIT_XY);
                        iv.setImageBitmap(bm);  // imageView에 내용 추가
                        iv.setLayoutParams(layoutParams);  // imageView layout 설정
                        iv.setBackgroundResource(R.drawable.image_radius);
                        iv.setClipToOutline(true);

                        layout.addView(iv); // 기존 linearLayout에 imageView 추가
                    }else {

                        for (int i = 0; i < data.getClipData().getItemCount(); i++) {

                            ImageView iv = new ImageView(this);  // 새로 추가할 imageView 생성
                            iv.setScaleType(ImageView.ScaleType.FIT_XY);

                            is = context.getContentResolver().openInputStream(data.getClipData().getItemAt(i).getUri());
                            bm = BitmapFactory.decodeStream(is);
                            iv.setImageBitmap(bm);  // imageView에 내용 추가
                            iv.setLayoutParams(layoutParams);  // imageView layout 설정
                            iv.setClipToOutline(true);
                            iv.setBackgroundResource(R.drawable.image_radius);
                            layout.addView(iv); // 기존 linearLayout에 imageView 추가
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {

                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }
}