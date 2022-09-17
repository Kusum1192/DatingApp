package com.honeybunch.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.InviteAppModel;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteActivity extends AppCompatActivity implements View.OnClickListener {

    SpinKitView loadBar;
    TextView tv_referralCode;
    TextView text_small;
    String invitefburl;
    String inviteTextUrl;
    LinearLayout layout_share_more;
    LinearLayout layout_share_whatsapp;
    LinearLayout layout_share_telegram;
    LinearLayout layout_share_fb;
    ImageView invite_image;
    Toolbar toolbar;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        toolbar = findViewById(R.id.toolbar);
        backBtn=findViewById(R.id.backbtn_iv);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setDisplayShowHomeEnabled(true);

//        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000'>Invite Friends</font>"));
  //      TextView textView = new TextView(this);
    //    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
      //  textView.setGravity(Gravity.CENTER);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.unselect_color), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        loadBar = findViewById(R.id.spin_kit);
        invite_image = findViewById(R.id.invite_image);
        text_small = findViewById(R.id.text_small);
        tv_referralCode = findViewById(R.id.tv_referralCode);

        // recylerview_invite = findViewById(R.id.recylerview_invite);
        layout_share_more = findViewById(R.id.layout_share_more);
        layout_share_more.setOnClickListener(this);
        layout_share_telegram = findViewById(R.id.layout_share_telegram);
        layout_share_telegram.setOnClickListener(this);
        layout_share_whatsapp = findViewById(R.id.layout_share_whatsapp);
        layout_share_whatsapp.setOnClickListener(this);
        layout_share_fb = findViewById(R.id.layout_share_fb);
        layout_share_fb.setOnClickListener(this);
        text_small.setText("Earn upto \u20B9 5000 every day by inviting \n your friends to " + getResources().getString(R.string.app_name));
        getInviteData();
    }

    private void getInviteData() {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<InviteAppModel> call = service.inviteData(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE);

        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<InviteAppModel>() {
            @Override
            public void onResponse(Call<InviteAppModel> call, final Response<InviteAppModel> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {

                            loadBar.setVisibility(View.GONE);

                            invitefburl = response.body().getInviteFbUrl();
                            inviteTextUrl = response.body().getInviteTextUrl();
                            //invitedatalist = response.body().getInviteText();
                            tv_referralCode.setText(response.body().getReferralCode());
                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.placeholder(R.drawable.plceholder);
                            requestOptions.error(R.drawable.plceholder);
                            Glide.with(getApplicationContext())
                                    .setDefaultRequestOptions(requestOptions)
                                    .load(response.body().getInviteImgUrl()).into(invite_image);
                        }
                    }
                } else {
                    Toast.makeText(InviteActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InviteAppModel> call, Throwable t) {
                // Log error here since request failed

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_share_fb:
                shareOnFacebook();
                break;
            case R.id.layout_share_whatsapp:
                shareOnWhatsapp();
                break;
            case R.id.layout_share_more:
                shareAppOther();
                break;
            case R.id.layout_share_telegram:
                shareOnTelegram();
                break;
        }
    }
    public void shareApp() {
        try {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(shareIntent, 0);
            if (!resInfo.isEmpty()) {
                for (ResolveInfo resolveInfo : resInfo) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    Intent targetedShareIntent = new Intent(Intent.ACTION_SEND);
                    targetedShareIntent.setType("text/plain");
                    targetedShareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject to be shared");
                    if (TextUtils.equals(packageName, "com.facebook.katana")) {
                        targetedShareIntent.putExtra(Intent.EXTRA_TEXT, invitefburl);
                    } else {
                        targetedShareIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);
                    }
                    targetedShareIntent.setPackage(packageName);
                    targetedShareIntents.add(targetedShareIntent);
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[targetedShareIntents.size()]));
                startActivity(chooserIntent);
            }
        } catch (Exception e) {
            e.toString();
        }
    }
    public void shareOnWhatsapp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);
        try {
            this.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }
    public void shareOnTelegram() {
        final String appName = "org.telegram.messenger";
        final boolean isAppInstalled = isAppAvailable(getApplicationContext(), appName);
        if (isAppInstalled) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            myIntent.setPackage(appName);
            myIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);
            startActivity(Intent.createChooser(myIntent, "Share with"));
        } else
            {
            Toast.makeText(this, "Telegram not Installed", Toast.LENGTH_SHORT).show();
        }
    }
    public void shareOnFacebook(){
        Intent facebookIntent = new Intent(Intent.ACTION_SEND);
        facebookIntent.setType("text/plain");
        facebookIntent.setPackage("com.facebook.katana");
        facebookIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);
        try {
            this.startActivity(facebookIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Facebook have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareAppOther() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage = "\nLet me recommend you this application\n\n";

            shareIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public static boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}