package com.honeybunch.app.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.MainActivity;
import com.honeybunch.app.R;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.AppOpenModel;
import com.honeybunch.app.models.UpdateProfileImageModels;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.CircleTransform;
import com.honeybunch.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadProfileActivity extends Activity implements View.OnClickListener {

    TextView tv_done;
    SpinKitView loadBar;
    ImageView im_first;
    ImageView im_first1;
    ImageView im_second;
    ImageView im_second2;
    ImageView im_third;
    ImageView im_third1;
    ImageView im_fourth;
    ImageView im_fourth1;
    ImageView im_fifth;
    ImageView im_fifth1;
    private static final int REQUEST_PERMISSION = 101;
    private static final int RESULT_IMAGE_ONE = 1001;
    private static final int RESULT_IMAGE_TWO = 1002;
    private static final int RESULT_IMAGE_THREE = 1003;
    private static final int RESULT_IMAGE_FOUR = 1004;
    private static final int RESULT_IMAGE_FIVE = 1005;

    ArrayList<Integer> counterImage = new ArrayList<>();
    int count = 0;
    //boolean allowPermisson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile);

        Constants.setSharedPreferenceInt(UploadProfileActivity.this, "flag", 3);

        loadBar = findViewById(R.id.spin_kit);
        im_first = findViewById(R.id.im_first);
        im_first1 = findViewById(R.id.im_first1);
        im_second = findViewById(R.id.im_second);
        im_second2 = findViewById(R.id.im_second2);
        im_third = findViewById(R.id.im_third);
        im_third1 = findViewById(R.id.im_third1);
        im_fourth = findViewById(R.id.im_fourth);
        im_fourth1 = findViewById(R.id.im_fourth1);
        im_fifth = findViewById(R.id.im_fifth);
        im_fifth1 = findViewById(R.id.im_fifth1);
        tv_done = findViewById(R.id.tv_done);
        tv_done.setOnClickListener(this);
        im_first.setOnClickListener(this);
        im_second.setOnClickListener(this);
        im_third.setOnClickListener(this);
        im_fourth.setOnClickListener(this);
        im_fifth.setOnClickListener(this);
    }

    @SuppressLint({"IntentReset", "NonConstantResourceId"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_done:
                if (counterImage.size() > 2 && counterImage.size() <= 5) {
                    appOpen();
                } else {
                    Toast.makeText(this, "Please upload minimum 3 images", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.im_first:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(UploadProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    return;
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "Select Image"), RESULT_IMAGE_ONE);
                }
                break;
            case R.id.im_second:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(UploadProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    return;
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "Select Image"), RESULT_IMAGE_TWO);
                }
                break;

            case R.id.im_third:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(UploadProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    return;
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "Select Image"), RESULT_IMAGE_THREE);
                }
                break;

            case R.id.im_fourth:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(UploadProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    return;
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "Select Image"), RESULT_IMAGE_FOUR);
                }
                break;

            case R.id.im_fifth:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(UploadProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    return;
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "Select Image"), RESULT_IMAGE_FIVE);
                }
                break;

            default:

        }
    }

    private void appOpen() {

       /* PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = pInfo.versionName;
        int versionCode = pInfo.versionCode;
*/
        Api getApiData = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<AppOpenModel> call = getApiData.appOpen(Constants.getSharedPreferenceInt(UploadProfileActivity.this, "userId", 0),
                Constants.getSharedPreferenceString(UploadProfileActivity.this, "securityToken", ""), BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        call.enqueue(new Callback<AppOpenModel>() {
            @Override
            public void onResponse(Call<AppOpenModel> call, Response<AppOpenModel> response) {

                if (response != null) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getStatus() == 200) {
                            Constants.setSharedPreferenceString(UploadProfileActivity.this, "forceUpdatePackage", response.body().getPackAge());
                            Constants.setSharedPreferenceInt(UploadProfileActivity.this, "msgCount", response.body().getMsgCount());
                            Constants.setSharedPreferenceInt(UploadProfileActivity.this, "activityCount", response.body().getCount().getTotal());
                            Constants.setSharedPreferenceString(UploadProfileActivity.this, "userAge", response.body().getUserAge());
                            Constants.setSharedPreferenceInt(UploadProfileActivity.this, "likeCount", response.body().getCount().getLikeCount());
                            Constants.setSharedPreferenceInt(UploadProfileActivity.this, "matchCount", response.body().getCount().getMatchCount());
                            Constants.setSharedPreferenceInt(UploadProfileActivity.this, "viewCount", response.body().getCount().getViewCount());

                            Intent intent = new Intent(UploadProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();


                        } else {
                            Toast.makeText(UploadProfileActivity.this, "System Message: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UploadProfileActivity.this, "System Message: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(UploadProfileActivity.this, "System Message: " + response.errorBody(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AppOpenModel> call, Throwable t) {

                Log.e("TAG", "onFailure: " + t);
//                Toast.makeText(UserProfileActivity.this, getString(R.string.systemmessage) + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfileImage(Uri picturePath, ImageView imageView, ImageView im_first) {
        counterImage.add(count);
        imageView.setVisibility(View.VISIBLE);
       im_first.setVisibility(View.GONE);
       try {
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
                               // countImage = true;

//                            Glide.with(UploadProfileActivity.this)
//                                    .load(response.body().getImage_url()) // Uri of the picture
//                                    .into(imageView);

                               Picasso.get().load(response.body().getImage_url())
                                       .placeholder(R.drawable.ic_placeholder)
                                       .error(R.drawable.ic_placeholder).transform(new CircleTransform())
                                       .into((imageView));


//                            Picasso.get().load(response.body().getImage_url()).into(profile_image);

                               Constants.setSharedPreferenceString(UploadProfileActivity.this, "userPic", response.body().getImage_url());

                               Toast.makeText(UploadProfileActivity.this, "Update Profile " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   } else {
                       Toast.makeText(UploadProfileActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
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

    private void dismissProgressDialog() {
        if (loadBar != null && loadBar.isShown()) {
            loadBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_IMAGE_ONE && resultCode == RESULT_OK && null != data.getData()) {
            Uri path = data.getData();
            if (path.getLastPathSegment()!=null){
               // String strPath= FileUtils.getPath(this,path);
            }
            updateProfileImage(path, im_first1,im_first);
            count++;
        }
        if (requestCode == RESULT_IMAGE_TWO && resultCode == RESULT_OK && null != data.getData()) {
            Uri path = data.getData();
            updateProfileImage(path, im_second2, im_second);
            count++;
        }

        if (requestCode == RESULT_IMAGE_THREE && resultCode == RESULT_OK && null != data.getData()) {
            Uri path = data.getData();
            updateProfileImage(path, im_third1, im_third);
            count++;
        }
        if (requestCode == RESULT_IMAGE_FOUR && resultCode == RESULT_OK && null != data.getData()) {
            Uri path = data.getData();
            updateProfileImage(path, im_fourth1, im_fourth);
            count++;
        }

        if (requestCode == RESULT_IMAGE_FIVE && resultCode == RESULT_OK && null != data.getData()) {
            Uri path = data.getData();
            updateProfileImage(path, im_fifth1, im_fifth);
            count++;
        }

    }

    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.

            } else {
                // User refused to grant permission.
            }
        }
    }
}