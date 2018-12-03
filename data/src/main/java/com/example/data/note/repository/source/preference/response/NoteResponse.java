package com.example.data.note.repository.source.preference.response;

public class NoteResponse {

    public String mId;

    public String mTitle;

    public String mDescription;

    public NoteResponse() {
    }

    public NoteResponse(String mId, String mTitle, String mDescription) {
        this.mId=mId;
        this.mTitle=mTitle;
        this.mDescription=mDescription;
    }
}
