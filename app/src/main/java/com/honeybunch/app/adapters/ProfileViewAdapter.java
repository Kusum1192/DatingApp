package com.honeybunch.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.honeybunch.app.R;
import com.honeybunch.app.activities.ViewPagerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewAdapter extends RecyclerView.Adapter<ProfileViewAdapter.viewHolder> {

    List<String> arrayList;
    Context context;


    public ProfileViewAdapter(List<String> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout_profile, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
            Picasso.get().load(arrayList.get(position)).into(viewHolder.image);
            viewHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ViewPagerActivity.class);
                    intent.putStringArrayListExtra("imagesList",(ArrayList<String>)arrayList);
                    context.startActivity(intent);
                }
            });
        }


    @Override
    public int getItemCount() {

       return Math.min(arrayList.size(), 4);
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView image;
        public viewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.images);

        }
    }


}