package com.honeybunch.app.profile;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.activities.AccountActivity;
import com.honeybunch.app.activities.CompleteProfileActivity;
import com.honeybunch.app.activities.GetCreditActivity;
import com.honeybunch.app.activities.WebViewActivity;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.UpdateProfileImageModels;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ProfileViewModel mViewModel;

    private LinearLayout ll_complete_profile;
    private LinearLayout ll_getcredit;
    private LinearLayout ll_account;
    private LinearLayout ll_term_services;
    private LinearLayout ll_privacy_policy;
    private LinearLayout ll_about_us;
    ImageView user_profile;
    TextView tv_view_edit;
    TextView tv_username;
    ImageView imageView_upload;
    private static int RESULT_LOAD_IMAGE = 1;
    SpinKitView loadBar;
    private static final int REQUEST_PERMISSION = 101;

    FragmentActivity mActivity;


    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        //((MainActivity) getActivity()).setTitle(Html.fromHtml("<font color='#000'>Profile</font>"));

        ll_complete_profile = view.findViewById(R.id.ll_complete_profile);
        tv_view_edit = view.findViewById(R.id.tv_view_edit);
        user_profile = view.findViewById(R.id.user_profile);
        ll_account = view.findViewById(R.id.ll_account);
        ll_getcredit = view.findViewById(R.id.ll_getcredit);
        ll_about_us = view.findViewById(R.id.ll_about_us);
        tv_username = view.findViewById(R.id.tv_username);
        imageView_upload = view.findViewById(R.id.imageView_upload);
        loadBar = view.findViewById(R.id.spin_kit);
        ll_privacy_policy = view.findViewById(R.id.ll_privacy_policy);
        ll_term_services = view.findViewById(R.id.ll_term_services);
        ll_about_us.setOnClickListener(this);
        ll_complete_profile.setOnClickListener(this);
        ll_privacy_policy.setOnClickListener(this);
        ll_term_services.setOnClickListener(this);
        ll_getcredit.setOnClickListener(this);
        ll_account.setOnClickListener(this);

        tv_view_edit.setOnClickListener(this);
        tv_username.setText(Constants.getSharedPreferenceString(mActivity, "userName", "") + ", " + Constants.getSharedPreferenceString(mActivity, "userAge", ""));

        Glide.with(this).load(Constants.getSharedPreferenceString(mActivity, "userPic", ""))
                .into(user_profile);

        imageView_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    return;
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            }
        });
        return view;
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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_complete_profile:
            case R.id.tv_view_edit:
                Intent intent = new Intent(mActivity, CompleteProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_getcredit:
                Intent intentgetCredit = new Intent(mActivity, GetCreditActivity.class);
                startActivity(intentgetCredit);
                break;

            case R.id.ll_account:
                Intent intent_account = new Intent(mActivity, AccountActivity.class);
                startActivity(intent_account);
                break;

            case R.id.ll_privacy_policy:
                Intent intent_privacy = new Intent(mActivity, WebViewActivity.class);
               // intent_privacy.putExtra("url","https://honeybunch.app/privacy-policy.html");
                intent_privacy.putExtra("url","https://honeybunch.app/privacy-policy.html");
                intent_privacy.putExtra("title","Privacy Policy");
                startActivity(intent_privacy);
                break;

            case R.id.ll_term_services:
                Intent intent_service = new Intent(mActivity, WebViewActivity.class);
                intent_service.putExtra("url","https://honeybunch.app/terms-conditions.html");
                intent_service.putExtra("title","Terms Of Services");
                startActivity(intent_service);
                break;

            case R.id.ll_about_us:
                Intent intent_about = new Intent(mActivity, WebViewActivity.class);
                intent_about.putExtra("url","https://honeybunch.app/about-us.html");
                intent_about.putExtra("title","About Us");
                startActivity(intent_about);
                break;
            default:
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            mActivity = (FragmentActivity) context;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri path = data.getData();

            updateProfileImage(path);

        }
    }

    private void updateProfileImage(Uri picturePath) {
    if (picturePath!=null) {
        try {
        File file = new File(getRealPathFromURI(picturePath));
        if (file.exists()){
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("picture", file.getName(), requestBody);

        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(Constants.getSharedPreferenceInt(mActivity, "userId", 0)));
        RequestBody SecurityTocken = RequestBody.create(MediaType.parse("multipart/form-data"), Constants.getSharedPreferenceString(mActivity, "securityToken", ""));
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
                            Constants.setSharedPreferenceString(mActivity, "userPic", response.body().getImage_url());

                            Picasso.get().load(response.body().getImage_url()).into(user_profile);

                            Toast.makeText(mActivity, "Update Profile " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(mActivity, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UpdateProfileImageModels> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();

            }
        });
    }
    }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(mActivity, "Select another image which is store in local ", Toast.LENGTH_SHORT).show();
        }
    }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = mActivity.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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