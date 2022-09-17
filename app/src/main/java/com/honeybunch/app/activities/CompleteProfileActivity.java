package com.honeybunch.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;

import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.MultiPLeData;
import com.honeybunch.app.models.UpdateAboutModel;
import com.honeybunch.app.models.UpdateProfileImageModels;
import com.honeybunch.app.models.UserProfileDataInterestModels;
import com.honeybunch.app.models.UserProfileDataModels;
import com.honeybunch.app.models.UserProfileModel;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;
import com.honeybunch.app.utils.CustomTextView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_edit_user_details;
    TextView tv_edit_purpose_dating;
    TextView tv_edit_aboutus;
    TextView tv_add_photo;
    EditText et_aboutme;
    EditText et_user_name;
    EditText et_uer_dob;
    TextView et_user_location;

    RelativeLayout rl_hiden_form;
    RelativeLayout rl_dating_hiden;
    RelativeLayout rl_hiden_tellus;
    RelativeLayout rl_hidden_apr;
    RelativeLayout rl_chip_save;
    RelativeLayout rl_save_interest_button;

    TextView bt_save_user;
    TextView bt_user_cancel;
    TextView bt_save_date;
    TextView bt_date_cancel;
    TextView tv_edit_apperance;
    TextView bt_save_aprear;
    TextView bt_cencel;
    TextView bt_cencel_about;
    TextView bt_save_about;
    TextView bt_save_chip;
    TextView bt_chip_cancel;
    TextView subaddress;
    TextView tv_purposedate;
    CustomTextView tv_interest_value;
    TextView tv_edit_interset;
    RecyclerView recyclerView;
    ImageView profile_image,backBtn;
    ImageView image_edit;
    private static int RESULT_LOAD_IMAGE = 1;
    SpinKitView loadBar;
    List<MultiPLeData> multiPLeDataList;
    ArrayList<MultiPLeData> multiPLeDataInterset;
    RecyclerView recyler_chip;
    List<String> datePurpose = new ArrayList<>();
    List<String> datePurposelist;
    List<String> interset;
    List<String> intersetList;
    //private Bitmap bitmap;
    TextView uer_name;
    TextView tell_us;
    TextView tell_apreance;
    DatePickerDialog picker;
    String dateofbirth;
    static int PROFILE_UPDATE_INTEREST = 1005;
    static int PROFILE_UPDATE_PURPOSE_CHAT = 1002;
    static int PROFILE_UPDATE_PURPOSE_LOCATION = 1001;
    List<String> selected;
    List<String> selectedInterest;
    List<String> hairlist;
    List<String> heightlist;
    List<String> weightlist;
    List<String> eyelist;
    private static final int REQUEST_PERMISSION = 101;
    Spinner spinner_haircolor;
    Spinner spinner_weight;
    Spinner spinner_height;
    Spinner spinner_eyecolor;
    String appearanceHairData;
    String appearanceWeightData;
    String appearanceHeightData;
    String appearanceEyeData;
    Spinner spinner_education;
    Spinner spinner_profession;
    List<String> educationlist  = new ArrayList<>();
    List<String> professionlist = new ArrayList<>();
    String educationData;
    String professionData;
    RadioButton radioButtonMale, radioButtonFemale;
    RadioGroup radioGroup;
    String gender;
    TextView genderAlertTv;
    Toolbar toolbar;
    List<String> selectedDatePurpose;
    SharedPreferences.Editor editor;
    ArrayAdapter dataAdapter_education,dataAdapter_professional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        toolbar = findViewById(R.id.profileToolbar);
        backBtn=findViewById(R.id.backbtn_iv);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setDisplayShowHomeEnabled(true);

    //    getSupportActionBar().setTitle(Html.fromHtml("<font color='#000'>Complete Profile</font>"));
      /*  TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);*/


      /*  final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.unselect_color), PorterDuff.Mode.SRC_ATOP);*/
       // getSupportActionBar().setHomeAsUpIndicator(upArrow);
        loadBar = findViewById(R.id.spin_kit);
        radioGroup = findViewById(R.id.gender);
        radioButtonMale = findViewById(R.id.male);
        radioButtonFemale = findViewById(R.id.female);
        genderAlertTv = findViewById(R.id.genderAlert);
        //recyclerView = findViewById(R.id.recyclerview);
        profile_image = findViewById(R.id.profile_image);
        rl_hiden_form = findViewById(R.id.rl_hiden_form);
        rl_hidden_apr = findViewById(R.id.rl_hidden_apr);
        rl_dating_hiden = findViewById(R.id.rl_dating_hiden);
        rl_hiden_tellus = findViewById(R.id.rl_hiden_tellus);
        tv_edit_user_details = findViewById(R.id.tv_edit_user_details);
        tv_edit_purpose_dating = findViewById(R.id.tv_edit_purpose_dating);
        recyler_chip = findViewById(R.id.recyler_chip);
        tv_purposedate = findViewById(R.id.tv_purposedate);
        tv_interest_value = findViewById(R.id.tv_interest_value);
        bt_save_chip = findViewById(R.id.bt_save_chip);
        rl_save_interest_button = findViewById(R.id.rl_save_interest_button);
        rl_chip_save = findViewById(R.id.rl_chip_save);
        tv_edit_interset = findViewById(R.id.tv_edit_interset);
        image_edit = findViewById(R.id.image_edit);
        uer_name = findViewById(R.id.uer_name);
        tell_us = findViewById(R.id.tell_us);
        tell_apreance = findViewById(R.id.tell_apreance);
        tv_edit_user_details.setOnClickListener(this);
        image_edit.setOnClickListener(this);

        tv_add_photo = findViewById(R.id.tv_add_photo);
        tv_edit_apperance = findViewById(R.id.tv_edit_apperance);
        bt_save_aprear = findViewById(R.id.bt_save_aprear);
        bt_cencel = findViewById(R.id.bt_cencel);
        tv_edit_aboutus = findViewById(R.id.tv_edit_aboutus);
        et_aboutme = findViewById(R.id.et_aboutme);
        et_user_name = findViewById(R.id.et_user_name);
        et_uer_dob = findViewById(R.id.et_uer_dob);
        et_user_location = findViewById(R.id.et_user_location);
        subaddress = findViewById(R.id.subaddress);
        bt_save_user = findViewById(R.id.bt_save_user);
        bt_user_cancel = findViewById(R.id.bt_user_cancel);
        bt_save_date = findViewById(R.id.bt_save_date);
        bt_date_cancel = findViewById(R.id.bt_date_cancel);
        bt_save_about = findViewById(R.id.bt_save_about);
        bt_cencel_about = findViewById(R.id.bt_cencel_about);
        bt_chip_cancel = findViewById(R.id.bt_chip_cancel);

        spinner_haircolor = (Spinner) findViewById(R.id.spinner_haircolor);
        spinner_weight = (Spinner) findViewById(R.id.spinner_weight);
        spinner_height = (Spinner) findViewById(R.id.spinner_height);
        spinner_eyecolor = (Spinner) findViewById(R.id.spinner_eyecolor);
        spinner_education = (Spinner) findViewById(R.id.spin_education);
        spinner_profession = (Spinner) findViewById(R.id.spinner_profession);

        bt_save_user.setOnClickListener(this);
        bt_chip_cancel.setOnClickListener(this);
        bt_save_date.setOnClickListener(this);
        bt_user_cancel.setOnClickListener(this);
        rl_dating_hiden.setOnClickListener(this);
        tv_edit_purpose_dating.setOnClickListener(this);
        tv_edit_aboutus.setOnClickListener(this);
        rl_hiden_tellus.setOnClickListener(this);
        bt_save_about.setOnClickListener(this);
        tv_add_photo.setOnClickListener(this);
        tv_edit_apperance.setOnClickListener(this);
        bt_save_aprear.setOnClickListener(this);
        bt_cencel_about.setOnClickListener(this);
        bt_cencel.setOnClickListener(this);
        bt_date_cancel.setOnClickListener(this);
        bt_save_chip.setOnClickListener(this);
        tv_edit_interset.setOnClickListener(this);
        et_user_location.setOnClickListener(this);

        hairlist = new ArrayList<>();
        heightlist = new ArrayList<>();
        eyelist = new ArrayList<>();
        weightlist = new ArrayList<>();

        //for hair
         hairlist.add("Select Hair Color");
        hairlist.add("Blond");
        hairlist.add("Brown");
        hairlist.add("Red");
        hairlist.add("Light Brown");
        hairlist.add("Black");
        hairlist.add("Gray");
        hairlist.add("White");

        ArrayAdapter dataAdapter_hair_list= new ArrayAdapter(this, android.R.layout.simple_spinner_item, hairlist);
        // Drop down layout style - list view with radio button
        dataAdapter_hair_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_haircolor.setAdapter(dataAdapter_hair_list);
        // for weight
         weightlist.add("Select Weight");
        weightlist.add("10 kg - 30 kg");
        weightlist.add("31 kg - 70 kg");
        weightlist.add("71 kg - 99 kg");
        weightlist.add("above 100 kg");

        ArrayAdapter dataAdapter_wieght = new ArrayAdapter(this, android.R.layout.simple_spinner_item, weightlist);
        // Drop down layout style - list view with radio button
        dataAdapter_wieght.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_weight.setAdapter(dataAdapter_wieght);

        // for height
        heightlist.add("Select Height");
        heightlist.add("143 cm");
        heightlist.add("146 cm");
        heightlist.add("150 cm");
        heightlist.add("170 cm");

        ArrayAdapter dataAdapter_smoke = new ArrayAdapter(this, android.R.layout.simple_spinner_item, heightlist);
        // Drop down layout style - list view with radio button
        dataAdapter_smoke.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_height.setAdapter(dataAdapter_smoke);
        //for eating
        eyelist.add("Select Eye Color");
        eyelist.add("Brown");
        eyelist.add("Black");

        ArrayAdapter dataAdapter_eyelist = new ArrayAdapter(this, android.R.layout.simple_spinner_item, eyelist);
        // Drop down layout style - list view with radio button
        dataAdapter_eyelist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_eyecolor.setAdapter(dataAdapter_eyelist);
        //for haircolor
        spinner_haircolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("TAG", "onItemSelected: " + hairlist.get(position));
                appearanceHairData = hairlist.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });
        //for weight
        spinner_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("TAG", "onItemSelected: " + weightlist.get(position));
                appearanceWeightData = weightlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });

        // for heigth
        spinner_height.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("TAG", "onItemSelected: " + heightlist.get(position));
                appearanceHeightData = heightlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });

        //for eye color
        spinner_eyecolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("TAG", "onItemSelected: " + eyelist.get(position));
                appearanceEyeData = eyelist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        //for education
        educationlist.add("Select Education");
        educationlist.add("High School");
        educationlist.add("Trade School");
        educationlist.add("Diploma");
        educationlist.add("Bachelor's");
        educationlist.add("Master's");
        educationlist.add("Doctorate's");

        dataAdapter_education = new ArrayAdapter(this, android.R.layout.simple_spinner_item, educationlist);
        // Drop down layout style - list view with radio button
        dataAdapter_education.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_education.setAdapter(dataAdapter_education);

        //for profession
        professionlist.add("Select Profession");
        professionlist.add("Software Professional");
        professionlist.add("Business Person");
        professionlist.add("Students");
        professionlist.add("Sales / Marketing");
        professionlist.add("Technical / Engineering / Science");
        professionlist.add("Hospitality / Service");
        professionlist.add("Executive / Management");
        professionlist.add("Account / Finance");
        professionlist.add("Acting / Artist / Musician");
        professionlist.add("Architect");
        professionlist.add("Journalism");
        professionlist.add("Fashion");
        professionlist.add("Law");
        professionlist.add("Teaching");
        professionlist.add("Agriculture");
        professionlist.add("Civil Services(IAS, IPS, IRS, IFS)");
        professionlist.add("Defence");
        professionlist.add("Social Services");
        professionlist.add("Medical");
        professionlist.add("Writer");
        professionlist.add("Others");

        dataAdapter_professional = new ArrayAdapter(this, android.R.layout.simple_spinner_item, professionlist);
        // Drop down layout style - list view with radio button
        dataAdapter_professional.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_profession.setAdapter(dataAdapter_professional);
        // for education
        spinner_education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("TAG", "onItemSelected: " + educationlist.get(position));
                educationData = educationlist.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });

        //for profession
        spinner_profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("TAG", "onItemSelected: " + professionlist.get(position));
                professionData = professionlist.get(position);
                //professionlist.set(Constants.setSharedPreferenceInt(CompleteProfileActivity.this,"profession"," "));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("Pref",0);
        int genderSP=sharedPreferences.getInt("genderSP",3);
        editor=sharedPreferences.edit();
        if(genderSP==1)
        {
            radioButtonMale.setChecked(true);
        }
        else if (genderSP==0){
            radioButtonFemale.setChecked(true);
        }


