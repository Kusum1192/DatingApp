package com.honeybunch.app.adapters;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.BlockUserListModels;
import com.honeybunch.app.models.UserProfileModel;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockListAdapter extends RecyclerView.Adapter<BlockListAdapter.ViewHolder> implements View.OnClickListener {

    private List<BlockUserListModels.BlockList> listCard;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public BlockListAdapter(List<BlockUserListModels.BlockList> listCard, Context context) {
        this.listCard = listCard;
        this.context = context;

    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_block_list, parent, false);
        v.setOnClickListener(this);
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
                removeAt(holder.getAdapterPosition());
                refreshView(position,listCard.get(position).getMatchId());

            }
        });

    }
    public void removeAt(int position) {
        listCard.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listCard.size());

    }


    public void refreshView(int position, Integer matchId) {
        unblockUser(context,matchId,"unblock");
        notifyItemChanged(position);
    }

    public void updateData(ArrayList<BlockUserListModels.BlockList> viewModels) {
        listCard.clear();
        listCard.addAll(viewModels);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        listCard.remove(position);
        notifyItemRemoved(position);
    }

    private void unblockUser(Context context, Integer matchId, String unblock) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        Call<UserProfileModel> call = service.updateMatchProfile(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, matchId, unblock);


        call.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, final Response<UserProfileModel> response) {

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {

                            Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }
                } else {
                    Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onItemClickListener.onItemClick(v, (BlockUserListModels) v.getTag());
                }
            }, 0);
        }
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
    public interface OnItemClickListener {
        void onItemClick(View view, BlockUserListModels viewModel);

    }
}