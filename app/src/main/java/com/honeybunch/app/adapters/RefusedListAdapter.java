package com.honeybunch.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.honeybunch.app.R;
import com.honeybunch.app.models.RefusedUserListModels;

import java.util.List;

public class RefusedListAdapter extends RecyclerView.Adapter<RefusedListAdapter.ViewHolder> {

    private List<RefusedUserListModels.RefuseList> listCard;
    private Context context;

    public RefusedListAdapter(List<RefusedUserListModels.RefuseList> listCard, Context context) {
        this.listCard = listCard;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_block_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_block_username.setText(listCard.get(position).getRequesterName()+", "+listCard.get(position).getRequesterAge());
        holder.tv_block_user_bio.setText(listCard.get(position).getBio());
        Glide.with(context).load(listCard.get(position).getImage()).into(holder.block_user_image);
        if(listCard.get(position).getStatus().equalsIgnoreCase("Offline")){
            holder.inactive.setVisibility(View.VISIBLE);
        }
        else {
            holder.active.setVisibility(View.VISIBLE);
        }
        holder.tv_unblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_block_username, tv_block_user_bio, tv_unblock;
        ImageView active, inactive;
        ImageView block_user_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_block_username = itemView.findViewById(R.id.tv_block_username);
            tv_unblock = itemView.findViewById(R.id.tv_unblock);
            active = itemView.findViewById(R.id.active);
            inactive = itemView.findViewById(R.id.inactive);
            tv_block_user_bio = itemView.findViewById(R.id.tv_block_user_bio);
            block_user_image = itemView.findViewById(R.id.block_user_image);

        }
    }
}