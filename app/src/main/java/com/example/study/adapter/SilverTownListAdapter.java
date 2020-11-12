package com.example.study.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.R;
import com.example.study.fragment.MainFragment;
import com.example.study.fragment.SilverTownDetailFragment;
import com.example.study.vo.AddressVO;
import com.example.study.vo.SilverTownVO;

import java.util.List;

public class SilverTownListAdapter extends RecyclerView.Adapter<SilverTownListAdapter.ViewHolder> {

    List<SilverTownVO> silverTownVOList = null;
    Context context = null;

    public SilverTownListAdapter(Context context, List<SilverTownVO> silverTownVOList) {
        this.silverTownVOList = silverTownVOList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnailUrl;
        public TextView title;
        public TextView address;
        public TextView telNumber;
        public Button silverTownDetail;
        public long id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailUrl = itemView.findViewById(R.id.silver_town_thumbnail);
            title = itemView.findViewById(R.id.silver_town_title);
            address = itemView.findViewById(R.id.silver_town_address);
            telNumber = itemView.findViewById(R.id.silver_town_phone);
            silverTownDetail = itemView.findViewById(R.id.silver_town_detail);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.silvertown_recyclerview_item, parent, false);
        SilverTownListAdapter.ViewHolder vh = new SilverTownListAdapter.ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SilverTownVO silverTown = silverTownVOList.get(position);

        holder.title.setText(silverTown.getTitle());
        holder.address.setText(silverTown.getAddress());
        holder.address.setSelected(true);
        holder.telNumber.setText(silverTown.getNumber());


        int pos = silverTown.getThumbnailUrl().lastIndexOf(".");
        String fileName = silverTown.getThumbnailUrl().substring(0, pos);
        holder.thumbnailUrl.setImageResource(context.getResources().getIdentifier(fileName,"drawable", context.getPackageName()));
        holder.silverTownDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                SilverTownDetailFragment silverTownDetailFragment = new SilverTownDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", silverTown.getTitle());
                bundle.putString("img", fileName);
                bundle.putString("address", silverTown.getAddress());
                bundle.putString("telNumber", silverTown.getNumber());
                bundle.putString("description", silverTown.getDescription());
                silverTownDetailFragment.setArguments(bundle);

                transaction.replace(R.id.frameLayout,silverTownDetailFragment ).commitAllowingStateLoss();
                transaction.addToBackStack(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return silverTownVOList.size();
    }


}
