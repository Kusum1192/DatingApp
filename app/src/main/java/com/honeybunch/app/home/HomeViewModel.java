package com.honeybunch.app.home;

import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.models.HomelistProfilesModels;
import com.honeybunch.app.repositories.HomeDataRepo;

public class HomeViewModel extends ViewModel {

    HomeDataRepo homeDataRepo = new HomeDataRepo();

    public LiveData<HomelistProfilesModels> setAllUserProfiles(FragmentActivity mActivity, SpinKitView loadBar, View supportLayout) {
        return homeDataRepo.getAllUsersProfile(mActivity,loadBar,supportLayout);
    }
}