package com.example.domain.note.repository;

import android.support.annotation.NonNull;

import com.example.domain.note.NoteResult;

import java.util.List;

import io.reactivex.Observable;

public interface NoteRepository {

    Observable<List<NoteResult>> getNotes();

    Observable<NoteResult> getNote(@NonNull String mid);

    Observable<NoteResult> saveNote(@NonNull String mid, @NonNull String mTitle, @NonNull String mDescription );

    Observable<String> refreshNotes();

    Observable<String> deleteAllNotes();

    Observable<String> deleteNotes(@NonNull String taskId);
}
