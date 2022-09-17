package com.honeybunch.app.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.SignUpModel;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserLoginActivity extends AppCompatActivity {

    private static final String TAG = "testing_SignGActivity";
    private static final int RC_SIGN_IN = 101;
    FirebaseAuth mAuth;
    String personEmail;
    String socialToken;
    GoogleSignInClient mGoogleSignInClient;
    TextView privacypolicy, termcondition;
    ProgressDialog progressDialog;
    private InstallReferrerClient referrerClient;
    String personId;
    TextView signInBtn;
   // String versionName;
     //int versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        getRefrealCode();
        privacypolicy = findViewById(R.id.tvPrivacyPolicy);
        termcondition = findViewById(R.id.tvTermCondition);
        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://honeybunch.app/privacy-policy";
                webViewLoad(url, "Privacy Policy");
            }
        });
        termcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlcondition = " https://honeybunch.app/terms-conditions";
                webViewLoad(urlcondition, "Terms Of Services");
            }
        });
        mAuth = FirebaseAuth.getInstance();
        // Button listeners
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInBtn=findViewById(R.id.signInButton);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    /*  public void getIdThread1() {

          AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
              @Override
              protected String doInBackground(Void... params) {
                  AdvertisingIdClient.Info idInfo = null;
                  try {
                      idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());

                  } catch (GooglePlayServicesNotAvailableException e) {
                      e.printStackTrace();
                  } catch (GooglePlayServicesRepairableException e) {
                      e.printStackTrace();
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                  String advertId = null;
                  try {
                      advertId = idInfo.getId();
  //                    advertisingId = advertId;
                      Constants.setSharedPreferenceString(SignWithGplusActivity.this, "adverId", advertId);
  //                    Log.e("id", "getIdThread: "+advertId );
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                  return advertId;
              }

              @Override
              protected void onPostExecute(String advertId) {
  //                Toast.makeText(getApplicationContext(), "advg: "+advertId, Toast.LENGTH_SHORT).show();
              }
          };
          task.execute();
      }*/
    private void webViewLoad(String url, String title) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_web);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView tv_title = dialog.findViewById(R.id.title);
        TextView btn_cancel = dialog.findViewById(R.id.btn_cancel);
        TextView btn_accept = dialog.findViewById(R.id.btn_accept);
        WebView webview = dialog.findViewById(R.id.webview);
        tv_title.setText(title);

        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account!=null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
          // Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        progressDialog = new ProgressDialog(UserLoginActivity.this);
        progressDialog.setMessage(getString(R.string.loadingwait));
        progressDialog.show();
        progressDialog.setCancelable(false);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           Log.d(TAG, "signInWithCredential:success");
                           // progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                        } else {
                             Log.w(TAG, "signInWithCredential: Failed!", task.getException());
                            // If sign in fails, display a message to the user.
                            Toast.makeText(UserLoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                             updateUI(null);
                            progressDialog.dismiss();
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user) {
        String apilevel = String.valueOf(android.os.Build.VERSION.SDK_INT);
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        String devicename = android.os.Build.MODEL;
        //versionName = BuildConfig.VERSION_NAME;
        //versionCode = BuildConfig.VERSION_CODE;
       /* try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pInfo.versionName;
            versioncode = pInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(UserLoginActivity.this);
        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            personEmail = account.getEmail();
            socialToken = account.getIdToken();
            personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            Constants.setSharedPreferenceInt(UserLoginActivity.this, "versionCode", BuildConfig.VERSION_CODE);
            Constants.setSharedPreferenceString(UserLoginActivity.this, "versionName", BuildConfig.VERSION_NAME);
            Constants.setSharedPreferenceString(UserLoginActivity.this, "userEmail", personEmail);
            Constants.setSharedPreferenceString(UserLoginActivity.this, "userName", personName);
            Constants.setSharedPreferenceString(UserLoginActivity.this, "userPic", personPhoto.toString());
            userSignUp(apilevel, android_id, devicename, "google",personId, personEmail, socialToken, account.getDisplayName(),
                    account.getPhotoUrl(), Constants.getSharedPreferenceString(UserLoginActivity.this, "adverId", "")
                    , BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        }
    }
    private void userSignUp(String apilevel, String android_id, String devicename, String socialtype, String id, String email,
                            String socialToken, String displayName, Uri photoUrl, String adId, String version, int versionCode) {
        if (!((Activity) UserLoginActivity.this).isFinishing()) {
            progressDialog = new ProgressDialog(UserLoginActivity.this);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<SignUpModel> call = service.userSignUp(apilevel, android_id, devicename, socialtype, id, email, displayName, photoUrl,
                adId, version, versionCode, Constants.getSharedPreferenceString(UserLoginActivity.this, "token", ""),
                Constants.getSharedPreferenceString(UserLoginActivity.this, "utm_source", ""),
                Constants.getSharedPreferenceString(UserLoginActivity.this, "utm_medium", ""),
                Constants.getSharedPreferenceString(UserLoginActivity.this, "referrerUrl", ""), socialToken);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                dismissProgressDialog();
                try {
                    if (response != null) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                Constants.setSharedPreferenceInt(UserLoginActivity.this, "flag", 1);
                                Constants.setSharedPreferenceInt(UserLoginActivity.this, "userId", response.body().getUserId());
                                Constants.setSharedPreferenceString(UserLoginActivity.this, "securityToken", response.body().getSecurityToken());
                                Intent intent = new Intent(UserLoginActivity.this, UserProfileUpdateFirstActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(UserLoginActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(UserLoginActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(UserLoginActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "onResponse: " + e);
                }
            }
            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(UserLoginActivity.this, getString(R.string.systemmessage) + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
    }

    @Override
    public void onStop() {
        super.onStop();
        dismissProgressDialog();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void getRefrealCode() {
        referrerClient = InstallReferrerClient.newBuilder(UserLoginActivity.this).build();
        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        // Connection established.
                        try {
                            ReferrerDetails response = referrerClient.getInstallReferrer();
                            String referrer = response.getInstallReferrer();
                            long clickTimestamp = response.getReferrerClickTimestampSeconds();
                            long installTimestamp = response.getInstallBeginTimestampSeconds();
                            handleRefrellUrl(referrer);
                            referrerClient.endConnection();
                        } catch (RemoteException e) {
                            Log.e(TAG, "" + e.getMessage());
                        }
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        break;
                }
            }
            @Override
            public void onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }

    private void handleRefrellUrl(String referrerUrl) {
        if (referrerUrl != null) {
            String referrer = referrerUrl.substring(0, referrerUrl.length());
            String params[] = referrer.split("&");
            String utm_source = params[0].substring(params[0].lastIndexOf("=") + 1);
            String utm_medium = params[1].substring(params[1].lastIndexOf("=") + 1);
            Constants.setSharedPreferenceString(UserLoginActivity.this, "utm_source", utm_source);
            Constants.setSharedPreferenceString(UserLoginActivity.this, "utm_medium", utm_medium);
            Constants.setSharedPreferenceString(UserLoginActivity.this, "referrerUrl", referrerUrl);
        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
