package com.honeybunch.app.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.models.AppOpenModel;
import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.MainActivity;
import com.honeybunch.app.R;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    SpinKitView loadBar;
    TextView tv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loadBar = findViewById(R.id.spin_kit);
        tv_version = findViewById(R.id.tv_version);
        tv_version.setText("v "+ BuildConfig.VERSION_NAME);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (Constants.getSharedPreferenceInt(SplashActivity.this, "userId", 0) > 0 &&
                                !Constants.getSharedPreferenceString(SplashActivity.this, "securityToken", "").isEmpty()) {
                            openApp();

                        } else {
                            Intent intent = new Intent(SplashActivity.this, UserLoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } }, SPLASH_TIME_OUT);

    }

    private void openApp() {
        if (isNetworkAvailable(SplashActivity.this)) {
            Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
            Call<AppOpenModel> call = service.appOpen(Constants.getSharedPreferenceInt(SplashActivity.this, "userId", 0),
                    Constants.getSharedPreferenceString(SplashActivity.this, "securityToken", ""), BuildConfig.VERSION_NAME,
                    BuildConfig.VERSION_CODE);

      /*  if (!((Activity) SplashActivity.this).isFinishing()) {
           loadBar.setVisibility(View.VISIBLE);
        }*/

        call.enqueue(new Callback<AppOpenModel>() {
            @Override
            public void onResponse(Call<AppOpenModel> call, Response<AppOpenModel> response) {
                dismissProgressDialog();
                try {
                    if (response != null) {
                        if (response.isSuccessful()) {
                            if (response.body().getForceUpdate()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                                builder.setMessage("Your " + getString(R.string.app_name) + " seems very old, Please update to get new Earning features!!");
                                builder.setPositiveButton("UPDATE NOW",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                dialog.dismiss();

                                                try {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Constants.getSharedPreferenceString(SplashActivity.this, "forceUpdatePackage", ""))));
                                                } catch (ActivityNotFoundException e) {
                                                    // TODO: handle exception
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Constants.getSharedPreferenceString(SplashActivity.this, "forceUpdatePackage", ""))));

                                                }
                                                finish();
                                            }
                                        });
                                builder.setCancelable(false);
                                builder.show();

                            } else {
                                if (response.body().getStatus() == 200) {
                                    String packAge = response.body().getPackAge();
                                    Constants.setSharedPreferenceString(SplashActivity.this, "forceUpdatePackage", packAge);

                                    Constants.setSharedPreferenceString(SplashActivity.this, "userPic", response.body().getImageUrl());
                                    Constants.setSharedPreferenceString(SplashActivity.this, "userName", response.body().getUserName());
                                    Constants.setSharedPreferenceString(SplashActivity.this, "userAge", response.body().getUserAge());
                                    Constants.setSharedPreferenceInt(SplashActivity.this, "msgCount", response.body().getMsgCount());
                                    Constants.setSharedPreferenceInt(SplashActivity.this, "activityCount", response.body().getCount().getTotal());
                                    Constants.setSharedPreferenceInt(SplashActivity.this, "likeCount", response.body().getCount().getLikeCount());
                                    Constants.setSharedPreferenceInt(SplashActivity.this, "matchCount", response.body().getCount().getMatchCount());
                                    Constants.setSharedPreferenceInt(SplashActivity.this, "viewCount", response.body().getCount().getViewCount());

                                    if(Constants.getSharedPreferenceInt(SplashActivity.this,"flag",0)==1){
                                        Intent intent = new Intent(SplashActivity.this, UserProfileUpdateFirstActivity.class);
                                        startActivity(intent);
                                        finish();
                                      }
                                    else if(Constants.getSharedPreferenceInt(SplashActivity.this,"flag",0)==2){
                                        Intent intent = new Intent(SplashActivity.this, UserProfileUpdateSecondActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if(Constants.getSharedPreferenceInt(SplashActivity.this,"flag",0)==3){
//                                        Constants.setSharedPreferenceBoolean(SplashActivity.this, "allowPermission", true);
                                        Intent intent = new Intent(SplashActivity.this, UploadProfileActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(SplashActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } else
                        Toast.makeText(SplashActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<AppOpenModel> call, Throwable t) {
                Toast.makeText(SplashActivity.this, getString(R.string.systemmessage) + t, Toast.LENGTH_SHORT).show();
            }
        });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.MyAlertDialogStyle));
            builder.setTitle("Alert!");
            builder.setMessage("Please Check Your Internet Connection.");
            builder.setPositiveButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            builder.setCancelable(false);
            builder.show();
        }
    }

    private void dismissProgressDialog() {
        if (loadBar != null && loadBar.isShown()) {
            loadBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();

    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}