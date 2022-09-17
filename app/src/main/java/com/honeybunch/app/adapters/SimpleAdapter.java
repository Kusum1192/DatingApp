package com.honeybunch.app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.honeybunch.app.R;
import com.honeybunch.app.models.BuyCreditModel;

import java.util.ArrayList;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    ArrayList<BuyCreditModel> buyCreditModelArrayList;
    int selectedPosition = -1;
    Context context;

    public SimpleAdapter(ArrayList<BuyCreditModel> buyCreditModelArrayList, Context context) {
        this.buyCreditModelArrayList = buyCreditModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_count_credit.setText(buyCreditModelArrayList.get(position).getCountCredits());
        holder.tv_msgchat_credit.setText(buyCreditModelArrayList.get(position).getMsgPerChatCredits());
        holder.tv_total_credit.setText("\u20B9" + buyCreditModelArrayList.get(position).getTotalCredits());
        holder.tv_chat_credit.setText("\u20B9" + buyCreditModelArrayList.get(position).getChatCredit());
        if(position == 1){
            holder.tv_popular.setVisibility(View.GONE);
            holder.rl_border.setBackground(context.getResources().getDrawable(R.drawable.bc_card));
            holder.tv_count_credit.setTextColor(Color.parseColor("#B5485D"));
        }
        if (selectedPosition == position) {
            holder.rl_border.setBackground(context.getResources().getDrawable(R.drawable.bc_card));
            holder.tv_count_credit.setTextColor(Color.parseColor("#B5485D"));
        } else {
            holder.rl_border.setBackground(context.getResources().getDrawable(R.drawable.unselect_border));
            holder.tv_count_credit.setTextColor(Color.parseColor("#000000"));
        }

        holder.rl_border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return buyCreditModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_count_credit;
        TextView tv_msgchat_credit;
        TextView tv_total_credit;
        TextView tv_chat_credit;
        TextView tv_popular;
        RelativeLayout rl_border;

        public ViewHolder(final View itemView) {
            super(itemView);
            tv_count_credit = itemView.findViewById(R.id.tv_count_credit);
            tv_msgchat_credit = itemView.findViewById(R.id.tv_msgchat_credit);
            tv_total_credit = itemView.findViewById(R.id.tv_total_credit);
            tv_chat_credit = itemView.findViewById(R.id.tv_chat_credit);
            tv_popular = itemView.findViewById(R.id.tv_popular);
            rl_border = itemView.findViewById(R.id.rl_border);
        }
    }
}