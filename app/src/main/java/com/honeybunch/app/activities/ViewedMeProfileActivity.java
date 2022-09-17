package com.honeybunch.app.activities;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;

import com.honeybunch.app.adapters.MyCustomPagerAdapter;
import com.honeybunch.app.adapters.ProfileViewAdapter;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.BlockUserModels;
import com.google.android.material.tabs.TabLayout;
import com.honeybunch.app.models.StartChatModel;
import com.honeybunch.app.models.UserProfileModel;
import com.honeybunch.app.models.ViewProfileModels;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewedMeProfileActivity extends Activity implements View.OnClickListener {

    MyCustomPagerAdapter myCustomPagerAdapter;

    ImageView im_cross;
    ImageView img_more;
    int viewProfileId;
    int matchId;
    TextView inactive;
    TextView active;

    TextView tv_interest;
    TextView tv_name;
    TextView tv_city;
    TextView purpose_date;
    TextView tell_us;
    TextView tv_all_photo;
    RecyclerView recyclerView;
    View sheetView;
    RoundedBottomSheetDialog mBottomSheetDialog;
    String viewProfile;
    LinearLayout ll_message;
    LinearLayout blockUser;
    LinearLayout reportAbuse;
    LinearLayout cancel;
    SpinKitView loadBar;
    String fullName;
    String age;
    String image;
    String city;
    RelativeLayout rl_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewed_me_profile);

        im_cross = findViewById(R.id.im_cross);
        active = findViewById(R.id.active);
        inactive = findViewById(R.id.inactive);
        //tv_message = findViewById(R.id.tv_message);
        loadBar = findViewById(R.id.spin_kit);
        im_cross.setOnClickListener(this);

       // NestedScrollView layoutBottomSheet = findViewById(R.id.bottom_sheet);

//        BottomSheetBehavior behavior = BottomSheetBehavior.from(layoutBottomSheet);
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        behavior.setPeekHeight(0);

        tv_name = findViewById(R.id.tv_name);
        img_more = findViewById(R.id.img_more);
        rl_profile=findViewById(R.id.rl_profile);
        tv_interest = findViewById(R.id.tv_interest);
        tv_all_photo = findViewById(R.id.tv_all_photo);
        tv_city = findViewById(R.id.tv_city);
        purpose_date = findViewById(R.id.purpose_date);
        tell_us = findViewById(R.id.tell_us);
        mBottomSheetDialog = new RoundedBottomSheetDialog(this);
        img_more.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            viewProfileId = intent.getIntExtra("viewProfileId", 0);
            matchId = intent.getIntExtra("matchId", 0);
            viewProfile = intent.getStringExtra("viewProfile");
           /* Log.e("TAG", "onCreate:viewProfileId " + viewProfileId);
            Log.e("TAG", "onCreate:matchId " + matchId);
            Log.e("TAG", "onCreate:matchIdviewProfile " + viewProfile);*/
            ViewProfile(this, viewProfileId);
        }
            if (viewProfile.equalsIgnoreCase("True")) {
            }
        else {
                fullName=intent.getStringExtra("name");
                city=intent.getStringExtra("location");
                image = intent.getStringExtra("image");
               // Log.e("TAG", "onCreate:Adapteeeee " + image);
                sampleCustomView(image);
            }
