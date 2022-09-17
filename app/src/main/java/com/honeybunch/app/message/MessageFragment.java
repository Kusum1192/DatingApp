package com.honeybunch.app.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.activities.ChatActivity;
import com.honeybunch.app.adapters.MessageChatAdapter;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.MessageChatModels;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessageFragment extends Fragment {

    private MessageViewModel messageViewModel;

    FragmentActivity mActivity;
    RecyclerView recyclerView;
    List<MessageChatModels.ConversationArray> messageChatModelArrayList;
    SpinKitView loadBar;
    ConstraintLayout no_chat;
    View supportLayout;
    private static int RESULT_LOAD_CHAT = 1006;
    MessageChatAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_message, container, false);
        loadBar = root.findViewById(R.id.spin_kit);
        supportLayout = root.findViewById(R.id.supportLayout);
        recyclerView = root.findViewById(R.id.recylerview_message);
        no_chat = root.findViewById(R.id.no_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        getMessageTab(mActivity);

        return root;
    }

    private void getMessageTab(Context context) {
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<MessageChatModels> call = service.getMessageList(Constants.getSharedPreferenceInt(context, "userId", 0),
                Constants.getSharedPreferenceString(context, "securityToken", ""),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);


        call.enqueue(new Callback<MessageChatModels>() {
            @Override
            public void onResponse(Call<MessageChatModels> call, final Response<MessageChatModels> response) {
                dismissProgressDialog();

                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            messageChatModelArrayList = response.body().getConversationArray();
                            Constants.getSharedPreferenceInt(mActivity, "msgCount", response.body().getUnreadCount());

                            if(messageChatModelArrayList.size() > 0){
                                adapter = new MessageChatAdapter(messageChatModelArrayList, mActivity, new MessageChatAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(MessageChatModels.ConversationArray Offer, int adapterPosition) {

                                        Intent intent = new Intent(mActivity, ChatActivity.class);
                                        intent.putExtra("conversationId",messageChatModelArrayList.get(adapterPosition).getConversationId());
                                        intent.putExtra("recipientName",messageChatModelArrayList.get(adapterPosition).getChatUserName());
                                        intent.putExtra("recipientAge",messageChatModelArrayList.get(adapterPosition).getRecipientAge());
                                        intent.putExtra("recipientImage",messageChatModelArrayList.get(adapterPosition).getImageUrl());
                                        startActivityForResult(intent,RESULT_LOAD_CHAT);
                                    }
                                });
                                recyclerView.setAdapter(adapter);
                            }
                            else {
                                no_chat.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } else {
                    dismissProgressDialog();
                    supportLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageChatModels> call, Throwable t) {
                // Log error here since request failed
                dismissProgressDialog();
                supportLayout.setVisibility(View.VISIBLE);
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
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            mActivity = (FragmentActivity) context;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_CHAT){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }
}