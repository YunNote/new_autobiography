package com.example.study.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.R;
import com.example.study.vo.AddressVO;
import com.example.study.vo.TestAddressVO;

import java.util.List;

public class InviteListAdapter extends RecyclerView.Adapter<InviteListAdapter.ViewHolder> {

    private List<TestAddressVO> addressList = null;
    Context context = null;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public Button inviteButton;
        public ImageView avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            avatar = (ImageView)itemView.findViewById(R.id.avatar);
            tv = (TextView) itemView.findViewById(R.id.address_name);
            inviteButton = (Button) itemView.findViewById(R.id.address_invite_btn);

        }
    }

    public InviteListAdapter(Context context, List<TestAddressVO> addressList) {
        this.addressList = addressList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.invite_recyclerview_item, parent, false) ;
        InviteListAdapter.ViewHolder vh = new InviteListAdapter.ViewHolder(view) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestAddressVO data = addressList.get(position);
        holder.tv.setText(data.getName());
        holder.avatar.setImageResource(context.getResources().getIdentifier(data.getAvatar(),"drawable", context.getPackageName()));
        holder.inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),  v.getContext().getString(R.string.invite_message) , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }
}
