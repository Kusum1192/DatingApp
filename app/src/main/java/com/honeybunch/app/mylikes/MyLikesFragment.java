package com.honeybunch.app.mylikes;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.activities.ViewedMeProfileActivity;
import com.honeybunch.app.adapters.CustomMyLIkesAdapter;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.ListMylikesModels;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLikesFragment extends Fragment {

    private MyLikesViewModel mViewModel;

    FragmentActivity mActivity;
    RecyclerView recyclerView;
    SpinKitView loadBar;
    List<ListMylikesModels.MylikedList> listMylikesModelsArrayList;
    ConstraintLayout no_mylike_list;
     View  supportLayout;

   /* public MyLikesFragment(){
    if (ActivityFragment.Page==3){
        lisMyLikesActivity(mActivity);
    }
    }*/


    public static MyLikesFragment newInstance() {
        return new MyLikesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_likes_fragment, container, false);
        loadBar = view.findViewById(R.id.spin_kit);
        no_mylike_list = view.findViewById(R.id.no_mylike_list);
        recyclerView = view.findViewById(R.id.recylerview);
        supportLayout = view.findViewById(R.id.supportLayout);
            lisMyLikesActivity(mActivity);
        return view;
    }
    private void lisMyLikesActivity(Context context) {

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        Call<ListMylikesModels> call = service.listMyLikes(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE);

        call.enqueue(new Callback<ListMylikesModels>() {
            @Override
            public void onResponse(Call<ListMylikesModels> call, final Response<ListMylikesModels> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            listMylikesModelsArrayList = response.body().getMylikedList();

                            if(listMylikesModelsArrayList.size()>0){
                                recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
                                //adapter = new CustomAdapter(profileArrayList, mActivity,this);
                                CustomMyLIkesAdapter adapter = new CustomMyLIkesAdapter(listMylikesModelsArrayList, mActivity, new CustomMyLIkesAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(ListMylikesModels.MylikedList Offer) {
                                        Intent intent = new Intent(mActivity, ViewedMeProfileActivity.class);
                                        intent.putExtra("viewProfileId",Offer.getRequesterId());
                                        intent.putExtra("matchId", Offer.getMatchId());
                                        intent.putExtra("image", Offer.getImage());
                                        intent.putExtra("viewProfile","true");
                                        mActivity.startActivity(intent);
                                    }
                                });
                                recyclerView.setAdapter(adapter);
                            }
                            else {
                                no_mylike_list.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<ListMylikesModels> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();
                supportLayout.setVisibility(View.VISIBLE);

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
        mViewModel = new ViewModelProvider(this).get(MyLikesViewModel.class);
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