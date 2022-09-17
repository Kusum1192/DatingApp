package com.honeybunch.app.matches;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.activities.ViewedMeProfileActivity;
import com.honeybunch.app.adapters.CustomMatchAdapter;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.ListMatchModels;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchesFragment extends Fragment {

    private MatchesViewModel mViewModel;
    SpinKitView loadBar;

    FragmentActivity mActivity;
   ConstraintLayout no_match_list;
    RecyclerView recyclerView;
    List<ListMatchModels.MatchList>listMatchModelsList;
    View supportLayout;

    public static MatchesFragment newInstance() {
        return new MatchesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.matches_fragment, container, false);
        loadBar = view.findViewById(R.id.spin_kit);
        no_match_list = view.findViewById(R.id.no_match_list);
        recyclerView = view.findViewById(R.id.recylerview);
        supportLayout = view.findViewById(R.id.supportLayout);
            listMatchActivity(mActivity);

        return view;
    }

    private void listMatchActivity(Context context) {

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        Call<ListMatchModels> call = service.listMatch(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);


        call.enqueue(new Callback<ListMatchModels>() {
            @Override
            public void onResponse(Call<ListMatchModels> call, final Response<ListMatchModels> response) {
                dismissProgressDialog();

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            listMatchModelsList = response.body().getMatchList();
                            Constants.setSharedPreferenceInt(mActivity, "matchCount", response.body().getCount().getMatchCount());
                            if(listMatchModelsList.size() > 0){
                                recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
                                //adapter = new CustomAdapter(profileArrayList, mActivity,this);
                                CustomMatchAdapter adapter = new CustomMatchAdapter(listMatchModelsList, mActivity, new CustomMatchAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(ListMatchModels.MatchList Offer) {

                                        Intent intent = new Intent(mActivity, ViewedMeProfileActivity.class);
                                        intent.putExtra("viewProfileId",Offer.getRequesterId());
                                        intent.putExtra("matchId", Offer.getMatchId());
                                        intent.putExtra("image", Offer.getImage());
                                        intent.putExtra("name", Offer.getRequesterName());
                                        intent.putExtra("viewProfile","true");
                                        mActivity.startActivity(intent);

                                    }
                                });
                                recyclerView.setAdapter(adapter);
                            }
                            else {
                                no_match_list.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } else {
                    dismissProgressDialog();
                    supportLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ListMatchModels> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();
                supportLayout.setVisibility(View.VISIBLE);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MatchesViewModel.class);
        // TODO: Use the ViewModel
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            mActivity = (FragmentActivity) context;
        }
    }

}