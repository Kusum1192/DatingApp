package com.honeybunch.app.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.models.AppOpenModel;
import com.honeybunch.app.models.UserProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.honeybunch.app.MainActivity;
import com.honeybunch.app.R;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.CircleTransform;
import com.honeybunch.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserProfileActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "UserProfileActivity";
    FirebaseAuth mAuth;
    ImageView circleImageView;
    TextView EmailId, fullName, genderAlertTv;
    EditText Number, dob, ReferralCodeED, address;
    List<Address> addresses;
    RadioButton radioButtonMale, radioButtonFemale;
    RadioGroup radioGroup;
    Button SaveButton;
    DatePickerDialog picker;
    String gender, mobileNumber, dateofbirth, userName, gen;
    String locationAdd;

    ProgressDialog progressDialog;


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

      Constants.setSharedPreferenceInt(UserProfileActivity.this, "flag", 1);

        mAuth = FirebaseAuth.getInstance();
        dob = findViewById(R.id.dob);
        radioGroup = findViewById(R.id.gender);
        radioButtonMale = findViewById(R.id.male);
        radioButtonFemale = findViewById(R.id.female);
        SaveButton = findViewById(R.id.SaveButton);
        genderAlertTv = findViewById(R.id.genderAlert);
        fullName = findViewById(R.id.fullName);
        EmailId = findViewById(R.id.email);
        address = findViewById(R.id.location);

        Number = findViewById(R.id.mobileNumber);
        circleImageView = findViewById(R.id.profileImage);
        ReferralCodeED = findViewById(R.id.referCode);


        SaveButton.setOnClickListener(this);

        //getSupportActionBar().setTitle("Profile Details");

        fullName.setText(Constants.getSharedPreferenceString(UserProfileActivity.this, "userName", ""));
        EmailId.setText(Constants.getSharedPreferenceString(UserProfileActivity.this, "userEmail", ""));

        address.setEnabled(false);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.female:
                        radioButtonMale.setChecked(false);
                        gender = "Female";
                        genderAlertTv.setVisibility(View.GONE);
                        break;
                    case R.id.male:
                        radioButtonFemale.setChecked(false);
                        gender = "Male";
                        genderAlertTv.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                dob.setError(null);

                // date picker dialog
                picker = new DatePickerDialog(UserProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                dateofbirth = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year);
                                // Log.e(TAG, "onDateSet: " + dateofbirth);
                                Constants.setSharedPreferenceString(UserProfileActivity.this, "date", dateofbirth);
                                dob.setText(dateofbirth);
                                String d = dob.getText().toString();
                                // Log.e(TAG, "onDateSet: " + d);

                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 568025136000L);
                picker.show();
            }
        });

        dob.setText(Constants.getSharedPreferenceString(UserProfileActivity.this, "date", ""));

        Picasso.get().load(mAuth.getCurrentUser().getPhotoUrl())
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .error(R.drawable.ic_baseline_account_circle_24).transform(new CircleTransform())
                .into((circleImageView));


        ReferralCodeED.setText(Constants.getSharedPreferenceString(UserProfileActivity.this, "utm_medium", ""));
    }

    private void getUserLocation() {
        Log.e(TAG, "getUserLocation: click");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);

        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            try {

                String city = currentLocation(location.getLatitude(), location.getLongitude());
                address.setText(city + "," + addresses.get(0).getCountryName());
                locationAdd = address.getText().toString();
                Log.e(TAG, "getUserLocation: " + locationAdd);
                Log.e(TAG, "getUserLocation:mobileNumber " + mobileNumber);
                Log.e(TAG, "getUserLocation:dateofbirth " + dateofbirth);
                Log.e(TAG, "getUserLocation:gen " + gen);
                Log.e(TAG, "getUserLocation:userName " + userName);
                sendUserDataDetails(mobileNumber, dateofbirth, gen, userName);
                // Log.e(TAG, "getUserLocation:try " + locationAdd);
            } catch (Exception e) {
                e.printStackTrace();

                locationAdd = address.getText().toString();
                if (address.getText().toString().isEmpty()) {
                    String msg = "Your location should not be empty enter your location manually.";
                    address.setEnabled(true);
                    address.requestFocus();
                    showRewardDialog(msg);
                } else {
                    sendUserDataDetails(mobileNumber, dateofbirth, gen, userName);
                }

            }
        }

    }

    private void appOpen() {

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
       /* String versionName = pInfo.versionName;
        int versionCode = pInfo.versionCode;*/
        Api getApiData = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<AppOpenModel> call = getApiData.appOpen(Constants.getSharedPreferenceInt(UserProfileActivity.this, "userId", 0),
                Constants.getSharedPreferenceString(UserProfileActivity.this, "securityToken", ""), BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        call.enqueue(new Callback<AppOpenModel>() {
            @Override
            public void onResponse(Call<AppOpenModel> call, Response<AppOpenModel> response) {

                if (response != null) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getStatus() == 200) {
                            Constants.setSharedPreferenceString(UserProfileActivity.this, "forceUpdatePackage", response.body().getPackAge());
                            Constants.setSharedPreferenceInt(UserProfileActivity.this, "msgCount", response.body().getMsgCount());
                            Constants.setSharedPreferenceInt(UserProfileActivity.this, "activityCount", response.body().getCount().getTotal());
                            Constants.setSharedPreferenceString(UserProfileActivity.this, "userAge", response.body().getUserAge());
                            Constants.setSharedPreferenceInt(UserProfileActivity.this, "likeCount", response.body().getCount().getLikeCount());
                            Constants.setSharedPreferenceInt(UserProfileActivity.this, "matchCount", response.body().getCount().getMatchCount());
                            Constants.setSharedPreferenceInt(UserProfileActivity.this, "viewCount", response.body().getCount().getViewCount());
                            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(UserProfileActivity.this, "System Message: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UserProfileActivity.this, "System Message: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(UserProfileActivity.this, "System Message: " + response.errorBody(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AppOpenModel> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t);
//                Toast.makeText(UserProfileActivity.this, getString(R.string.systemmessage) + t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void sendUserDataDetails(String mobileNumber, String dob, String gen, String userName) {

       /* PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = pInfo.versionName;
        int versionCode = pInfo.versionCode;*/

        Api getApiData = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<UserProfileModel> call = getApiData.addUserProfileData(Constants.getSharedPreferenceInt(UserProfileActivity.this, "userId", 0)
                , Constants.getSharedPreferenceString(UserProfileActivity.this, "securityToken", "")
                , BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, userName, mobileNumber, gen, locationAdd, dob,"aboutme");

        call.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {

                if (response != null) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getStatus() == 200) {
                            appOpen();
//                            Constants.setSharedPreferenceInt(UserProfileActivity.this, "flag", 1);


                        } else {
                            Toast.makeText(UserProfileActivity.this, "System Message: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(UserProfileActivity.this, "System Message: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
            }
        });

    }

    private void invalidateData() {
        mobileNumber = Number.getText().toString();
        userName = fullName.getText().toString();
        gen = gender;
        dateofbirth = dob.getText().toString();
        if (mobileNumber.isEmpty() || mobileNumber.length() < 10) {
            Number.setError("10 digit Mobile Number Required");
            Number.requestFocus();

        } else if (dateofbirth.isEmpty()) {
            dob.setError("Date Of Birth Required");
            dob.requestFocus();
        } else if (!radioButtonFemale.isChecked() && !radioButtonMale.isChecked()) {
            genderAlertTv.setVisibility(View.VISIBLE);
        } else {
            getUserLocation();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) UserProfileActivity.this.getSystemService(Context.LOCATION_SERVICE);
                    assert locationManager != null;
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    try {
                        String city = currentLocation(location.getLatitude(), location.getLongitude());
                        address.setText(city + "," + addresses.get(0).getCountryName());
                        locationAdd = address.getText().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        String msg = "Your location is not found please enter your location manually.";
                        address.setEnabled(true);
                        address.requestFocus();
                        showRewardDialog(msg);
//                    Toast.makeText(UserProfileActivity.this, "Not Found!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                    String msg = "These permissions are mandatory to get your location. You need to allow them.";
                    showRewardDialog(msg);
                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:" + UserProfileActivity.this.getPackageName()));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    UserProfileActivity.this.startActivity(i);                    // User selected the Never Ask Again Option
                } else {
                    String msg = "These permissions are mandatory to get your location. You need to allow them.";
                    showRewardDialog(msg);
                }
            }
        }
    }

    private void showRewardDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setMessage(msg);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        if (!UserProfileActivity.this.isFinishing()) {
            dialog.show();
        }
    }
    private String currentLocation(double lat, double lon) {
        String currentCity = "";
//        Log.e(TAG, "currentLocation:city " + lat);
//        Log.e(TAG, "currentLocation:city " + lon);


        Geocoder geocoder = new Geocoder(UserProfileActivity.this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lon, 10);
            if (addresses.size() > 0) {

                for (Address adr : addresses) {
                    if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                        currentCity = adr.getLocality();
//                        Log.e(TAG, "currentLocation:city " + currentCity);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentCity;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.SaveButton) {
            invalidateData();

        }
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }


    @Override
    public void onPause() {
        super.onPause();
        dismissProgressDialog();
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
}