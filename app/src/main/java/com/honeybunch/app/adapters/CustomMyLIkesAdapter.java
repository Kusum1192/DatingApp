package com.honeybunch.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.activities.ChatActivity;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.ListMylikesModels;
import com.honeybunch.app.models.StartChatModel;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomMyLIkesAdapter extends RecyclerView.Adapter<CustomMyLIkesAdapter.viewHolder> {

    List<ListMylikesModels.MylikedList> arrayList;
    Context context;
    OnItemClickListener listener;

    public CustomMyLIkesAdapter(List<ListMylikesModels.MylikedList> arrayList, Context context, OnItemClickListener listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_me_item_list, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        ListMylikesModels.MylikedList alist=arrayList.get(position);
        viewHolder.name.setText(alist.getRequesterName() + ", " + alist.getRequesterAge());

        Glide.with(context).load(alist.getImage())
                .into(viewHolder.image);
        if(alist.getStatus().equalsIgnoreCase("Offline")){
            viewHolder.im_inactive.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.im_active.setVisibility(View.VISIBLE);
        }
        viewHolder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getConversionId(alist.getRequesterId(), alist.getRequesterAge(),
                       alist.getRequesterName(), alist.getImage());
            }
        });
        viewHolder.bind(alist, listener);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        ImageView im_active;
        ImageView im_inactive;
        TextView message;

        public viewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.username);
            image = (ImageView) itemView.findViewById(R.id.image);
            message =  itemView.findViewById(R.id.message);
            im_active = (ImageView) itemView.findViewById(R.id.im_active);
            im_inactive = (ImageView) itemView.findViewById(R.id.im_inactive);
        }


        public void bind(final ListMylikesModels.MylikedList item, final CustomMyLIkesAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ListMylikesModels.MylikedList profile);
    }

    private void getConversionId(Integer requesterId, String requesterAge, String requesterName, String image) {

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        Call<StartChatModel> call = service.startChat(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE,requesterId);


        call.enqueue(new Callback<StartChatModel>() {
            @Override
            public void onResponse(Call<StartChatModel> call, final Response<StartChatModel> response) {

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            Intent intent_chat = new Intent(context, ChatActivity.class);
                            intent_chat.putExtra("conversationId",response.body().getConversationId());
                            intent_chat.putExtra("recipientName",requesterName);
                            intent_chat.putExtra("recipientAge",requesterAge);
                            intent_chat.putExtra("recipientImage",image);
                            context.startActivity(intent_chat);

                        }
                    }
                } else {
                    Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<StartChatModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });
    }

}