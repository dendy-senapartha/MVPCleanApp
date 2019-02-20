package com.example.dendy_s784.mvccleanapptemplate.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.*;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import io.reactivex.annotations.Nullable;

/*
 * Created by dendy-prtha on 19/02/2019.
 * Notification Generator. Sample taken from https://github.com/googlesamples/android-Notifications
 */
public class NotificationGenerator {
    public static final String TAG = "NotificationGenerator";

    public Notification generateBigTextStyleNotification(Context context,
                                                         NotificationData notificationData,
                                                         Intent notifyIntentClass,
                                                         int smallIcon) {
        return generateBigTextStyleNotification(context, notificationData, notifyIntentClass, smallIcon,
                null, null, -1, null, null, -1,
                null);

    }

    /*
     * Generates a BIG_TEXT_STYLE Notification that supports both phone/tablet and wear. For devices
     * on API level 16 (4.1.x - Jelly Bean) and after, displays BIG_TEXT_STYLE. Otherwise, displays
     * a basic notification.
     */
    public Notification generateBigTextStyleNotification(Context context,
                                                         NotificationData notificationData,
                                                         Intent notifyIntentClass,
                                                         int smallIcon,
                                                         @Nullable Bitmap largeIcon,
                                                         @Nullable Intent optOneIntent,
                                                         int optOneIcon,
                                                         @Nullable String optOneTitle,
                                                         @Nullable Intent optTwoIntent,
                                                         int optTwoIcon,
                                                         @Nullable String optTwoTitle) {
        Log.d(TAG, "generateBigTextStyleNotification()");
        // Main steps for building a BIG_TEXT_STYLE notification:
        //      0. Get your data
        //      1. Create/Retrieve Notification Channel for O and beyond devices (26+)
        //      2. Build the BIG_TEXT_STYLE
        //      3. Set up main Intent for notification
        //      4. Create additional Actions for the Notification
        //      5. Build and issue the notification

        // 0. Get your data (everything unique per Notification).
        NotificationData data = notificationData;

        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        String notificationChannelId = NotificationUtil.createNotificationChannel(context, notificationData);

        // 2. Build the BIG_TEXT_STYLE.
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
                // Overrides ContentText in the big form of the template.
                .bigText(data.getContentText())
                // Overrides ContentTitle in the big form of the template.
                .setBigContentTitle(data.getContentTitle())
                // Summary line after the detail section in the big form of the template.
                // Note: To improve readability, don't overload the user with info. If Summary Text
                // doesn't add critical information, you should skip it.
                .setSummaryText(data.getContentTitle());

        // 3. Set up main Intent for notification.
        // When creating your Intent, you need to take into account the back state, i.e., what
        // happens after your Activity launches and the user presses the back button.

        // There are two options:
        //      1. Regular activity - You're starting an Activity that's part of the application's
        //      normal workflow.

        //      2. Special activity - The user only sees this Activity if it's started from a
        //      notification. In a sense, the Activity extends the notification by providing
        //      information that would be hard to display in the notification itself.

        // For the BIG_TEXT_STYLE notification, we will consider the activity launched by the main
        // Intent as a special activity, so we will follow option 2.

        // For an example of option 1, check either the MESSAGING_STYLE or BIG_PICTURE_STYLE
        // examples.

        // For more information, check out our dev article:
        // https://developer.android.com/training/notify-user/navigation.html

        // Sets the Activity to start in a new, empty task
        notifyIntentClass.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        notifyIntentClass,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        // 4. Create additional Actions (Intents) for the Notification.
        // In our case, we create two additional actions: a Snooze action and a Dismiss action.
        // OptOne Action.
        if (optOneIntent != null) {
            PendingIntent optOnePendingIntent = PendingIntent.getService(context, 0, optOneIntent, 0);
            NotificationCompat.Action snoozeAction =
                    new NotificationCompat.Action.Builder(optOneIcon,
                            optOneTitle,
                            optOnePendingIntent)
                            .build();
        }
        // OptTwo Action.
        if (optTwoIntent != null) {
            PendingIntent optTwoPendingIntent = PendingIntent.getService(context, 0, optTwoIntent, 0);
            NotificationCompat.Action dismissAction =
                    new NotificationCompat.Action.Builder(
                            optTwoIcon,
                            optTwoTitle,
                            optTwoPendingIntent)
                            .build();
        }

        // 5. Build and issue the notification.
        // Because we want this to be a new notification (not updating a previous notification), we
        // create a new Builder. Later, we use the same global builder to get back the notification
        // we built here for the snooze action, that is, canceling the notification and relaunching
        // it several seconds later.
        // Notification Channel Id is ignored for Android pre O (26).
        NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(context, notificationChannelId);

        // BIG_TEXT_STYLE sets title and content for API 16 (4.1 and after).
        notificationCompatBuilder.setStyle(bigTextStyle);
        // Title for API <16 (4.0 and below) devices.
        notificationCompatBuilder.setContentTitle(data.getContentTitle());
        // Content for API <24 (7.0 and below) devices.
        notificationCompatBuilder.setContentText(data.getContentText());
        notificationCompatBuilder.setSmallIcon(smallIcon);
        if (largeIcon != null) {
            notificationCompatBuilder.setLargeIcon(largeIcon);
        }
        notificationCompatBuilder.setContentIntent(notifyPendingIntent);
        notificationCompatBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        // SIDE NOTE: Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
        // devices and all Wear devices. If you have more than one notification and
        // you prefer a different summary notification, set a group key and create a
        // summary notification via
        // .setGroupSummary(true)
        // .setGroup(GROUP_KEY_YOUR_NAME_HERE)
        notificationCompatBuilder.setCategory(Notification.CATEGORY_REMINDER);
        // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
        // 'importance' which is set in the NotificationChannel. The integers representing
        // 'priority' are different from 'importance', so make sure you don't mix them.
        notificationCompatBuilder.setPriority(data.getPriority());

        // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
        // visibility is set in the NotificationChannel.
        notificationCompatBuilder.setVisibility(data.getChannelLockscreenVisibility());

        Notification notification = notificationCompatBuilder.build();
        return notification;
    }
}
