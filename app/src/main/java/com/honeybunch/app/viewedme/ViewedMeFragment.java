package com.honeybunch.app.viewedme;

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
import com.honeybunch.app.adapters.CustomAdapter;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.ListViewModels;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewedMeFragment extends Fragment {

    private ViewedMeViewModel mViewModel;
    //    private CustomAdapter adapter;
    FragmentActivity mActivity;
    List<ListViewModels.ViewerList> viewerListList;
    RecyclerView recyclerView;
    SpinKitView loadBar;
    ConstraintLayout no_viewedme_list;
    View supportLayout;

    public ViewedMeFragment() {

    }


    public static ViewedMeFragment newInstance() {
        return new ViewedMeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewed_me_fragment, container, false);
        recyclerView = view.findViewById(R.id.recylerview);
        loadBar = view.findViewById(R.id.spin_kit);
        supportLayout = view.findViewById(R.id.supportLayout);
        no_viewedme_list = view.findViewById(R.id.no_viewedme_list);

        listViewdActivity(mActivity);

        return view;
    }

    private void listViewdActivity(Context context) {

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        Call<ListViewModels> call = service.listViewd(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);


        call.enqueue(new Callback<ListViewModels>() {
            @Override
            public void onResponse(Call<ListViewModels> call, final Response<ListViewModels> response) {
                dismissProgressDialog();

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {

                            // Log.e("TAG", "onCreateView: " + profileArrayList.size());
                            viewerListList = response.body().getViewerList();

                            Constants.setSharedPreferenceInt(mActivity, "viewCount", response.body().getCount().getViewCount());
                            if (viewerListList.size() > 0) {
                                recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
                                //adapter = new CustomAdapter(profileArrayList, mActivity,this);
                                CustomAdapter adapter = new CustomAdapter(viewerListList, mActivity, new CustomAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(ListViewModels.ViewerList Offer) {
                                        Intent intent = new Intent(mActivity, ViewedMeProfileActivity.class);
                                        intent.putExtra("viewProfileId", Offer.getRequesterId());
                                        intent.putExtra("matchId", Offer.getMatchId());
                                        intent.putExtra("image", Offer.getImage());
                                        intent.putExtra("name",Offer.getRequesterName());
                                        intent.putExtra("viewProfile","true");
                                        mActivity.startActivity(intent);
                                    }
                                });
                                recyclerView.setAdapter(adapter);
                            } else {
                                no_viewedme_list.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<ListViewModels> call, Throwable t) {
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
        mViewModel = new ViewModelProvider(this).get(ViewedMeViewModel.class);
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