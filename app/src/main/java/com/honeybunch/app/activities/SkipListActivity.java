package com.honeybunch.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;

import com.honeybunch.app.interfaces.Api;

import com.honeybunch.app.models.SkipListModel;
import com.honeybunch.app.models.UserProfileModel;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkipListActivity extends AppCompatActivity {

    SpinKitView loadBar;
    RecyclerView recylerview_refused;
    SkipListAdapter skipListAdapter;
    ConstraintLayout no_refused_list;
    Toolbar toolbar;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refused_list);

        recylerview_refused = findViewById(R.id.recylerview_refused);
        loadBar = findViewById(R.id.spin_kit);
        no_refused_list = findViewById(R.id.no_refused_list);
        toolbar = findViewById(R.id.toolbar);
        backBtn=findViewById(R.id.backbtn_iv);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
      /*  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Skip User list</font>"));
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.unselect_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);*/

        SkipUserList();

    }

    private void SkipUserList() {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<SkipListModel> call = service.skipList(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);

        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<SkipListModel>() {
            @Override
            public void onResponse(Call<SkipListModel> call, final Response<SkipListModel> response) {
                dismissProgressDialog();
                Log.e("TAG", "onResponse:Skipp "+new Gson().toJson(response.body()));

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            List<SkipListModel.SkipList> skipList = response.body().getSkipList();
                            if (skipList.size() > 0) {
                                skipListAdapter = new SkipListAdapter(skipList, SkipListActivity.this,"unskip");
                                recylerview_refused.setLayoutManager(new LinearLayoutManager(SkipListActivity.this));
                                recylerview_refused.setAdapter(skipListAdapter);
                            } else {
                                no_refused_list.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                } else {
                    Toast.makeText(SkipListActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SkipListModel> call, Throwable t) {
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

    public class SkipListAdapter extends RecyclerView.Adapter<SkipListAdapter.ViewHolder> {

        private List<SkipListModel.SkipList> listCard;
        private Context context;
        String unskip;


        public SkipListAdapter(List<SkipListModel.SkipList> listCard, Context context, String unskip) {
            this.listCard = listCard;
            this.context = context;
            this.unskip = unskip;
        }

        @NonNull
        @Override
        public SkipListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_block_list, parent, false);

            return new SkipListAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull SkipListAdapter.ViewHolder holder, int position) {
            holder.tv_block_username.setText(listCard.get(position).getRequesterName() + ", " + listCard.get(position).getRequesterAge());
            holder.tv_block_user_bio.setText(listCard.get(position).getBioText());
            Glide.with(context).load(listCard.get(position).getImage()).into(holder.block_user_image);
            if (listCard.get(position).getStatus().equalsIgnoreCase("Offline")) {
                holder.inactive.setVisibility(View.VISIBLE);
            } else {
                holder.active.setVisibility(View.VISIBLE);
            }
            holder.tv_unblock.setText(unskip);
            holder.tv_unblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(holder.getAdapterPosition());
                }
            });

        }

        public void removeAt(int position) {
            if (listCard.size() > 0) {
                unblockUser(context, listCard.get(position).getMatchId(), "unskip",position);
            } else {
                no_refused_list.setVisibility(View.VISIBLE);
            }
        }

        private void unblockUser(Context context, Integer matchId, String unblock, int position) {
            Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

            Call<UserProfileModel> call = service.updateMatchProfile(Constants.getSharedPreferenceInt(context, "userId", 0),
                    Constants.getSharedPreferenceString(context, "securityToken", ""),
                    BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE, matchId, unblock);


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
                                no_refused_list.setVisibility(View.VISIBLE);
                                skipListAdapter.notifyDataSetChanged();
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