/*
        Picasso.get().load(Constants.getSharedPreferenceString(CompleteProfileActivity.this, "userPic", ""))
                .into(profile_image);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.animate_progress)
                .error(R.drawable.animate_progress)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
*/

        Glide.with(CompleteProfileActivity.this)
                .load(Constants.getSharedPreferenceString(CompleteProfileActivity.this, "userPic", "")).into(profile_image);

        et_aboutme.addTextChangedListener(new TextWatcher() {
            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                String address = et_aboutme.getText().toString().trim();
                if (!address.isEmpty()) {
                    bt_save_about.setVisibility(View.VISIBLE);
                } else {
                    bt_save_about.setVisibility(View.GONE);
                }
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank

            }

            public void afterTextChanged(Editable c) {
                // this one too

            }
        });

        et_user_name.addTextChangedListener(new TextWatcher() {
            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                String address = et_user_name.getText().toString().trim();
                if (!address.isEmpty()) {
                    bt_save_user.setVisibility(View.VISIBLE);
                } else {
                    bt_save_user.setVisibility(View.GONE);
                }
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {

            }
            public void afterTextChanged(Editable c) {
            }
        });
        et_uer_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                et_uer_dob.setError(null);
                final long today = System.currentTimeMillis() - 1000;
                // date picker dialog
                picker = new DatePickerDialog(CompleteProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                dateofbirth = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year);
                                // Log.e(TAG, "onDateSet: " + dateofbirth);
                                Constants.setSharedPreferenceString(CompleteProfileActivity.this, "date", dateofbirth);
                                et_uer_dob.setText(dateofbirth);
                                String d = et_uer_dob.getText().toString();
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 568025136000L);
                picker.show();

            }
        });
        et_uer_dob.setText(Constants.getSharedPreferenceString(CompleteProfileActivity.this, "date", ""));

        tv_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(CompleteProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CompleteProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    return;
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            }
        });
        getUserProfile();
    }

    private void getupdatePurpose(String datePurpose) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<UserProfileModel> call = service.updatePurposeProfile(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE, datePurpose, "Post");
        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, final Response<UserProfileModel> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            Toast.makeText(CompleteProfileActivity.this, "Update Purposes " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            rl_chip_save.setVisibility(View.GONE);
                        }
                    }
                } else {
                    Toast.makeText(CompleteProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();
                Log.e("response", t.toString());
            }
        });
    }
    private void getUserProfileUpdate(String userName, String userDob, String userCity) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<UserProfileDataModels> call = service.getUserProfileUpdateData(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE,"Post", userName, gender, userDob, userCity,educationData,professionData);

        Log.e("TAG", "getUserProfileUpdate: "+Constants.getSharedPreferenceInt(this, "userId", 0) );
        Log.e("TAG", "getUserProfileUpdate: "+Constants.getSharedPreferenceString(this, "securityToken", ""));
        Log.e("TAG", "getUserProfileUpdate:Edu "+educationData);
        Log.e("TAG", "getUserProfileUpdate:Pro "+professionData);
        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UserProfileDataModels>() {
            @Override
            public void onResponse(Call<UserProfileDataModels> call, final Response<UserProfileDataModels> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            uer_name.setText(response.body().getFullName() + ", " + response.body().getUserAge());
                            Constants.setSharedPreferenceString(CompleteProfileActivity.this, "userName", response.body().getFullName());
                            Constants.setSharedPreferenceString(CompleteProfileActivity.this, "userAge", response.body().getUserAge());
                            Constants.setSharedPreferenceString(CompleteProfileActivity.this,"city", response.body().getLocation());
                            subaddress.setText(response.body().getLocation());
                            et_user_name.setText(response.body().getFullName());
                            et_uer_dob.setText(response.body().getAge());
                            et_user_location.setText(response.body().getLocation());
                            genderAlertTv.setText(gender);
                            rl_hiden_form.setVisibility(View.GONE);
                        }
                    }
                } else {
                    Toast.makeText(CompleteProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfileDataModels> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();
                Log.e("response", t.toString());
            }
        });
    }

    private void updateAboutProfile(Editable text) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<UpdateAboutModel> call = service.editupdateAboutProfile(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE,
                "Post",text.toString());
        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UpdateAboutModel>() {
            @Override
            public void onResponse(Call<UpdateAboutModel> call, final Response<UpdateAboutModel> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            //Log.e("TAG", "onResponse: "+response.body().getAbout() );
                            Toast.makeText(CompleteProfileActivity.this, "Update " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            rl_hiden_tellus.setVisibility(View.GONE);
                            tell_us.setText(response.body().getAbout());
                        }
                    }
                } else {
                    Toast.makeText(CompleteProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateAboutModel> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();
                Log.e("response", t.toString());
            }
        });
    }

    private void updateInterestProfile(String text) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<UserProfileDataInterestModels> call = service.updateInterestProfile(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, text, "Post");
        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UserProfileDataInterestModels>() {
            @Override
            public void onResponse(Call<UserProfileDataInterestModels> call, final Response<UserProfileDataInterestModels> response) {
                dismissProgressDialog();

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            Toast.makeText(CompleteProfileActivity.this, "Update Interesets " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            rl_chip_save.setVisibility(View.GONE);
                        }
                    }
                } else {
                    Toast.makeText(CompleteProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserProfileDataInterestModels> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();
                Log.e("response", t.toString());
            }
        });
    }

    private void updateAppreanceProfile(String appearanceHairData, String appearanceWeightData, String appearanceHeightData, String appearanceEyeData) {
        String apreanceData = appearanceHairData + "," + appearanceWeightData + "," + appearanceHeightData + "," + appearanceEyeData;
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<UpdateAboutModel> call = service.editupdateApreanceData(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE, "Post",apreanceData);

        Log.e("TAG", "updateAppreanceProfile: " + apreanceData);
        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UpdateAboutModel>() {
            @Override
            public void onResponse(Call<UpdateAboutModel> call, final Response<UpdateAboutModel> response) {
                loadBar.setVisibility(View.GONE);
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            //Log.e("TAG", "onResponse:11 "+new Gson().toJson(response.body()) );
                            Toast.makeText(CompleteProfileActivity.this, "Update Successfully " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            tell_apreance.setText(apreanceData);
                        }
                    }
                } else {
                    Toast.makeText(CompleteProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateAboutModel> call, Throwable t) {
                // Log error here since request failed
                loadBar.setVisibility(View.GONE);
                Log.e("response", t.toString());
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void getUserProfile() {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<UserProfileDataModels> call = service.getUserProfileData(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE, "Get");
        loadBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<UserProfileDataModels>() {
            @Override
            public void onResponse(Call<UserProfileDataModels> call, final Response<UserProfileDataModels> response) {
                dismissProgressDialog();
                UserProfileDataModels data=response.body();
                // Log.e("TAG", "onResponse:APPR "+new Gson().toJson(response.body()));
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            uer_name.setText(response.body().getFullName() + ", " + response.body().getUserAge());
                            if (datePurpose!=null) {
                                datePurpose = response.body().getPurpose();
                            }else{
                                Toast.makeText(CompleteProfileActivity.this, "Please select your purpose", Toast.LENGTH_SHORT).show();
                            }
                            datePurposelist = response.body().getPurposeList();
                            interset = response.body().getInterest();
                            intersetList = response.body().getInterestList();
                            subaddress.setText(response.body().getLocation());
                            et_user_name.setText(response.body().getFullName());
                            et_uer_dob.setText(response.body().getAge());
                            tell_us.setText(response.body().getBioText());
                            tell_apreance.setText(response.body().getAppearance());
                            et_user_location.setText(response.body().getLocation());
                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    if (checkedId ==R.id.male){
                                        //getIntent().getIntExtra("radioButton",1);
                                       editor.putInt("genderSP",1);
                                        genderAlertTv.setVisibility(View.GONE);
                                    }else if (checkedId ==R.id.female){
                                       //getIntent().getIntExtra("radioButton",0);
                                        editor.putInt("genderSP",0);
                                    }
                                    editor.commit();
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
                            for (String edlist: educationlist) {
                                if (edlist.matches(data.getEducation())){
                                    Log.d("abc", "education: "+edlist);
                                   spinner_education.setSelection(dataAdapter_education.getPosition(educationData));
                                }
                            }
                           // professionlist=new ArrayList<>();
                            for (String profList: professionlist){
                                if(profList.matches(data.getProfession())){
                                    //spinner_profession.setSelection();
                                }
                            }
                            if (!datePurpose.isEmpty()) {
                                String datePurpose = TextUtils.join(", ", data.getPurpose());
                                tv_purposedate.setText(datePurpose);
                            }
                            if (!interset.isEmpty()) {
                                String dateinterset = TextUtils.join(", ", data.getInterest());
                                tv_interest_value.setText(dateinterset);
                            }
                            RecyclerView recylerview_multiple = findViewById(R.id.recylerview_multiple);
                            multiPLeDataList = new ArrayList<>();
                            for (String list : datePurposelist) {
                                multiPLeDataList.add(new MultiPLeData(list));
                            }
                            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(CompleteProfileActivity.this);
                            layoutManager.setFlexDirection(FlexDirection.ROW);
                            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                            layoutManager.setAlignItems(AlignItems.FLEX_START);
                            recylerview_multiple.setLayoutManager(layoutManager);
                            FlexboxAdapter adapter = new FlexboxAdapter(CompleteProfileActivity.this, multiPLeDataList);
                            recylerview_multiple.setAdapter(adapter);
                            multiPLeDataInterset = new ArrayList<>();
                            for (String list : intersetList) {
                                multiPLeDataInterset.add(new MultiPLeData(list));
                            }
                            RecyclerView recyler_chip = findViewById(R.id.recyler_chip);
                            FlexboxLayoutManager layoutManager_interset = new FlexboxLayoutManager(CompleteProfileActivity.this);
                            layoutManager_interset.setFlexDirection(FlexDirection.ROW);
                            layoutManager_interset.setJustifyContent(JustifyContent.FLEX_START);
                            layoutManager_interset.setAlignItems(AlignItems.FLEX_START);
                            recyler_chip.setLayoutManager(layoutManager_interset);
                            FlexboxInterestAdapter adapter_interest = new FlexboxInterestAdapter(CompleteProfileActivity.this, multiPLeDataInterset);
                            recyler_chip.setAdapter(adapter_interest);
                        }
                    }
                } else {
                    Toast.makeText(CompleteProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserProfileDataModels> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();
                Log.e("response", t.toString());
            }
        });
    }

    //for date purpose
    public class FlexboxAdapter extends RecyclerView.Adapter<FlexboxAdapter.ViewHolder> {

        Context context;
        List<MultiPLeData> arrayList = new ArrayList<>();
        public FlexboxAdapter(Context context, List<MultiPLeData> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        @Override
        public FlexboxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.custom_layout, parent, false);
            return new FlexboxAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FlexboxAdapter.ViewHolder holder, int position) {
            holder.title.setText(arrayList.get(position).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    arrayList.get(pos).setSelected(!arrayList.get(pos).isSelected());
                    if (arrayList.get(pos).isSelected()) {
                        holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_square_bg_without_border));
                        holder.title.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_square_bg_with_border));
                        holder.title.setTextColor(getResources().getColor(R.color.black));
                    }
                    getSelected();
                }
            });
        }
        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            LinearLayout ll_custom_bg;

            public ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tvTitle);
                ll_custom_bg = itemView.findViewById(R.id.ll_custom_bg);
            }
        }

        public List<String> getSelected() {
            selected = new ArrayList<>();
            for (MultiPLeData model : multiPLeDataList) {
                if (model.isSelected()) {
                    selected.add(model.getName());
                }
            }
            return selected;
        }
    }


    //for interest
    public class FlexboxInterestAdapter extends RecyclerView.Adapter<FlexboxInterestAdapter.ViewHolder> {

        Context context;
        List<MultiPLeData> arrayList1 = new ArrayList<>();

        public FlexboxInterestAdapter(Context context, List<MultiPLeData> arrayList) {
            this.context = context;
            this.arrayList1 = arrayList;
        }

        @Override
        public FlexboxInterestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.custom_layout, parent, false);
            return new FlexboxInterestAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FlexboxInterestAdapter.ViewHolder holder, int position) {
            holder.title.setText(arrayList1.get(position).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    arrayList1.get(pos).setSelected(!arrayList1.get(pos).isSelected());
                    if (arrayList1.get(pos).isSelected()) {
                        holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_square_bg_without_border));
                        holder.title.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_square_bg_with_border));
                        holder.title.setTextColor(getResources().getColor(R.color.black));
                    }
                    getInterestList();
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList1.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            LinearLayout ll_custom_bg;

            public ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tvTitle);
                ll_custom_bg = itemView.findViewById(R.id.ll_custom_bg);
            }
        }

        public List<String> getInterestList() {
            selectedInterest = new ArrayList<>();
            for (MultiPLeData model : multiPLeDataInterset) {
                if (model.isSelected()) {
                    selectedInterest.add(model.getName());
                }
            }
            return selectedInterest;
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri path = data.getData();

            updateProfileImage(path);

        } else if (requestCode == PROFILE_UPDATE_PURPOSE_LOCATION && resultCode == RESULT_OK && null != data) {
            String uricity = data.getStringExtra("city");
            String uristate = data.getStringExtra("state");

            et_user_location.setText(uricity + ", " + uristate);

        }
