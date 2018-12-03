package com.example.dendy_s784.mvccleanapptemplate.model;

import android.support.annotation.Nullable;

import java.util.UUID;

public class Note {
    private  String mId;

    private  String mTitle;

    private  String mDescription;

    public Note(String mId, String mTitle, String mDescription) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public Note(String mTitle, String mDescription) {
        this.mId = UUID.randomUUID().toString();
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public boolean isEmpty() {
        return mTitle.isEmpty() &&
                mDescription.isEmpty();
    }

    public String getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    @Nullable
    public String getTitleForList() {
        if (mTitle!=null) {
            return mTitle;
        } else {
            return mDescription;
        }
    }
}
