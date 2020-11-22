package com.example.study;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintDocumentAdapter;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.study.databinding.ActivityMainBinding;
import com.example.study.fragment.InviteUserFragment;
import com.example.study.fragment.ItemShopFragment;
import com.example.study.fragment.MainFragment;
import com.example.study.fragment.MyPageFragment;
import com.example.study.fragment.SearchFragment;
import com.example.study.fragment.ShopFragment;
import com.example.study.fragment.ShopWebViewFragment;
import com.example.study.fragment.SilverTownFragment;
import com.example.study.vo.AddressVO;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int CONTACTS_REQUEST_CODE = 1000;
    private static String LOG_NAME = "MainActivity :: ";
    private static ActivityMainBinding binding = null;
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainLayout(this);

        // FragmentManager
        fragmentManager = getSupportFragmentManager();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, new MainFragment()).commitAllowingStateLoss();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(false); // 뒤로가기 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.ic_direction_left); //뒤로가기 버튼 이미지 지정

        mDrawerLayout = (DrawerLayout) findViewById(R.id.menu_drawer);


        View view = findViewById(R.id.custom_drawer_layout);
        LinearLayout familyFriend = view.findViewById(R.id.family_friend);
        LinearLayout familyFriendSubMenu = view.findViewById(R.id.family_friend_sub_menu);

        view.findViewById(R.id.side_myprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                transaction = fragmentManager.beginTransaction();
                String fragmentTag = fragmentManager.getClass().getSimpleName();

                getSupportFragmentManager().popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction.addToBackStack(fragmentTag);

                binding.icUser.setImageResource(R.drawable.ic_user);
                binding.iconNavCom.setImageResource(R.drawable.icon_nav_com);
                binding.icTown.setImageResource(R.drawable.ic_town);
                binding.icShopping.setImageResource(R.drawable.ic_shopping);

                findViewById(R.id.drawer_button).setVisibility(View.VISIBLE);
                findViewById(R.id.write_btn).setVisibility(View.VISIBLE);
                findViewById(R.id.search_btn).setVisibility(View.VISIBLE);

                binding.icUser.setImageResource(R.drawable.ic_user_select);
                transaction.replace(R.id.frameLayout, new MyPageFragment()).commitAllowingStateLoss();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        view.findViewById(R.id.drawer_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = getSupportFragmentManager().beginTransaction();
                String fragmentTag = fragmentManager.getClass().getSimpleName();

                getSupportFragmentManager().popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }

                binding.icUser.setImageResource(R.drawable.ic_user);
                binding.iconNavCom.setImageResource(R.drawable.icon_nav_com);
                binding.icTown.setImageResource(R.drawable.ic_town);
                binding.icShopping.setImageResource(R.drawable.ic_shopping);

                transaction.replace(R.id.frameLayout, new MainFragment()).commitAllowingStateLoss();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        view.findViewById(R.id.family_friend_invite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                transaction = getSupportFragmentManager().beginTransaction();
                String fragmentTag = fragmentManager.getClass().getSimpleName();

                getSupportFragmentManager().popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction.addToBackStack(fragmentTag);
                transaction.replace(R.id.frameLayout, new InviteUserFragment()).commitAllowingStateLoss();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        familyFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if("true".equals(familyFriend.getTag())) {
                    familyFriendSubMenu.setVisibility(View.VISIBLE);
                    ImageView arrow = view.findViewById(R.id.family_friend_toogle);
                    arrow.setImageResource(R.drawable.ic_direction_up);
                    familyFriend.setTag("false");
                }else {

                    familyFriendSubMenu.setVisibility(View.GONE);
                    ImageView arrow = view.findViewById(R.id.family_friend_toogle);
                    arrow.setImageResource(R.drawable.ic_direction_down);
                    familyFriend.setTag("true");
                }

            }
        });



        binding.drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                fragmentManager.popBackStack();
                actionBar.setDisplayHomeAsUpEnabled(false);
                binding.drawerButton.setVisibility(View.VISIBLE);
                binding.writeBtn.setVisibility(View.VISIBLE);
                    binding.searchBtn.setVisibility(View.VISIBLE);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CONTACTS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, new InviteUserFragment()).commitAllowingStateLoss();
                }
            }

            return;
        }
    }

    public void writerFragment(View v) {
        Intent intent = new Intent(this, WriteActivity.class);
        startActivity(intent);
    }

    public void searchFragment(View v) {
        transaction = fragmentManager.beginTransaction();
        String fragmentTag = fragmentManager.getClass().getSimpleName();

        getSupportFragmentManager().popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.addToBackStack(fragmentTag);
        transaction.replace(R.id.frameLayout, new SearchFragment()).commitAllowingStateLoss();
    }

    public void changeFragment(View v) {

        int fragmentType = v.getId();
        transaction = fragmentManager.beginTransaction();
        String fragmentTag = fragmentManager.getClass().getSimpleName();

        getSupportFragmentManager().popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.addToBackStack(fragmentTag);

        binding.icUser.setImageResource(R.drawable.ic_user);
        binding.iconNavCom.setImageResource(R.drawable.icon_nav_com);
        binding.icTown.setImageResource(R.drawable.ic_town);
        binding.icShopping.setImageResource(R.drawable.ic_shopping);

        findViewById(R.id.drawer_button).setVisibility(View.VISIBLE);
        findViewById(R.id.write_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.search_btn).setVisibility(View.VISIBLE);

        switch (fragmentType) {
            case R.id.my_page:
                binding.icUser.setImageResource(R.drawable.ic_user_select);
                transaction.replace(R.id.frameLayout, new MyPageFragment()).commitAllowingStateLoss();
                break;
            case R.id.item_shop:
                binding.iconNavCom.setImageResource(R.drawable.icon_nav_com_select);
                transaction.replace(R.id.frameLayout, new ItemShopFragment()).commitAllowingStateLoss();
                break;
            case R.id.silver_town:
                binding.icTown.setImageResource(R.drawable.ic_town_select);
                transaction.replace(R.id.frameLayout, new SilverTownFragment()).commitAllowingStateLoss();
                break;
            case R.id.shop:

                transaction.replace(R.id.frameLayout, new ShopWebViewFragment()).commitAllowingStateLoss();
                //transaction.replace(R.id.frameLayout, new ShopFragment()).commitAllowingStateLoss();
                break;
        }

        fragmentManager.executePendingTransactions();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View currentFocusView = getCurrentFocus();

        if (currentFocusView != null) {
            Rect rect = new Rect();
            currentFocusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
                currentFocusView.clearFocus();
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {


        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Log.i(LOG_NAME , getSupportFragmentManager().getBackStackEntryCount()+ "");
            actionBar.setDisplayHomeAsUpEnabled(false);
            findViewById(R.id.drawer_button).setVisibility(View.VISIBLE);
            findViewById(R.id.write_btn).setVisibility(View.VISIBLE);
            findViewById(R.id.search_btn).setVisibility(View.VISIBLE);
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}