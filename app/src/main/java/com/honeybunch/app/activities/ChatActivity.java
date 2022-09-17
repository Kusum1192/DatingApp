package com.honeybunch.app.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.adapters.One2OneChatAdapter;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.BlockUserModels;
import com.honeybunch.app.models.DeleteChatModel;
import com.honeybunch.app.models.OnetoOneChatModel;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    List<OnetoOneChatModel.MessageArray> onetoOneChatModelArrayList;
    TextView recevierName;
    TextView send_mssage;
    String rName;
    String rUrl;
    String rAge;
    ImageView recevierpic;
    ImageView more;
    View sheetView;
    LinearLayout seeProfile;
    LinearLayout blockUser;
    LinearLayout reportAbuse;
    LinearLayout deleteChat;
    LinearLayout cancel;
    RoundedBottomSheetDialog mBottomSheetDialog;
    ImageView iv_backarrow;
    SpinKitView loadBar;
    EditText et_send_msg;
    int conversationId;
    RelativeLayout chat_rl;
    String message;
    int recipientId;
    private AlertDialog.Builder alertDialog;
    TextView blockStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recevierName = findViewById(R.id.receviername);
        chat_rl=findViewById(R.id.chat_rl);
        blockStatus=findViewById(R.id.blockStatus);
        et_send_msg = findViewById(R.id.et_send_msg);
        recevierpic = findViewById(R.id.recevierpic);
        more = findViewById(R.id.more);
        iv_backarrow = findViewById(R.id.iv_backarrow);
        iv_backarrow.setOnClickListener(this);
        mBottomSheetDialog = new RoundedBottomSheetDialog(this);
        loadBar = findViewById(R.id.spin_kit);
        send_mssage = findViewById(R.id.send_mssage);
        send_mssage.setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);


        Intent intent = getIntent();
        if (intent != null) {
            conversationId = intent.getIntExtra("conversationId",0);
            rName = intent.getStringExtra("recipientName");
            rAge = intent.getStringExtra("recipientAge");
            rUrl = intent.getStringExtra("recipientImage");
            Log.e("TAG", "onCreate:CHATURL "+rUrl );
            Log.e("TAG", "onCreate:CHATconversationId "+conversationId );
            recevierName.setText(rName+", "+rAge);

            Glide.with(this).load(rUrl).into(recevierpic);

            listChat(this,conversationId,rUrl);

        }
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sampleCustomView();
            }
        });

        et_send_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0 ) {
                    send_mssage.setVisibility(View.VISIBLE);
                    message = s.toString();

                }
                  else
                      send_mssage.setVisibility(View.GONE);

            }
        });
    }

    private void listChat(Context context, int conversationId, String rUrl) {

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        Call<OnetoOneChatModel> call = service.viewChat(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE,conversationId,"Get");

        call.enqueue(new Callback<OnetoOneChatModel>() {
            @Override
            public void onResponse(Call<OnetoOneChatModel> call, final Response<OnetoOneChatModel> response) {
                OnetoOneChatModel data= response.body();
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
//                            Log.e("TAG", "onResponse: "+response.body() );
                            onetoOneChatModelArrayList = response.body().getMessageArray();
                            recipientId = response.body().getRecipientId();
                            if (data.isBlocked()){
                                chat_rl.setVisibility(View.GONE);
                                blockStatus.setVisibility(View.VISIBLE);
                            }else{
                                chat_rl.setVisibility(View.VISIBLE);
                            }
                            if(onetoOneChatModelArrayList.size() > 0){
                                One2OneChatAdapter adapter = new One2OneChatAdapter(onetoOneChatModelArrayList, rUrl, context);
                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                                recyclerView.setLayoutManager(mLayoutManager);
                                ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
                                recyclerView.setAdapter(adapter);
                            }

                        }
                    }
                } else {
                    Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OnetoOneChatModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
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
    public void sampleCustomView() {
        sheetView = getLayoutInflater().inflate(R.layout.bottomdialog_layout, null);
        seeProfile = sheetView.findViewById(R.id.ll_see_profile);
        blockUser = sheetView.findViewById(R.id.ll_block_user);
        reportAbuse = sheetView.findViewById(R.id.ll_reports_abuse);
        deleteChat = sheetView.findViewById(R.id.ll_delete_chat);
        cancel = sheetView.findViewById(R.id.ll_cancel);
        seeProfile.setOnClickListener(this);
        blockUser.setOnClickListener(this);
        reportAbuse.setOnClickListener(this);
        deleteChat.setOnClickListener(this);
        cancel.setOnClickListener(this);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }
    public void blockUserCustomView() {
        sheetView = getLayoutInflater().inflate(R.layout.bottom_block_user_layout, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        TextView block_user = sheetView.findViewById(R.id.block_user);
        TextView cancel = sheetView.findViewById(R.id.cancel);
        block_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blockUser(recipientId);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });

    }
    public void reportAbuseCustomView() {
        sheetView = getLayoutInflater().inflate(R.layout.bottom_report_abuse_layout, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        TextView tv_report = sheetView.findViewById(R.id.tv_report);
        EditText et_report = sheetView.findViewById(R.id.et_report);
        TextView cancel = sheetView.findViewById(R.id.cancel);

        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(et_report.getText())) {
                    et_report.setError("Enter Reason");
                    et_report.requestFocus();
                }
               else{
                    reportUser(recipientId,et_report.getText().toString());
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
    }
    public void deleteChatCustomView() {
        sheetView = getLayoutInflater().inflate(R.layout.bottom_delete_layout, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        TextView delete_chat = sheetView.findViewById(R.id.delete_chat);
        TextView cancel = sheetView.findViewById(R.id.cancel_delete_chat);
        delete_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMessage(conversationId);
                mBottomSheetDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
    }
    private void deleteMessage(int conversationId) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<DeleteChatModel> call=service.deleteUserChat(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken",""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE,conversationId);

        call.enqueue(new Callback<DeleteChatModel>() {
            @Override
            public void onResponse(Call<DeleteChatModel> call, Response<DeleteChatModel> response) {
                DeleteChatModel data= response.body();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            Picasso.get().load(data.getRecipientImage()).into(recevierpic);
                            recevierName.setText(data.getRecipientName());
                            send_mssage.setText(data.getMessage());
                            data.getConversationId();
                            data.setRecipientId(recipientId);
                            Toast.makeText(ChatActivity.this, "Deleted"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            mBottomSheetDialog.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(ChatActivity.this, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
        }
            @Override
            public void onFailure(Call<DeleteChatModel> call, Throwable t) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_see_profile:
               mBottomSheetDialog.dismiss();
                Intent intentProfile = new Intent(ChatActivity.this, ViewedMeProfileActivity.class);
                intentProfile.putExtra("viewProfileId",recipientId);
                intentProfile.putExtra("viewProfile",true);
                startActivity(intentProfile);
                break;
            case R.id.ll_block_user:
                blockUserCustomView();
                break;
            case R.id.ll_reports_abuse:
                reportAbuseCustomView();
                break;
            case R.id.ll_delete_chat:
                deleteChatCustomView();
                break;
            case R.id.ll_cancel:
                mBottomSheetDialog.dismiss();
                break;
            case R.id.iv_backarrow:
                 finish();
                break;
            case R.id.send_mssage:
                 sendMessage(conversationId,message);
                et_send_msg.setText("");
                break;
            default:
        }
    }
    private void sendMessage(int conversationId,String message) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<OnetoOneChatModel> call = service.sendMessage(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE,conversationId,"Post",message);
        call.enqueue(new Callback<OnetoOneChatModel>() {
            @Override
            public void onResponse(Call<OnetoOneChatModel> call, final Response<OnetoOneChatModel> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            onetoOneChatModelArrayList = response.body().getMessageArray();
                            if(onetoOneChatModelArrayList.size() > 0){
                                One2OneChatAdapter adapter = new One2OneChatAdapter(onetoOneChatModelArrayList, rUrl, ChatActivity.this);
                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChatActivity.this);
                                 recyclerView.setLayoutManager(mLayoutManager);
                                ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    }
                } else {
                    Toast.makeText(ChatActivity.this, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<OnetoOneChatModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });
    }
    private void blockUser(int blockedId) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<BlockUserModels> call = service.blockUser(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE,blockedId);

        loadBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<BlockUserModels>() {
            @Override
            public void onResponse(Call<BlockUserModels> call, final Response<BlockUserModels> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {

                            Toast.makeText(ChatActivity.this, "Block "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            mBottomSheetDialog.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(ChatActivity.this, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BlockUserModels> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });
    }
    private void reportUser(long reportedId, String reportTxt) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<BlockUserModels> call = service.reportUser(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE,reportedId,reportTxt);

        loadBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<BlockUserModels>() {
            @Override
            public void onResponse(Call<BlockUserModels> call, final Response<BlockUserModels> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {

                            Toast.makeText(ChatActivity.this, "Block "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            mBottomSheetDialog.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(ChatActivity.this, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BlockUserModels> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_OK);
        Intent intent = new Intent();
        intent.putExtra("id","value");
        setResult(RESULT_OK, intent);
        finish();
    }
}