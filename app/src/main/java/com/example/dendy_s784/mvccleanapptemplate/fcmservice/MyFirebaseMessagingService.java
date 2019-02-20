package com.example.dendy_s784.mvccleanapptemplate.fcmservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.aplication.MVPCleanAppTemplate;
import com.example.dendy_s784.mvccleanapptemplate.mainactivity.MainActivity;
import com.example.dendy_s784.mvccleanapptemplate.notification.NotificationData;
import com.example.dendy_s784.mvccleanapptemplate.notification.NotificationGenerator;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/*
 * Created by dendy-prtha on 19/02/2019.
 * an example for Firebase messaging service
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static final int NOTIFICATION_ID = 888;
    private NotificationManagerCompat mNotificationManagerCompat;

    /*onMessageReceive is ONLY called when the app is in foreground, if the app if is background,
    the Google Services will take care of displaying the message.*/
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a notification payload.
        mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "From: " + remoteMessage.getFrom());

            Intent notifyIntent = new Intent(this, MainActivity.class);

            NotificationData data = new NotificationData();
            data.setmChannelId("NotifId");
            data.setmChannelName("notifChanelName");
            data.setmChannelDescription("notifChaneldescription");
            data.setmChannelImportance(NotificationManager.IMPORTANCE_HIGH);
            data.setmChannelEnableVibrate(true);
            data.setmChannelLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

            data.setmContentText(remoteMessage.getNotification().getBody());
            data.setmContentTitle(remoteMessage.getNotification().getTitle());
            data.setmPriority(NotificationCompat.PRIORITY_HIGH);

            //use notification to notify users
            Notification notification = new NotificationGenerator()
                    .generateBigTextStyleNotification(getApplicationContext(), data, notifyIntent, R.drawable.ic_done);

            mNotificationManagerCompat.notify(NOTIFICATION_ID, notification);
        }

    }
}
