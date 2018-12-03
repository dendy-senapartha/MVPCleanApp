package com.example.domain.note;

public class NoteResult  {
    private final String mId;

    private final String mTitle;

    private final String mDescription;

    public NoteResult(String mId, String mTitle, String mDescription) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
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
}
