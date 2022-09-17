package com.honeybunch.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;


import com.honeybunch.app.interfaces.Api;

import com.honeybunch.app.models.RefusedUserListModels;
import com.honeybunch.app.models.UserProfileModel;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefusedListActivity extends AppCompatActivity {

    SpinKitView loadBar;
    RecyclerView recylerview_refused;
    RefusedListAdapter refusedListAdapter;
    ConstraintLayout no_refused_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refused_list);

        recylerview_refused = findViewById(R.id.recylerview_refused);
        loadBar = findViewById(R.id.spin_kit);
        no_refused_list = findViewById(R.id.no_refused_list);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000'>Refused User list</font>"));
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.unselect_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

         RefuesdUserList();

    }

    private void RefuesdUserList() {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<RefusedUserListModels> call = service.refusedList(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);

        loadBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<RefusedUserListModels>() {
            @Override
            public void onResponse(Call<RefusedUserListModels> call, final Response<RefusedUserListModels> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            List<RefusedUserListModels.RefuseList> refuseListList = response.body().getRefuseList();
                            if(refuseListList.size() > 0){
                                refusedListAdapter = new RefusedListAdapter(refuseListList,RefusedListActivity.this,"unrefused");
                                recylerview_refused.setLayoutManager(new LinearLayoutManager(RefusedListActivity.this));
                                recylerview_refused.setAdapter(refusedListAdapter);
                            }
                            else{
                                no_refused_list.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } else {
                    Toast.makeText(RefusedListActivity.this, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RefusedUserListModels> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });
    }

    private void dismissProgressDialog() {
        if (loadBar != null && loadBar.isShown()) {
            loadBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissProgressDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

    @Override
    public void onStop() {
        super.onStop();
        dismissProgressDialog();

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class RefusedListAdapter extends RecyclerView.Adapter<RefusedListAdapter.ViewHolder> {

        private List<RefusedUserListModels.RefuseList> listCard;
        private Context context;
        String refused;

        public RefusedListAdapter(List<RefusedUserListModels.RefuseList> listCard, Context context, String refused) {
            this.listCard = listCard;
            this.context = context;
            this.refused = refused;

        }

        @NonNull
        @Override
        public RefusedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_block_list, parent, false);
            return new RefusedListAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RefusedListAdapter.ViewHolder holder, int position) {
            holder.tv_block_username.setText(listCard.get(position).getRequesterName()+", "+listCard.get(position).getRequesterAge());
            holder.tv_block_user_bio.setText(listCard.get(position).getBio());
            Glide.with(context).load(listCard.get(position).getImage()).into(holder.block_user_image);
            if(listCard.get(position).getStatus().equalsIgnoreCase("Offline")){
                holder.inactive.setVisibility(View.VISIBLE);
            }
            else {
                holder.active.setVisibility(View.VISIBLE);
            }

            holder.tv_unblock.setText(refused);
            holder.tv_unblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(holder.getAdapterPosition());
                }
            });
        }

        public void removeAt(int position) {
            if (listCard.size() > 0) {
                unblockUser(context, listCard.get(position).getMatchId(), "refused",position);
            } else {
                no_refused_list.setVisibility(View.VISIBLE);
            }
        }

        private void unblockUser(Context context, Integer matchId, String refused, int position) {
            Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

            Call<UserProfileModel> call = service.updateMatchProfile(Constants.getSharedPreferenceInt(context, "userId", 0),
                    Constants.getSharedPreferenceString(context, "securityToken", ""),
                    BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, matchId, refused);

            call.enqueue(new Callback<UserProfileModel>() {
                @Override
                public void onResponse(Call<UserProfileModel> call, final Response<UserProfileModel> response) {

                    if (response != null) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {

                                Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                listCard.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, listCard.size());
                               // no_refused_list.setVisibility(View.VISIBLE);
                                refusedListAdapter.notifyDataSetChanged();
                                if(listCard.size() > 0){

                                }else {
                                    finish();
                                }

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
}