//        tv_message.setOnTouchListener(new OnSwipeTouchListener(ViewedMeProfileActivity.this) {
//            @Override
//            public void onSwipeLeft() {
//                super.onSwipeLeft();
//                Toast.makeText(ViewedMeProfileActivity.this, "Swipe Left gesture detected", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onSwipeRight() {
//                super.onSwipeRight();
//                Toast.makeText(ViewedMeProfileActivity.this, "Swipe Right gesture detected", Toast.LENGTH_SHORT).show();
//            }
//        });
        recyclerView = findViewById(R.id.recylerview_profile);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return true;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }
    public void sampleCustomView(String image) {
        sheetView = getLayoutInflater().inflate(R.layout.bottomdialog_accept_reject_layout, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.setCanceledOnTouchOutside(false);

        TextView tv_name = sheetView.findViewById(R.id.tv_name);
        TextView tv_location = sheetView.findViewById(R.id.tv_location);
        TextView accept = sheetView.findViewById(R.id.accept);
        TextView reject = sheetView.findViewById(R.id.reject);
        ImageView image_profile = sheetView.findViewById(R.id.user_profile);
        ImageView img_close = sheetView.findViewById(R.id.img_close);
        tv_name.setText(fullName);
        tv_location.setText(city);
        Glide.with(this).load(image).into(image_profile);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMatchProfileStatus(matchId, "Confirm");
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMatchProfileStatus(matchId, "Reject");
            }
        });

    /*    slideView.setOnSlideCompleteListener(new SlideView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideView slideView) {
                // vibrate the device
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                updateMatchProfileStatus(matchId, "Confirm");
            }
        });

        SlideView slideView_cancel = sheetView.findViewById(R.id.slideView_cancel);
        slideView_cancel.setOnSlideCompleteListener(new SlideView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideView slideView) {
                // vibrate the device
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                updateMatchProfileStatus(matchId, "Reject");


            }
        });*/

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();

            }
        });
    }
    private void updateMatchProfileStatus(int matchId, String confirm) {

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        Call<UserProfileModel> call = service.updateMatchProfile(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, matchId, confirm);


        call.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, final Response<UserProfileModel> response) {

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            Toast.makeText(ViewedMeProfileActivity.this, "Matched" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            mBottomSheetDialog.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(ViewedMeProfileActivity.this, "Reject" + response.errorBody(), Toast.LENGTH_SHORT).show();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_cross:
                finish();
                break;

            case R.id.img_more:
                sampleCustomView1();
                break;
            case R.id.ll_message:
                getConversionId(viewProfileId, age, fullName, image);
                break;
            case R.id.ll_block_user:
                blockUserCustomView();
                break;
            case R.id.ll_reports_abuse:
                reportAbuseCustomView();
                break;
            case R.id.ll_cancel:
                mBottomSheetDialog.dismiss();
                break;
            default:
        }
    }

    public void sampleCustomView1() {
        sheetView = getLayoutInflater().inflate(R.layout.custom_bottomdialog_layout, null);
        ll_message = sheetView.findViewById(R.id.ll_message);
        blockUser = sheetView.findViewById(R.id.ll_block_user);
        reportAbuse = sheetView.findViewById(R.id.ll_reports_abuse);
        cancel = sheetView.findViewById(R.id.ll_cancel);
        ll_message.setOnClickListener(this);
        blockUser.setOnClickListener(this);
        reportAbuse.setOnClickListener(this);

        cancel.setOnClickListener(this);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }

    private void ViewProfile(Context context, int viewProfileId) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        Call<ViewProfileModels> call = service.viewProfile(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, viewProfileId);

        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ViewProfileModels>() {
            @Override
            public void onResponse(Call<ViewProfileModels> call, final Response<ViewProfileModels> response) {
                dismissProgressDialog();
                rl_profile.setVisibility(View.VISIBLE);
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            fullName = response.body().getFullName();
                            age = String.valueOf(response.body().getAge());
                            List<String> profilepicList = response.body().getPictures();
                            Log.e("TAG", "onResponse:IMAGE " + profilepicList.size());
                            tv_name.setText(response.body().getFullName() + ", " + response.body().getAge());
                            tv_city.setText(response.body().getCity());
                            tell_us.setText(response.body().getAbout());
                            //purpose_date.setText(response.body().getPurpose().get(0));
                            if (response.body().getStatus().equals("Offline")) {
                                inactive.setVisibility(View.VISIBLE);
                            } else {
                                active.setVisibility(View.VISIBLE);
                            }
                            String datePurpose = TextUtils.join(", ", response.body().getPurpose());
                            purpose_date.setText(datePurpose);

                            String interests = TextUtils.join(", ", response.body().getInterest());
                            tv_interest.setText(interests);

                            ProfileViewAdapter profileViewAdapter = new ProfileViewAdapter(profilepicList, ViewedMeProfileActivity.this);
                            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
                            staggeredGridLayoutManager.setReverseLayout(false);
                            recyclerView.setLayoutManager(staggeredGridLayoutManager);
                            recyclerView.setAdapter(profileViewAdapter);

                            tv_all_photo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ViewedMeProfileActivity.this, ViewPagerActivity.class);
                                    intent.putStringArrayListExtra("imagesList", (ArrayList<String>) profilepicList);
                                    startActivity(intent);
                                }
                            });
                            ViewPager mImageViewPager = findViewById(R.id.viewpager);
                            TabLayout tabLayout = findViewById(R.id.tabDots);
                            tabLayout.setupWithViewPager(mImageViewPager, true);
                            myCustomPagerAdapter = new MyCustomPagerAdapter(ViewedMeProfileActivity.this, profilepicList);
                            mImageViewPager.setAdapter(myCustomPagerAdapter);
                        }
                    }
                } else {
                    Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<ViewProfileModels> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });
    }
    private void getConversionId(Integer requesterId, String requesterAge, String requesterName, String image) {

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        Call<StartChatModel> call = service.startChat(Constants.getSharedPreferenceInt(ViewedMeProfileActivity.this, "userId", 0),
                Constants.getSharedPreferenceString(ViewedMeProfileActivity.this, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, requesterId);


        call.enqueue(new Callback<StartChatModel>() {
            @Override
            public void onResponse(Call<StartChatModel> call, final Response<StartChatModel> response) {

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {

                            Intent intent_chat = new Intent(ViewedMeProfileActivity.this, ChatActivity.class);
                            intent_chat.putExtra("conversationId", response.body().getConversationId());
                            intent_chat.putExtra("recipientName", requesterName);
                            intent_chat.putExtra("recipientAge", requesterAge);
                            intent_chat.putExtra("recipientImage", image);
                            ViewedMeProfileActivity.this.startActivity(intent_chat);

                        }
                    }
                } else {
                    Toast.makeText(ViewedMeProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<StartChatModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });
    }

    public void blockUserCustomView() {
        sheetView = getLayoutInflater().inflate(R.layout.bottom_block_user_layout, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        TextView block_user = sheetView.findViewById(R.id.block_user);
        TextView cancel = sheetView.findViewById(R.id.cancel);

        block_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blockUser(viewProfileId);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
    }
    public void reportAbuseCustomView() {
        sheetView = getLayoutInflater().inflate(R.layout.bottom_report_abuse_layout, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        TextView tv_report = sheetView.findViewById(R.id.tv_report);
        EditText et_report = sheetView.findViewById(R.id.et_report);
        TextView cancel = sheetView.findViewById(R.id.cancel);

        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(et_report.getText())) {
                    et_report.setError("Tell us Your Reason ");
                    et_report.requestFocus();
                } else {
                    reportUser(viewProfileId, et_report.getText().toString());
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });

    }

    private void blockUser(int blockedId) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<BlockUserModels> call = service.blockUser(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, blockedId);

        loadBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<BlockUserModels>() {
            @Override
            public void onResponse(Call<BlockUserModels> call, final Response<BlockUserModels> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {

                            Toast.makeText(ViewedMeProfileActivity.this, "Block " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            mBottomSheetDialog.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(ViewedMeProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BlockUserModels> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });
    }

    private void reportUser(int reportedId, String reportTxt) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<BlockUserModels> call = service.reportUser(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, reportedId, reportTxt);

        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<BlockUserModels>() {
            @Override
            public void onResponse(Call<BlockUserModels> call, final Response<BlockUserModels> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            Toast.makeText(ViewedMeProfileActivity.this, "Block " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            mBottomSheetDialog.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(ViewedMeProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BlockUserModels> call, Throwable t) {
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


}