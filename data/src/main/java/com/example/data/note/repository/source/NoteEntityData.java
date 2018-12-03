package com.example.data.note.repository.source;

import android.support.annotation.NonNull;

import com.example.data.note.repository.source.preference.response.NoteResponse;
import com.example.domain.note.NoteResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public interface NoteEntityData {

    Observable<Boolean> init(String key);
    /**
     * Refresh Notes
     *
     *
     */
    Observable<String> refreshNotes();

    Observable<List<NoteResponse>> getNotes();

    Observable<NoteResponse> saveNote(String mid, String mTitle, String mDescription);

    Observable<NoteResponse> getNote(String taskId);

    Observable<ArrayList<String>> deleteNote(ArrayList<String> mid);
}
