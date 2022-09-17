package com.honeybunch.app.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;

import com.honeybunch.app.activities.SkipListActivity;
import com.honeybunch.app.adapters.CardStackAdapterTest;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.HomelistProfilesModels;
import com.honeybunch.app.models.ReloadDataModels;
import com.honeybunch.app.models.RightSwipeModels;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {

    FragmentActivity mActivity;
    CardStackLayoutManager manager;
    CardStackView cardStackView;
    CardStackAdapterTest adapter;
    FloatingActionButton skipButton;
    FloatingActionButton like_button;
    FloatingActionButton rewind_data;
    SpinKitView loadBar;
    HomeViewModel homeViewModel;
    int commonPosition;
    int homeDatalist;
    RelativeLayout button_container;
    RelativeLayout rl_warning_image;
    View supportLayout;
    public static int listCount;

    private Callbacks mCallbacks;
    private ArrayList<HomelistProfilesModels.UserList> list;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            mActivity = (FragmentActivity) context;
            mCallbacks = (Callbacks) mActivity;
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        loadBar = root.findViewById(R.id.spin_kit);
        listCount=0;
        button_container = root.findViewById(R.id.button_container);
        cardStackView = root.findViewById(R.id.card_stack_view);
        supportLayout = root.findViewById(R.id.supportLayout);
        skipButton = root.findViewById(R.id.skip_button);
        rewind_data = root.findViewById(R.id.rewind_data);
        cardStackView.setOnClickListener(this);
        like_button = root.findViewById(R.id.like_button);
        skipButton.setOnClickListener(this);
        rewind_data.setOnClickListener(this);
        like_button.setOnClickListener(this);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.setAllUserProfiles(mActivity, loadBar, supportLayout).observe(mActivity, new Observer<HomelistProfilesModels>() {
            @Override
            public void onChanged(HomelistProfilesModels homelistProfilesModels) {
                loadBar.setVisibility(View.GONE);
               homeDatalist = homelistProfilesModels.getUserList().size();
              //  Log.e("TAG", "onChanged:size " + homeDatalist);
                if (homelistProfilesModels.getMessage().equals("Success")) {
                    button_container.setVisibility(View.VISIBLE);
                } else {
                    button_container.setVisibility(View.GONE);
                }
                        list=homelistProfilesModels.getUserList();

                adapter = new CardStackAdapterTest(list, mActivity);
                cardStackView.setAdapter(adapter);
                manager = new CardStackLayoutManager(mActivity, new CardStackListener() {
                    @Override
                    public void onCardDragging(Direction direction, float ratio) {
                    }
                    @Override
                    public void onCardSwiped(Direction direction) {

                        String commonDirection = String.valueOf(direction);
                      //  if (list.size() <= 5) {
                            if (commonDirection.equals("Left")) {
                                getswipeLeft(mActivity, list.get(commonPosition).getId());
                            }
                            if (commonDirection.equals("Right")) {
                                getSwipeRight(mActivity, list.get(commonPosition).getId());
                            }
                            listCount--;
                        if(listCount==5){
                            ReloadData(mActivity,adapter);
                            Toast.makeText(mActivity, "Reload image", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("TAG", "onCardSwiped: "+listCount);
                        }
                          //  list.remove(commonPosition);
                    //}
                    @Override
                    public void onCardRewound() {

                    }
                    @Override
                    public void onCardCanceled() {

                    }
                    @Override
                    public void onCardAppeared(View view, int position) {
                        commonPosition = position;
                    }
                    @Override
                    public void onCardDisappeared(View view, int position) {
                        commonPosition = position;
                    }


                });
                cardStackView.setLayoutManager(manager);
                manager.setStackFrom(StackFrom.None);
                manager.setVisibleCount(3);
                manager.setTranslationInterval(8.0f);
                manager.setScaleInterval(0.95f);
                manager.setSwipeThreshold(0.3f);
                manager.setMaxDegree(20.0f);
                manager.setDirections(Direction.HORIZONTAL);
                manager.setCanScrollHorizontal(true);
                manager.setCanScrollVertical(false);
            }
        });
        return root;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip_button:
                if (homeDatalist <= 1) {
                    Toast.makeText(mActivity, "can't swipe left.", Toast.LENGTH_SHORT).show();
                } else {
                    SwipeAnimationSetting settingLeft = new SwipeAnimationSetting.Builder()
                            .setDirection(Direction.Left)
                            .setDuration(Duration.Normal.duration)
                            .setInterpolator(new AccelerateInterpolator())
                            .build();
                    manager.setSwipeAnimationSetting(settingLeft);
                    cardStackView.swipe();
                }
                break;
            case R.id.like_button:
                if (homeDatalist <= 1) {
                    Toast.makeText(mActivity, "can't swipe right.", Toast.LENGTH_SHORT).show();
                } else {
                    SwipeAnimationSetting settingRight = new SwipeAnimationSetting.Builder()
                            .setDirection(Direction.Right)
                            .setDuration(Duration.Normal.duration)
                            .setInterpolator(new AccelerateInterpolator())
                            .build();
                    manager.setSwipeAnimationSetting(settingRight);
                    cardStackView.swipe();
                }
                break;
            case R.id.rewind_data:
                if (homeDatalist != 0) {
                    ReloadData(mActivity,null);
                   // Toast.makeText(mActivity, "This features is coming soon", Toast.LENGTH_SHORT).show();
                } else {
                    ShowDialogBox();
                    Toast.makeText(mActivity, "can't rewind still there is list exits.", Toast.LENGTH_SHORT).show();
                }
//                    Intent intent_chat = new Intent(mActivity, ChatActivity.class);
//                    startActivity(intent_chat);
                break;
            default:
        }
    }
    private void ShowDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mActivity, R.style.MyAlertDialogStyle));
        builder.setTitle("Refresh List");
        builder.setMessage("You have Reached in the end to, restart please remove users from skip list");
        builder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                        //finish();
                        Intent intent_skiplist = new Intent(mActivity, SkipListActivity.class);
                        startActivityForResult(intent_skiplist,1001);
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    private void ReloadData(Context context, CardStackAdapterTest adapter) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<ReloadDataModels> call = service.getHomeDataReload(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE);

        call.enqueue(new Callback<ReloadDataModels>() {
            @Override
            public void onResponse(Call<ReloadDataModels> call, final Response<ReloadDataModels> response) {

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            //Toast.makeText(mActivity, "success chage", Toast.LENGTH_SHORT).show();
                            mCallbacks.onButtonClicked(mActivity);
                            if (adapter!=null) {
                                adapter.notifyDataSetChanged();
                                //Log.d("TAG", "onResponse: " + response);
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ReloadDataModels> call, Throwable t) {
                // Log error here since request failed
                //Log.e("response", t.toString());
            }
        });
    }

    private void getSwipeRight(Context context, int matchid) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<RightSwipeModels> call = service.rightswipe(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE, matchid);

        call.enqueue(new Callback<RightSwipeModels>() {
            @Override
            public void onResponse(Call<RightSwipeModels> call, final Response<RightSwipeModels> response) {

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                          //  Log.e("TAG", "onResponse:right "+response.body() );
                        }
                    }
                } else {
                    Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RightSwipeModels> call, Throwable t) {
                // Log error here since request failed
              //  Log.e("response", t.toString());
            }
        });
    }
    private void getswipeLeft(Context context, int matchid) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<RightSwipeModels> call = service.leftswipe(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, matchid);

        call.enqueue(new Callback<RightSwipeModels>() {
            @Override
            public void onResponse(Call<RightSwipeModels> call, final Response<RightSwipeModels> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                          //  Log.e("TAG", "onResponse:left "+response.body() );
                        }
                    }
                } else {
                    cardStackView.setVisibility(View.GONE);
                    Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RightSwipeModels> call, Throwable t) {
                // Log error here since request failed
               // Log.e("response", t.toString());
            }
        });
    }
    public interface Callbacks {
        //Callback for when button clicked.
        public void onButtonClicked(Context context);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }
}