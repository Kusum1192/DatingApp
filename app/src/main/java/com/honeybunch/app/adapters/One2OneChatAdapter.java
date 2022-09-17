package com.honeybunch.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.honeybunch.app.R;
import com.honeybunch.app.models.OnetoOneChatModel;

import java.util.List;

public class One2OneChatAdapter extends RecyclerView.Adapter<One2OneChatAdapter.viewHolder> {

    List<OnetoOneChatModel.MessageArray> arrayList;
    Context context;
    String rUrl;

    public One2OneChatAdapter(List<OnetoOneChatModel.MessageArray> onetoOneChatModelArrayList, String rUrl, Context context) {
        this.arrayList = onetoOneChatModelArrayList;
        this.context = context;
        this.rUrl = rUrl;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_to_one_item, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        if(arrayList.get(position).isBySelf()){
            viewHolder.cardview_sender.setVisibility(View.VISIBLE);
            viewHolder.sendermessage.setText(arrayList.get(position).getText());
            viewHolder.tv_sender_time.setText(arrayList.get(position).getMsgTime());

        }
        else {
            viewHolder.rl_recevier.setVisibility(View.VISIBLE);
            viewHolder.receviermssage.setText(arrayList.get(position).getText());
            viewHolder.tv_recevier_time.setText(arrayList.get(position).getMsgTime());
        }
        Glide.with(context).load(rUrl)
                .into(viewHolder.reciverpic);
    }
    @Override
    public int getItemCount() {
       return arrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView receviermssage;
        TextView sendermessage;
        TextView tv_recevier_time;
        TextView tv_sender_time;
        ImageView reciverpic;
        CardView cardview_sender;
        RelativeLayout rl_recevier;
        public viewHolder(View itemView) {
            super(itemView);
            receviermssage = (TextView) itemView.findViewById(R.id.receviermssage);
            sendermessage = (TextView) itemView.findViewById(R.id.sendermsg);
            tv_recevier_time = (TextView) itemView.findViewById(R.id.tv_recevier_time);
            tv_sender_time = (TextView) itemView.findViewById(R.id.tv_sender_time);
            reciverpic = (ImageView) itemView.findViewById(R.id.reciverpic);
            rl_recevier =  itemView.findViewById(R.id.rl_recevier);
            cardview_sender =  itemView.findViewById(R.id.cardview_sender);
        }
    }
}