package com.example.study.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.study.R;
import com.example.study.adapter.SilverTownListAdapter;
import com.example.study.databinding.FragmentSilverTownBinding;
import com.example.study.databinding.FragmentSilverTownDetailBinding;
import com.example.study.helper.DataBaseHelper;
import com.example.study.utils.SQLiteUtils;
import com.example.study.vo.SilverTownVO;
import com.google.gson.Gson;

public class SilverTownDetailFragment extends Fragment {

    private static String LOG_NAME = "SilverTownDetailFragment :: ";
    private FragmentSilverTownDetailBinding binding;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_silver_town_detail, container, false);
        View root = binding.getRoot();

        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String img = bundle.getString("img");
        String address = bundle.getString("address");
        String telNumber = bundle.getString("telNumber");
        String description = bundle.getString("description");




        ActionBar actionBar = ((AppCompatActivity)context).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        container.getRootView().findViewById(R.id.write_btn).setVisibility(View.INVISIBLE);
        container.getRootView().findViewById(R.id.search_btn).setVisibility(View.INVISIBLE);

        binding.silverTownDetailTitle.setText(title);
        binding.silverTownDetailImage.setImageResource(context.getResources().getIdentifier(img,"drawable", context.getPackageName()));
        binding.silverTownDetailDescription.setText(description);
        binding.silverTownDetailAddress.setText(address);
        binding.silverTownDetailTelNumber.setText(telNumber);


        TextView viewById = container.getRootView().findViewById(R.id.toolbar_title);
        viewById.setText(title);
        container.getRootView().findViewById(R.id.drawer_button).setVisibility(View.INVISIBLE);

        return root;
    }
}
