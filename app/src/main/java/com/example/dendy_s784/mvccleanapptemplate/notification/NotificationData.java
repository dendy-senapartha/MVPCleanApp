package com.example.dendy_s784.mvccleanapptemplate.notification;

/*
 * Created by dendy-prtha on 19/02/2019.
 * Represent Stadard data needed for a notification
 */
public class NotificationData {
    // Standard notification values:
    private String mContentTitle;
    private String mContentText;
    private int mPriority;

    // Notification channel values (O and above):
    private String mChannelId;
    private CharSequence mChannelName;
    private String mChannelDescription;
    private int mChannelImportance;
    private boolean mChannelEnableVibrate;
    private int mChannelLockscreenVisibility;

    // Notification Standard notification get methods:
    public String getContentTitle() {
        return mContentTitle;
    }

    public String getContentText() {
        return mContentText;
    }

    public int getPriority() {
        return mPriority;
    }

    // Channel values (O and above) get methods:
    public String getChannelId() {
        return mChannelId;
    }

    public CharSequence getChannelName() {
        return mChannelName;
    }

    public String getChannelDescription() {
        return mChannelDescription;
    }

    public int getChannelImportance() {
        return mChannelImportance;
    }

    public boolean isChannelEnableVibrate() {
        return mChannelEnableVibrate;
    }

    public int getChannelLockscreenVisibility() {
        return mChannelLockscreenVisibility;
    }

    public void setmContentTitle(String mContentTitle) {
        this.mContentTitle = mContentTitle;
    }

    public void setmContentText(String mContentText) {
        this.mContentText = mContentText;
    }

    public void setmPriority(int mPriority) {
        this.mPriority = mPriority;
    }

    public void setmChannelId(String mChannelId) {
        this.mChannelId = mChannelId;
    }

    public void setmChannelName(CharSequence mChannelName) {
        this.mChannelName = mChannelName;
    }

    public void setmChannelDescription(String mChannelDescription) {
        this.mChannelDescription = mChannelDescription;
    }

    public void setmChannelImportance(int mChannelImportance) {
        this.mChannelImportance = mChannelImportance;
    }

    public void setmChannelEnableVibrate(boolean mChannelEnableVibrate) {
        this.mChannelEnableVibrate = mChannelEnableVibrate;
    }

    public void setmChannelLockscreenVisibility(int mChannelLockscreenVisibility) {
        this.mChannelLockscreenVisibility = mChannelLockscreenVisibility;
    }
}
