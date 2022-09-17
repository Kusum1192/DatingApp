package com.honeybunch.app.fcm;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.honeybunch.app.R;
import com.honeybunch.app.activities.SplashActivity;
import com.honeybunch.app.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "testing";
    Bitmap image;
    NotificationCompat.Builder notificationBuilder;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        final Map<String, String> data = remoteMessage.getData();
        String mapData = data.get("OfferKey");

        if (remoteMessage == null)
            return;
        Uri imgurl  = remoteMessage.getNotification().getImageUrl();
        String imgs = String.valueOf(imgurl);
        image = getBitmapFromURL(imgs);
        Log.e(TAG, "onMessageReceived: TAG"+remoteMessage.getNotification().getTag() );
        sendNotification(remoteMessage.getNotification().getTag(), remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody(),image,mapData);

    }

    private void sendNotification(String tag, String title, String messageBody, Bitmap img, String mapData) {
        Intent intent = new Intent(this, SplashActivity.class);

        intent.putExtra("OfferKey",mapData);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);



        String channelID = getString(R.string.channel_id);

        if (tag.equalsIgnoreCase("image")) {
            notificationBuilder = new NotificationCompat.Builder(this, channelID)
                    .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(img))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this, channelID)
                    .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "Refreshed token: " + token);
        Constants.setSharedPreferenceString(MyFirebaseMessagingService.this,"token",token);

    }

}
