package com.example.dendy_s784.mvccleanapptemplate.locationservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

/*
 * Created by dendy-prtha on 22/02/2019.
 * activity recognition intent receiver example
 */
public class ActivityRecognitionIntentService extends IntentService {

    public static final String BROADCAST_DETECTED_ACTIVITY = "activity_intent";

    public static final long DETECTION_INTERVAL_IN_MILLISECONDS = 30 * 1000;

    public static final int CONFIDENCE = 70;

    protected static final String TAG = ActivityRecognitionIntentService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ActivityRecognitionIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

        // Get the list of the probable activities associated with the current state of the
        // device. Each activity is associated with a confidence level, which is an int between
        // 0 and 100.
        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();

        for (DetectedActivity activity : detectedActivities) {
            Log.e(TAG, "Detected activity: " + activity.getType() + ", " + activity.getConfidence());
            broadcastActivity(activity);
        }
    }

    private void broadcastActivity(DetectedActivity activity) {
        Intent intent = new Intent(BROADCAST_DETECTED_ACTIVITY);
        intent.putExtra("type", activity.getType());
        intent.putExtra("confidence", activity.getConfidence());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


}
