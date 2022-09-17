package com.honeybunch.app.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.honeybunch.app.R;
import com.honeybunch.app.models.DataItem;

import java.util.ArrayList;

public class CustomCityAdapter extends RecyclerView.Adapter<CustomCityAdapter.ViewHolder> {

    Context context;
    ArrayList<DataItem> arrayList;

    public CustomCityAdapter(Context context, ArrayList<DataItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_city_name, parent, false);
        return new CustomCityAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem dItem=arrayList.get(position);
        holder.tv_cityname.setText(dItem.getName()+", "+ dItem.getState());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("city",dItem.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cityname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_cityname = itemView.findViewById(R.id.tv_cityname);

        }
    }

    public void filterList(ArrayList<DataItem> filteredList) {
        arrayList = filteredList;
        notifyDataSetChanged();
    }

}
