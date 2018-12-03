package com.example.data.note.repository.source.preference;

import android.text.TextUtils;

import com.example.data.base.UnInitializedSecuredPreferencesException;
import com.example.data.note.repository.source.NoteEntityData;
import com.example.data.note.repository.source.NotePreferences;
import com.example.data.note.repository.source.preference.response.NoteResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class PreferenceNoteEntityData implements NoteEntityData{

    private final NotePreferences notePreferences;

    public PreferenceNoteEntityData(NotePreferences notePreferences) {
        this.notePreferences = notePreferences;
    }

    @Override
    public Observable<Boolean> init(String key) {
        return initObservable(() -> {
            notePreferences.init(key);
            return true;
        });
    }

    @Override
    public Observable<String> refreshNotes() {
        return null;
    }

    @Override
    public Observable<List<NoteResponse>> getNotes() {
        return initObservable(() -> {
            return notePreferences.getNotes();
        });
    }

    @Override
    public Observable<NoteResponse> saveNote(String mid, String mTitle,String mDescription) {
        return initObservable(()->{
            //save the note and reselect it as confirmation of success
             notePreferences.saveNote(mid, mTitle,mDescription);
             return notePreferences.getNote(mid);
        });
    }

    @Override
    public Observable<NoteResponse> getNote(String taskId) {
        return initObservable(() -> {
            return notePreferences.getNote(taskId);
        });
    }

    @Override
    public Observable<ArrayList<String>> deleteNote(ArrayList<String> mid) {
        return initObservable(()->{
            return notePreferences.deleteNote(mid);
        });
    }

    private <T> Observable<T> initObservable(Callable<T> callable) {
        return initializedRequest(Observable.fromCallable(callable));
    }

    private <T> Observable<T> initializedRequest(Observable<T> observable) {
        return observable.onErrorResumeNext(initAndRetry(observable));
    }

    private <T> Function<Throwable, ? extends Observable<? extends T>>
    initAndRetry(final Observable<T> resumedObservable) {
        return (Function<Throwable, Observable<? extends T>>) throwable -> {
            if (throwable instanceof UnInitializedSecuredPreferencesException) {
                String userId = "MyNote";//CommonUtil.getUserId(securityGuardFacade, null);
                if (!TextUtils.isEmpty(userId)) {
                    return init(userId).concatMap(aBoolean -> resumedObservable);
                }
            }
            return Observable.error(throwable);
        };
    }
}
