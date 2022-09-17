package com.honeybunch.app.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.honeybunch.app.R;
import com.honeybunch.app.models.MessageChatModels;

import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter<MessageChatAdapter.viewHolder> {

    List<MessageChatModels.ConversationArray> arrayList;
    Context context;
    OnItemClickListener listener;


    public MessageChatAdapter(List<MessageChatModels.ConversationArray> arrayList, Context context, OnItemClickListener listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_view_item, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        viewHolder.senderName.setText(arrayList.get(position).getChatUserName()+", "+arrayList.get(position).getRecipientAge());

       // viewHolder.msgCount.setText(arrayList.get(position).get());
        Glide.with(context).load(arrayList.get(position).getImageUrl())
                .into(viewHolder.senderpic);
        //viewHolder.image.setImageResource(Integer.parseInt(arrayList.get(position).getImageUrl()));
        viewHolder.bind(arrayList.get(position), listener);
        if(arrayList.get(position).getLastRead()){
            viewHolder.senderMessage.setTypeface(null, Typeface.BOLD);
            viewHolder.senderMessage.setText(arrayList.get(position).getMessage());
            viewHolder.im_inactive_msg.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.senderMessage.setText(arrayList.get(position).getMessage());
            viewHolder.senderMessage.setTypeface(null, Typeface.NORMAL);
            viewHolder.im_inactive_msg.setVisibility(View.GONE);
        }
        if(arrayList.get(position).getStatus().equalsIgnoreCase("Offline")){
            viewHolder.im_inactive.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.im_active.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView senderName;
        TextView senderMessage;
        ImageView im_inactive_msg;
        ImageView senderpic;
        ImageView im_active;
        ImageView im_inactive;

        public viewHolder(View itemView) {
            super(itemView);
            senderName = (TextView) itemView.findViewById(R.id.sendername);
            senderMessage = (TextView) itemView.findViewById(R.id.sendermessage);
            im_inactive_msg = itemView.findViewById(R.id.im_inactive_msg);
            senderpic = (ImageView) itemView.findViewById(R.id.senderpic);
            im_inactive = (ImageView) itemView.findViewById(R.id.im_inactive);
            im_active = (ImageView) itemView.findViewById(R.id.im_active);

        }

        public void bind(final MessageChatModels.ConversationArray item, final MessageChatAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item,getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MessageChatModels.ConversationArray profile, int adapterPosition);
    }
}