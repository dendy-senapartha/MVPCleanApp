package com.example.data.note;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

public class NoteEntity {

    private String mId;

    private String mTitle;

    private String mDescription;

    public NoteEntity()
    {

    }

    public NoteEntity(@Nullable String title, @Nullable String description) {
        this(UUID.randomUUID().toString(), title, description);
    }

    public NoteEntity(@NonNull String mId, @Nullable String mTitle, @Nullable String mDescription) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    @NonNull
    public void setId(String mId) {
        this.mId = mId;
    }

    @Nullable
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Nullable
    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

}
