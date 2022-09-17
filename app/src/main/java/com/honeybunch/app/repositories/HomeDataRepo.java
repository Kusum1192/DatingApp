package com.honeybunch.app.repositories;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.home.HomeFragment;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.HomelistProfilesModels;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDataRepo {

    private static final String TAG = "HomeDataRepo";
    private MutableLiveData<HomelistProfilesModels> listMutableLiveData;
    Context context;
    SpinKitView loadBar;
    View supportLayout;


    public LiveData<HomelistProfilesModels> getAllUsersProfile(FragmentActivity mActivity, SpinKitView loadBar, View supportLayout) {
        if (listMutableLiveData == null) {
            context = mActivity;
            this.loadBar = loadBar;
            this.supportLayout = supportLayout;
            listMutableLiveData = new MutableLiveData<>();
        }
        getUsersProfiles(context);

        return listMutableLiveData;

    }
    private void getUsersProfiles(Context context) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        loadBar.setVisibility(View.GONE);
        Call<HomelistProfilesModels> call = service.getHomeDataProfile(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE);

        call.enqueue(new Callback<HomelistProfilesModels>() {
            @Override
            public void onResponse(Call<HomelistProfilesModels> call, final Response<HomelistProfilesModels> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            HomelistProfilesModels homelistProfilesModels = response.body();
                            listMutableLiveData.setValue(homelistProfilesModels);
                            HomeFragment.listCount=homelistProfilesModels.getUserList().size();
                        }
                    }
                } else {
                    //loadBar.setVisibility(View.GONE);
                    Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<HomelistProfilesModels> call, Throwable t) {
                // Log error here since request failed
                loadBar.setVisibility(View.GONE);
                supportLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
