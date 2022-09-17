package com.honeybunch.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.tabs.TabLayout;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.UpdateProfileImageModels;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPhotosActivity extends AppCompatActivity {

    ImageView iv_add_image;

    ImageView img_delete;
    SpinKitView loadBar;
    private static int RESULT_LOAD_IMAGE = 1;
    ViewPager mImageViewPager;
    TextView tv_image_count;
    TabLayout tabLayout;
    MyCustomViewPagerAdapter myCustomPagerAdapter;
    int currentPosition;
    LinearLayout rl_profile;

    List<UpdateProfileImageModels.Picture> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photos);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setDisplayShowHomeEnabled(true);

       /* getSupportActionBar().setTitle(Html.fromHtml("<font color='#000'>My Photos</font>"));
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);*/

/*
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.unselect_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
*/

        mImageViewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mImageViewPager, true);

        loadBar = findViewById(R.id.spin_kit);
        tv_image_count = findViewById(R.id.tv_image_count);
        img_delete = findViewById(R.id.img_delete);

        iv_add_image = findViewById(R.id.iv_add_image);
        rl_profile = findViewById(R.id.rl_profile);

        getProfileImage();

        iv_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });

        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteImage(currentPosition);
            }
        });

        rl_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPictures(currentPosition);
            }
        });


    }

    public void setPictures(int pictureId) {

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        Call<UpdateProfileImageModels> call = service.setUserImage(
                Constants.getSharedPreferenceInt(MyPhotosActivity.this, "userId", 0),
                Constants.getSharedPreferenceString(MyPhotosActivity.this, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, pictureId);


        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UpdateProfileImageModels>() {
            @Override
            public void onResponse(Call<UpdateProfileImageModels> call, Response<UpdateProfileImageModels> response) {
               dismissProgressDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        Constants.setSharedPreferenceString(MyPhotosActivity.this, "userPic", response.body().getImage_url());
                    }
                }

            }

            @Override
            public void onFailure(Call<UpdateProfileImageModels> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t);
                dismissProgressDialog();
            }
        });


    }

    private void deleteImage(int pictureid) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<UpdateProfileImageModels> call = service.removeProfileImage(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, pictureid);

        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UpdateProfileImageModels>() {
            @Override
            public void onResponse(Call<UpdateProfileImageModels> call, final Response<UpdateProfileImageModels> response) {
                dismissProgressDialog();

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            imageList = response.body().getPictures();

                            if (imageList.size() > 1) {
                                img_delete.setVisibility(View.VISIBLE);
                            } else {
                                img_delete.setVisibility(View.GONE);
                            }
                            myCustomPagerAdapter = new MyCustomViewPagerAdapter(MyPhotosActivity.this, imageList);
                            mImageViewPager.setAdapter(myCustomPagerAdapter);

                            mImageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                    tv_image_count.setText(mImageViewPager.getCurrentItem() + 1 + "/" + imageList.size());
                                    currentPosition = imageList.get(position).getId();
                                }

                                @Override
                                public void onPageSelected(int position) {

                                    tv_image_count.setText(mImageViewPager.getCurrentItem() + 1 + "/" + imageList.size());
                                    currentPosition = imageList.get(position).getId();
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });
                        }
                    }
                } else {
                    Toast.makeText(MyPhotosActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdateProfileImageModels> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();

            }
        });
    }


    private void getProfileImage() {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(Constants.getSharedPreferenceInt(this, "userId", 0)));
        RequestBody SecurityTocken = RequestBody.create(MediaType.parse("multipart/form-data"), Constants.getSharedPreferenceString(this, "securityToken", ""));
        RequestBody versionName = RequestBody.create(MediaType.parse("multipart/form-data"), BuildConfig.VERSION_NAME);
        RequestBody versionCode = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(BuildConfig.VERSION_CODE));
        RequestBody actionType = RequestBody.create(MediaType.parse("multipart/form-data"), "Get");


        Call<UpdateProfileImageModels> call = service.getProfileImage(userId, SecurityTocken, versionName, versionCode, actionType);

        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UpdateProfileImageModels>() {
            @Override
            public void onResponse(Call<UpdateProfileImageModels> call, final Response<UpdateProfileImageModels> response) {
                dismissProgressDialog();

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            imageList = response.body().getPictures();

                            if (imageList.size() > 1) {
                                img_delete.setVisibility(View.VISIBLE);
                            } else {
                                img_delete.setVisibility(View.GONE);
                            }

                            myCustomPagerAdapter = new MyCustomViewPagerAdapter(MyPhotosActivity.this, imageList);
                            mImageViewPager.setAdapter(myCustomPagerAdapter);

                            mImageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    //Log.e("TAG", "onPageScrolled:ITEM " + position);
                                    tv_image_count.setText(mImageViewPager.getCurrentItem() + 1 + "/" + imageList.size());
                                    currentPosition = imageList.get(position).getId();
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    //Log.e("TAG", "onPageSelected:ITEM " + position);
                                    tv_image_count.setText(mImageViewPager.getCurrentItem() + 1 + "/" + imageList.size());
                                    currentPosition = imageList.get(position).getId();
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });
                        }
                    }
                } else {
                    Toast.makeText(MyPhotosActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdateProfileImageModels> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();

            }
        });
    }

    private void updateProfileImage(Uri picturePath) {
    try {
        File file = new File(getRealPathFromURI(picturePath));

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("picture", file.getName(), requestBody);

        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(Constants.getSharedPreferenceInt(this, "userId", 0)));
        RequestBody SecurityTocken = RequestBody.create(MediaType.parse("multipart/form-data"), Constants.getSharedPreferenceString(this, "securityToken", ""));
        RequestBody versionName = RequestBody.create(MediaType.parse("multipart/form-data"), BuildConfig.VERSION_NAME);
        RequestBody versionCode = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(BuildConfig.VERSION_CODE));
        RequestBody actionType = RequestBody.create(MediaType.parse("multipart/form-data"), "Post");


        Call<UpdateProfileImageModels> call = service.uploadImage(userId, SecurityTocken, versionName, versionCode, parts, actionType);

        loadBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UpdateProfileImageModels>() {
            @Override
            public void onResponse(Call<UpdateProfileImageModels> call, final Response<UpdateProfileImageModels> response) {
                dismissProgressDialog();

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {

                            imageList = response.body().getPictures();

                            if (imageList.size() > 1) {
                                img_delete.setVisibility(View.VISIBLE);
                            } else {
                                img_delete.setVisibility(View.GONE);
                            }
                            myCustomPagerAdapter = new MyCustomViewPagerAdapter(MyPhotosActivity.this, imageList);
                            mImageViewPager.setAdapter(myCustomPagerAdapter);
                            mImageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    tv_image_count.setText(mImageViewPager.getCurrentItem() + 1 + "/" + imageList.size());
                                    currentPosition = imageList.get(position).getId();
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    //Log.e("TAG", "onPageSelected:ITEM " + position);
                                    tv_image_count.setText(mImageViewPager.getCurrentItem() + 1 + "/" + imageList.size());
                                    currentPosition = imageList.get(position).getId();
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });


                        }
                    }
                } else {
                    Toast.makeText(MyPhotosActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdateProfileImageModels> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();

            }
        });
    }catch (Exception e){
        e.printStackTrace();
        Toast.makeText(this, "This image can't be loaded", Toast.LENGTH_SHORT).show();
    }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri path = data.getData();
            updateProfileImage(path);

        }
    }


    public static class MyCustomViewPagerAdapter extends PagerAdapter {

        private List<UpdateProfileImageModels.Picture> images;
        private LayoutInflater inflater;
        private Context context;

        public MyCustomViewPagerAdapter(Context context, List<UpdateProfileImageModels.Picture> images) {
            this.context = context;
            this.images = images;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View myImageLayout = inflater.inflate(R.layout.view_image_viewpager_full, view, false);
            ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.image);

            Picasso.get().load(images.get(position).getUrl())
                    .into(myImage);

            view.addView(myImageLayout, 0);
            //listening to image click


            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}