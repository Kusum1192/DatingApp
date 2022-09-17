package com.honeybunch.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.honeybunch.app.R;
import com.honeybunch.app.activities.ViewedMeProfileActivity;
import com.honeybunch.app.models.HomelistProfilesModels;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class CardStackAdapterTest extends RecyclerView.Adapter<CardStackAdapterTest.ViewHolder> {

    private ArrayList<HomelistProfilesModels.UserList> listCard;
    private Context context;

    public CardStackAdapterTest(ArrayList<HomelistProfilesModels.UserList> listCard, Context context) {
        this.listCard = listCard;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_spot, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomelistProfilesModels.UserList itemList=listCard.get(position);
        holder.username.setText(itemList.getName()+", "+itemList.getAge());
        holder.cityName.setText(itemList.getCity());
        Glide.with(context).load(itemList.getImageUrl()).into(holder.item_image);
        if(itemList.getStatus().equalsIgnoreCase("Offline")){
            holder.inactive.setVisibility(View.VISIBLE);
        }
        else {
            holder.active.setVisibility(View.VISIBLE);
        }
        holder.cardView_match_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_profile = new Intent(context, ViewedMeProfileActivity.class);
                intent_profile.putExtra("viewProfileId",itemList.getId());
                intent_profile.putExtra("viewProfile","true");
                intent_profile.putExtra("image",itemList.getImageUrl());
                intent_profile.putExtra("name",itemList.getName());
                intent_profile.putExtra("location",itemList.getCity());
                context.startActivity(intent_profile);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, cityName;
        TextView active, inactive;
        RoundedImageView item_image;
        CardView cardView_match_profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView_match_profile = itemView.findViewById(R.id.cardView_match_profile);
            username = itemView.findViewById(R.id.item_name);
            active = itemView.findViewById(R.id.active);
            inactive = itemView.findViewById(R.id.inactive);
            cityName = itemView.findViewById(R.id.item_city);
            item_image = itemView.findViewById(R.id.item_image);
        }
    }
}