//        else if (requestCode == PROFILE_UPDATE_INTEREST && resultCode == RESULT_OK && null != data) {
//            // multiPLeDataInterset = data.getStringArrayListExtra("mylist");
//            Log.e("TAG", "onActivityResult:1003 " + data.getStringExtra("interest"));
//            tv_interest_value.setText(data.getStringExtra("interest"));
//
//
//        }
    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public static byte[] getStreamByteFromImage(final File imageFile) {

        Bitmap photoBitmap = BitmapFactory.decodeFile(imageFile.getPath());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        int imageRotation = getImageRotation(imageFile);

        if (imageRotation != 0)
            photoBitmap = getBitmapRotatedByDegree(photoBitmap, imageRotation);
        photoBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);

        return stream.toByteArray();
    }

    private static int getImageRotation(final File imageFile) {

        ExifInterface exif = null;
        int exifRotation = 0;

        try {
            exif = new ExifInterface(imageFile.getPath());
            exifRotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (exif == null)
            return 0;
        else
            return exifToDegrees(exifRotation);
    }

    private static int exifToDegrees(int rotation) {
        if (rotation == ExifInterface.ORIENTATION_ROTATE_90)
            return 90;
        else if (rotation == ExifInterface.ORIENTATION_ROTATE_180)
            return 180;
        else if (rotation == ExifInterface.ORIENTATION_ROTATE_270)
            return 270;

        return 0;
    }

    private static Bitmap getBitmapRotatedByDegree(Bitmap bitmap, int rotationDegree) {
        Matrix matrix = new Matrix();
        matrix.preRotate(rotationDegree);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void updateProfileImage(Uri picturePath) {

        File file = new File(getRealPathFromURI(picturePath));

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), getStreamByteFromImage(file));
        MultipartBody.Part parts = MultipartBody.Part.createFormData("picture", file.getName(), requestBody);

        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(Constants.getSharedPreferenceInt(this, "userId", 0)));
        RequestBody SecurityTocken = RequestBody.create(MediaType.parse("multipart/form-data"), Constants.getSharedPreferenceString(this, "securityToken", ""));
        RequestBody versionName = RequestBody.create(MediaType.parse("multipart/form-data"), BuildConfig.VERSION_NAME);
        RequestBody versionCode = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(BuildConfig.VERSION_CODE));
        RequestBody status = RequestBody.create(MediaType.parse("multipart/form-data"), "true");
        RequestBody actionType = RequestBody.create(MediaType.parse("multipart/form-data"), "Post");


        Call<UpdateProfileImageModels> call = service.uploadProfileImage(userId, SecurityTocken, versionName, versionCode, parts, status, actionType);

        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UpdateProfileImageModels>() {
            @Override
            public void onResponse(Call<UpdateProfileImageModels> call, final Response<UpdateProfileImageModels> response) {
                dismissProgressDialog();

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
//                            Picasso.get().load(response.body().getImage_url())
//                                    .into(profile_image);

                            Glide.with(CompleteProfileActivity.this)
                                    .load(response.body().getImage_url()) // Uri of the picture
                                    .into(profile_image);

//                            Picasso.get().load(response.body().getImage_url()).into(profile_image);

                            Constants.setSharedPreferenceString(CompleteProfileActivity.this, "userPic", response.body().getImage_url());

                            Toast.makeText(CompleteProfileActivity.this, "Update Profile " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(CompleteProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UpdateProfileImageModels> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit_user_details:
                rl_hiden_form.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_user_cancel:
                rl_hiden_form.setVisibility(View.GONE);
                break;
            case R.id.et_user_location:
                Intent intentSearch = new Intent(this, SearchCityActivity.class);
                startActivityForResult(intentSearch, PROFILE_UPDATE_PURPOSE_LOCATION);
                break;
            case R.id.bt_save_user:
                if (TextUtils.isEmpty(et_user_name.getText().toString())) {
                    et_user_name.setError("User name is required*");
                    et_user_name.findFocus();
                } else if (TextUtils.isEmpty(et_user_location.getText().toString())) {
                    et_user_location.setError("City is required*");
                    et_user_location.findFocus();
                } else if (TextUtils.isEmpty(et_uer_dob.getText().toString())) {
                    et_uer_dob.setError("DOB is required*");
                    et_uer_dob.findFocus();
                } else if (!radioButtonFemale.isChecked() && !radioButtonMale.isChecked()) {
                    genderAlertTv.setVisibility(View.VISIBLE);
                } else if (educationData.isEmpty() || spinner_education.getSelectedItem().equals("Select Education")) {
                    TextView errorText = (TextView) spinner_education.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Select Education*");//changes the selected item text to this
                    Toast.makeText(this, "Select Education*", Toast.LENGTH_SHORT).show();
                } else if (professionData.isEmpty() || spinner_profession.getSelectedItem().equals("Select Profession")) {
                    TextView errorText = (TextView) spinner_profession.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Select Profession*");//changes the selected item text to this
                    Toast.makeText(this, "Select Profession*", Toast.LENGTH_SHORT).show();
                } else {
                    getUserProfileUpdate(et_user_name.getText().toString(), et_uer_dob.getText().toString(), et_user_location.getText().toString());
                }
                break;

            case R.id.tv_edit_purpose_dating:
                rl_dating_hiden.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_date_cancel:
                rl_dating_hiden.setVisibility(View.GONE);
                break;
            case R.id.bt_save_date:
                rl_dating_hiden.setVisibility(View.GONE);
                String datePurpose = TextUtils.join(", ", selected);
               // String datePurpose=tv_purposedate.getText().toString();
                tv_purposedate.setText(datePurpose);
                getupdatePurpose(datePurpose);
                break;
            case R.id.tv_edit_aboutus:
                rl_hiden_tellus.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_cencel_about:
                rl_hiden_tellus.setVisibility(View.GONE);
                break;
            case R.id.bt_save_about:
                if (TextUtils.isEmpty(et_aboutme.getText())) {
                    et_aboutme.setError("bio is required*");
                } else {
                    updateAboutProfile(et_aboutme.getText());
                }
                break;
            case R.id.tv_edit_apperance:
                rl_hidden_apr.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_cencel:
                rl_hidden_apr.setVisibility(View.GONE);
                break;
            case R.id.bt_save_aprear:
                if (appearanceHairData.isEmpty() || spinner_haircolor.getSelectedItem().equals("Select Hair Color")) {
                    TextView errorText = (TextView) spinner_haircolor.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Select Hair Color*");//changes the selected item text to this
                    Toast.makeText(this, "Select Hair Color*", Toast.LENGTH_SHORT).show();
                } else if (appearanceWeightData.isEmpty() || spinner_weight.getSelectedItem().equals("Select Weight")) {
                    TextView errorText = (TextView) spinner_weight.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Select Weight*");//changes the selected item text to this
                    Toast.makeText(this, "Select Weight*", Toast.LENGTH_SHORT).show();
                }
               else if (appearanceHeightData.isEmpty() || spinner_height.getSelectedItem().equals("Select Height")) {
                    TextView errorText = (TextView) spinner_height.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Select Height*");//changes the selected item text to this
                    Toast.makeText(this, "Select Height*", Toast.LENGTH_SHORT).show();
                } else if (appearanceEyeData.isEmpty() || spinner_eyecolor.getSelectedItem().equals("Select Eye Color")) {
                    TextView errorText = (TextView) spinner_eyecolor.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Select Eye Color*");//changes the selected item text to this
                    Toast.makeText(this, "Select Eye Color*", Toast.LENGTH_SHORT).show();
                }  else {
                    rl_hidden_apr.setVisibility(View.GONE);
                    updateAppreanceProfile(appearanceHairData, appearanceWeightData, appearanceHeightData, appearanceEyeData);
                }
                break;
            case R.id.bt_save_chip:
               String interesetlist = TextUtils.join(", ", selectedInterest);
                rl_chip_save.setVisibility(View.GONE);
                tv_interest_value.setText(interesetlist);
                updateInterestProfile(interesetlist);

                break;

            case R.id.bt_chip_cancel:
                rl_chip_save.setVisibility(View.GONE);
                break;

            case R.id.tv_edit_interset:
                rl_chip_save.setVisibility(View.VISIBLE);
//                Intent intent_interest = new Intent(CompleteProfileActivity.this, InterestsActivity.class);
//                intent_interest.putExtra("mylist", multiPLeDataInterset);
//                startActivityForResult(intent_interest, PROFILE_UPDATE_INTEREST);

                break;
            case R.id.image_edit:
                Intent intent_myPhotos = new Intent(CompleteProfileActivity.this, MyPhotosActivity.class);
                startActivity(intent_myPhotos);
                break;
            default:


        }
    }

    public int getCameraPhotoOrientation(String imageFilePath) {
        int rotate = 0;
        try {

            ExifInterface exif;
            exif = new ExifInterface(imageFilePath);
            String exifOrientation = exif
                    .getAttribute(ExifInterface.TAG_ORIENTATION);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.

            } else {
                // User refused to grant permission.
            }
        }
    }

   /* public List<String> getPurposeList() {
        selectedDatePurpose = new ArrayList<>();
        for (MultiPLeData model : arrayListDatePuorpse) {
            if (model.isSelected()) {
                selectedDatePurpose.add(model.getName());
            }
        }
        return selectedDatePurpose;
    }*/

}