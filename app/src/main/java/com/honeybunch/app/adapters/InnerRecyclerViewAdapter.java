package com.honeybunch.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.honeybunch.app.R;


import java.util.ArrayList;


public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder> {


    ArrayList<String> listSubDataList;
    Context context;
    int selectedPosition=-1;


    public InnerRecyclerViewAdapter( ArrayList<String> listSubDataList, Context context) {
        this.listSubDataList = listSubDataList;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expand_item_view, parent, false);
        InnerRecyclerViewAdapter.ViewHolder vh = new InnerRecyclerViewAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String subDataList=listSubDataList.get(position);
        holder.tv_subcat_name.setText(subDataList);

        if(selectedPosition == position){
//            holder.cardView_child.setBackgroundColor(Color.parseColor("#eeeeef"));
            holder.cardView_child.setBackground(context.getResources().getDrawable(R.drawable.bc_card));
            //holder.tv.setTextColor(Color.parseColor("#ffffff"));
        }
        else {
            holder.cardView_child.setBackgroundColor(Color.parseColor("#f4f5fa"));
            //holder.tv.setTextColor(Color.parseColor("#6200EA"));
        }

        holder.cardView_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_subcat_name;
        LinearLayout cardView_child;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_subcat_name = itemView.findViewById(R.id.tv_subcat_name);
            cardView_child = itemView.findViewById(R.id.cardView_child);
            // cardView_child.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return listSubDataList.size();
    }

}