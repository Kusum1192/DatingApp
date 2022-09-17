package com.honeybunch.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.honeybunch.app.activities.GetCreditActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.honeybunch.app.activities.InviteActivity;
import com.honeybunch.app.home.HomeFragment;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.AppCloseModel;

import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements UpdateBadgeCount, HomeFragment.Callbacks {

    private static int RESULT_LOAD_CHAT = 1006;
    View notificationBadge;
    TextView textView_msgCount;
    View notification_activity;
    TextView textView_activityCount;
    BottomNavigationView navView;
    BottomNavigationMenuView menuView;
    BottomNavigationItemView itemView;
    BottomNavigationItemView itemViewActivityCount;

    MenuItem share,credit;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorOfStatusBar));
        }else {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }
        Constants.setSharedPreferenceInt(MainActivity.this, "flag", 0);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
      //setLogo(R.drawable.hbunch_logo);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        menuView = (BottomNavigationMenuView) navView.getChildAt(0);
        itemView = (BottomNavigationItemView) menuView.getChildAt(1);
        itemViewActivityCount = (BottomNavigationItemView) menuView.getChildAt(2);

        notificationBadge = LayoutInflater.from(this).inflate(R.layout.notification_badge, menuView, false);
        textView_msgCount = notificationBadge.findViewById(R.id.counter_badge);
        if (Constants.getSharedPreferenceInt(MainActivity.this, "msgCount", 0) > 0) {
            textView_msgCount.setText("" + Constants.getSharedPreferenceInt(MainActivity.this, "msgCount", 0));
            itemView.addView(notificationBadge);
        } else {
            textView_msgCount.setVisibility(View.GONE);
        }
        notification_activity = LayoutInflater.from(this).inflate(R.layout.notification_badge, menuView, false);
        textView_activityCount = notification_activity.findViewById(R.id.counter_badge);
        if (Constants.getSharedPreferenceInt(MainActivity.this, "activityCount", 0) > 0) {
            textView_activityCount.setText("" + Constants.getSharedPreferenceInt(MainActivity.this, "activityCount", 0));
            itemViewActivityCount.addView(notification_activity);
        } else {
            textView_activityCount.setVisibility(View.GONE);
        }
//       throw new RuntimeException("Test Crash"); // Force a crash
    }
   /* public void setTitle(String title) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = new TextView(this);
        textView.setText(title.trim());
        textView.setTextSize(20);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.select_color));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }
*/
    /*public void setLogo(int logo){
        getSupportActionBar().setLogo(logo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }*/
    @Override
    public void updateBadgeCount(int totalActivityCount) {
        Constants.setSharedPreferenceInt(MainActivity.this, "activityCount", totalActivityCount);
        if (Constants.getSharedPreferenceInt(MainActivity.this, "activityCount", 0) > 0) {
            textView_activityCount.setText("" + Constants.getSharedPreferenceInt(MainActivity.this, "activityCount", 0));
        } else {
            textView_activityCount.setVisibility(View.GONE);
        }
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.main_menu, menu);
        credit=menu.findItem(R.id.new_credit);
        share=menu.findItem(R.id.new_share);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_credit:
                Intent intent_credit = new Intent(MainActivity.this, GetCreditActivity.class);
                startActivity(intent_credit);
                break;
            case R.id.new_share:
                  Intent intent_share = new Intent(MainActivity.this, InviteActivity.class);
                  startActivity(intent_share);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appClose();
    }

    private void appClose() {

       /* PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/
        String versionName = BuildConfig.VERSION_NAME;
        int versionCode = BuildConfig.VERSION_CODE;
        Api getApiData = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        Call<AppCloseModel> call = getApiData.appClose(Constants.getSharedPreferenceInt(MainActivity.this, "userId", 0),
                Constants.getSharedPreferenceString(MainActivity.this, "securityToken", ""), versionName, versionCode);
        call.enqueue(new Callback<AppCloseModel>() {
            @Override
            public void onResponse(Call<AppCloseModel> call, Response<AppCloseModel> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getStatus() == 200) {
                            Log.e("TAG", "onResponse:Close " + response.body().getMessage());

                        } else {
                            Toast.makeText(MainActivity.this, "System Message: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "System Message: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, "System Message: " + response.errorBody(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<AppCloseModel> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t);

            }
        });
    }

    @Override
    public void onButtonClicked(Context context) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(new HomeFragment()).attach(new HomeFragment()).commit();

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.detach(new HomeFragment()).attach().commit();
    }
}