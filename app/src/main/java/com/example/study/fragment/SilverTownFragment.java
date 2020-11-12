package com.example.study.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.LoginActivity;
import com.example.study.R;
import com.example.study.adapter.InviteListAdapter;
import com.example.study.adapter.ItemDecoration;
import com.example.study.adapter.SilverTownListAdapter;
import com.example.study.databinding.FragmentMypageBinding;
import com.example.study.databinding.FragmentSilverTownBinding;
import com.example.study.helper.DataBaseHelper;
import com.example.study.utils.SQLiteUtils;
import com.example.study.vo.SilverTownVO;
import com.google.gson.Gson;

import java.util.List;

public class SilverTownFragment extends Fragment {

    private DataBaseHelper helper;
    private SQLiteUtils sqlite;
    RecyclerView recyclerView;
    private static String LOG_NAME = "SilverTownFragment :: ";
    private FragmentSilverTownBinding binding;
    private SilverTownListAdapter silverTownListAdapter;
    private Context context;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_silver_town, container, false);
        View root = binding.getRoot();


        TextView viewById = container.getRootView().findViewById(R.id.toolbar_title);
        viewById.setText("실버타운");

        ActionBar actionBar = ((AppCompatActivity)context).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        container.getRootView().findViewById(R.id.write_btn).setVisibility(View.INVISIBLE);
        container.getRootView().findViewById(R.id.search_btn).setVisibility(View.INVISIBLE);

        
        // 실버타운 리스트 그리기
        recyclerView = binding.silverTownRecyclerView;
        helper = new DataBaseHelper(context, getString(R.string.app_db), null, 1);
        sqlite = new SQLiteUtils(helper);
        List<SilverTownVO> silverTown = sqlite.getSilverTown();
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        silverTownListAdapter = new SilverTownListAdapter(context, silverTown);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(silverTownListAdapter);

        // 검색 결과 카운트
        binding.searchCount.setText(String.valueOf(silverTownListAdapter.getItemCount()));

        // 검색 레이아웃 부분
        binding.searchOptions.setAdapter(new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.search_options)));

        return root;
    }
}
