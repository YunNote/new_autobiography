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

import com.example.study.R;
import com.example.study.databinding.FragmentSilverTownDetailBinding;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class SilverTownDetailFragment extends Fragment {

    private static String LOG_NAME = "SilverTownDetailFragment :: ";
    private FragmentSilverTownDetailBinding binding;
    private Context context;
    String[] coordSplit = new String[2];

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
        String coord = bundle.getString("coord");


        ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        container.getRootView().findViewById(R.id.write_btn).setVisibility(View.INVISIBLE);
        container.getRootView().findViewById(R.id.search_btn).setVisibility(View.INVISIBLE);

        binding.silverTownDetailTitle.setText(title);
        binding.silverTownDetailImage.setImageResource(context.getResources().getIdentifier(img, "drawable", context.getPackageName()));
        binding.silverTownDetailDescription.setText(description);
        binding.silverTownDetailAddress.setText(address);
        binding.silverTownDetailTelNumber.setText(telNumber);


        TextView viewById = container.getRootView().findViewById(R.id.toolbar_title);
        viewById.setText(title);
        container.getRootView().findViewById(R.id.drawer_button).setVisibility(View.INVISIBLE);

        coordSplit = coord.split(",");
        double latitude = Double.parseDouble(coordSplit[0]);
        double longitude = Double.parseDouble(coordSplit[1]);

        MapView mapView = new MapView(context);
        ViewGroup mapViewContainer = binding.mapView;
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
        mapView.setZoomLevel(3, true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(title);
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);


        mapViewContainer.addView(mapView);

        return root;
    }



}
