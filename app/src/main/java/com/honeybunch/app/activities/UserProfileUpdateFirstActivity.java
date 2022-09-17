package com.honeybunch.app.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.UserProfileModel;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileUpdateFirstActivity extends Activity implements View.OnClickListener {

    TextView tv_female;
    TextView tv_male;
    TextView tv_submit_profile;
    TextView tv_select_city;
    TextView genderAlertTv;
    ProgressBar complete_profile;
    static int PROFILE_UPDATE_PURPOSE_LOCATION = 1001;
    EditText dob;
    EditText et_full_name;
    EditText et_mobile_number;
    EditText et_aboutme;
    DatePickerDialog picker;
    String dateofbirth;
    String mobileNumber;
    String fullname;
    String aboutme;

    String gender;
    RadioButton radioButtonMale, radioButtonFemale;
    RadioGroup radioGroup;
    String gen;
    SpinKitView loadBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update_first);

        Constants.setSharedPreferenceInt(UserProfileUpdateFirstActivity.this, "flag", 1);

        loadBar = findViewById(R.id.spin_kit);
        complete_profile = findViewById(R.id.complete_profile);
        tv_select_city = findViewById(R.id.tv_select_city);
        tv_male = findViewById(R.id.tv_male);
        tv_female = findViewById(R.id.tv_female);
        et_aboutme = findViewById(R.id.et_aboutme);
        tv_submit_profile = findViewById(R.id.tv_submit_profile);
        dob = findViewById(R.id.dob);
        et_full_name = findViewById(R.id.et_full_name);
        et_mobile_number = findViewById(R.id.et_mobile_number);
        tv_male.setOnClickListener(this);
        tv_female.setOnClickListener(this);
        tv_submit_profile.setOnClickListener(this);
        tv_select_city.setOnClickListener(this);

        radioGroup = findViewById(R.id.gender);
        radioButtonMale = findViewById(R.id.male);
        radioButtonFemale = findViewById(R.id.female);
        genderAlertTv = findViewById(R.id.genderAlert);

        et_full_name.setText(Constants.getSharedPreferenceString(UserProfileUpdateFirstActivity.this, "userName", ""));

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
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
        });
        radioGroup.setSelected(Constants.getSharedPreferenceBoolean(UserProfileUpdateFirstActivity.this,"radioButton", true));
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                dob.setError(null);
                et_mobile_number.clearFocus();
                // date picker dialog
                picker = new DatePickerDialog(UserProfileUpdateFirstActivity.this, R.style.MyDatePickerDialogStyle,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateofbirth = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year);
                                // Log.e(TAG, "onDateSet: " + dateofbirth);
                                Constants.setSharedPreferenceString(UserProfileUpdateFirstActivity.this, "date", dateofbirth);
                                dob.setText(dateofbirth);
                                String d = dob.getText().toString();
                                // Log.e(TAG, "onDateSet: " + d);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 568025136000L);

                picker.show();
                picker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.custom_theme_color));
                picker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.custom_theme_color));
            }
        });
        dob.setText(Constants.getSharedPreferenceString(UserProfileUpdateFirstActivity.this, "date", ""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gender:

                if (!radioButtonFemale.isChecked() && !radioButtonMale.isChecked()) {
                    genderAlertTv.setVisibility(View.VISIBLE);
                    boolean isSelected = true;
                    SharedPreferences sharedPreferences=getSharedPreferences("Preference",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("gender",true);
                }
                //invalidateMaleBg();
                break;
           /*case R.id.tv_female:
                select_gender = false;
                isSelected = true;
                invalidateFeMaleBg();
                break;*/

            case R.id.tv_submit_profile:
                invalidateData();
                break;

            case R.id.tv_select_city:
                et_mobile_number.clearFocus();
                Intent intentSearch = new Intent(this, SearchCityActivity.class);
                startActivityForResult(intentSearch, PROFILE_UPDATE_PURPOSE_LOCATION);
                break;
            default:
        }
    }

    private void invalidateData() {
        fullname = et_full_name.getText().toString();
        mobileNumber = et_mobile_number.getText().toString();
        aboutme = et_aboutme.getText().toString();
        dateofbirth = dob.getText().toString();
        String select_city = tv_select_city.getText().toString();
        gen = gender;
        if (fullname.isEmpty()) {
            et_full_name.setError("Enter full Name*");
            et_full_name.requestFocus();
        } else if (!radioButtonFemale.isChecked() && !radioButtonMale.isChecked()) {
            genderAlertTv.setVisibility(View.VISIBLE);
        } else if (mobileNumber.isEmpty() || mobileNumber.length() < 10 || mobileNumber.length() > 15) {
            et_mobile_number.setError("Valid Mobile Number Required");
            et_mobile_number.setTextColor(getResources().getColor(R.color.mixed));
            et_mobile_number.requestFocus();
        } else if (select_city.isEmpty()) {
            tv_select_city.setError("Choose city*");
            tv_select_city.requestFocus();
            // et_mobile_number.clearFocus();
        } else if (dateofbirth.isEmpty()) {
            dob.setError("Choose date of birth*");
            dob.requestFocus();
            // tv_select_city.clearFocus();
        } else if (aboutme.isEmpty()) {
            et_aboutme.setError("Enter Bio*");
            et_aboutme.requestFocus();
            //et_mobile_number.clearFocus();
        }else if ( aboutme.length()<=25){
            et_aboutme.setError("Please write at least 25 character!");
            et_aboutme.requestFocus();
        } else {
            et_mobile_number.clearFocus();
            dob.clearFocus();
            et_aboutme.clearFocus();
            Constants.setSharedPreferenceString(UserProfileUpdateFirstActivity.this, "userName", fullname);
            sendUserDataDetails(fullname, gender, mobileNumber, select_city, dateofbirth, aboutme);
        }
    }
    private void sendUserDataDetails(String fullname, String gender, String mobileNumber, String select_city, String dateofbirth, String aboutme) {
       /* PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = pInfo.versionName;
        int versionCode = pInfo.versionCode;*/

        Api getApiData = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<UserProfileModel> call = getApiData.addUserProfileData(Constants.getSharedPreferenceInt(UserProfileUpdateFirstActivity.this, "userId", 0)
                , Constants.getSharedPreferenceString(UserProfileUpdateFirstActivity.this, "securityToken", "")
                , BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, fullname, mobileNumber, gender, select_city, dateofbirth, aboutme);

        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {
                loadBar.setVisibility(View.GONE);
                if (response != null) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getStatus() == 200) {
                            Intent intent_profile = new Intent(UserProfileUpdateFirstActivity.this, UserProfileUpdateSecondActivity.class);
                            startActivity(intent_profile);
                            finish();
                        } else {
                            Toast.makeText(UserProfileUpdateFirstActivity.this, "System Message: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(UserProfileUpdateFirstActivity.this, "System Message: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t);
                loadBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_UPDATE_PURPOSE_LOCATION && resultCode == RESULT_OK && null != data) {
            String uricity = data.getStringExtra("city");
            String uristate = data.getStringExtra("state");
            tv_select_city.setText(uricity + ", " + uristate);
            tv_select_city.setError(null);
        }
    }
    private void invalidateMaleBg() {
        tv_male.setBackgroundColor(getResources().getColor(R.color.custom_theme_color));
        tv_male.setTextColor(getResources().getColor(R.color.white));
        tv_female.setBackgroundColor(getResources().getColor(R.color.lighted_gray));
        tv_female.setTextColor(getResources().getColor(R.color.black));
    }
    private void invalidateFeMaleBg() {
        tv_female.setBackgroundColor(getResources().getColor(R.color.custom_theme_color));
        tv_female.setTextColor(getResources().getColor(R.color.white));
        tv_male.setBackgroundColor(getResources().getColor(R.color.lighted_gray));
        tv_male.setTextColor(getResources().getColor(R.color.black));
    }
}