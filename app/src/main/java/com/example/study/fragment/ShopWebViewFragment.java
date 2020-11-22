package com.example.study.fragment;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.study.MainActivity;
import com.example.study.R;
import com.example.study.databinding.FragmentSearchBinding;
import com.example.study.databinding.FragmentShopWebviewBinding;
import com.example.study.helper.DataBaseHelper;
import com.example.study.utils.SQLiteUtils;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class ShopWebViewFragment extends Fragment {

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private static String LOG_NAME = "ShopWebViewFragment :: ";
    private FragmentShopWebviewBinding binding;
    private Context context;
    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_webview, container, false);
        View fragment = binding.getRoot();


        mWebView = binding.shopWebview;//xml 자바코드 연결
        mWebView.getSettings().setJavaScriptEnabled(true);//자바스크립트 허용
        mWebView.loadUrl("https://seniormall.net");//웹뷰 실행
        mWebView.setWebChromeClient(new WebChromeClient());//웹뷰에 크롬 사용 허용//이 부분이 없으면 크롬에서 alert가 뜨지 않음
        mWebView.setWebViewClient(new WebViewClientClass(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                binding.webviewProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                binding.webviewProgress.setVisibility(View.GONE);
            }
        });//새창열기 없이 웹뷰 내에서 다시 열기//페이지 이동 원활히 하기위해 사용

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //This is the filter
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;


                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        ((MainActivity)getActivity()).onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });

        return fragment;
    }


    private class WebViewClientClass extends WebViewClient {//페이지 이동
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
        }
    }

}
