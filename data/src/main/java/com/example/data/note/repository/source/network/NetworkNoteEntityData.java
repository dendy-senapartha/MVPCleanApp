package com.example.data.note.repository.source.network;

import com.example.data.note.NoteEntity;
import com.example.data.note.repository.source.NoteEntityData;
import com.example.data.note.repository.source.preference.response.NoteResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.Observable;

/*
 * Created by dendy-prtha on 06/02/2019.
 * EntityData for network source repository
 */
public class NetworkNoteEntityData implements NoteEntityData {

    private final NoteNetwork noteNetwork;

    public NetworkNoteEntityData(NoteNetwork noteNetwork) {
        this.noteNetwork = noteNetwork;
    }

    @Override
    public Observable<Boolean> init(String key) {

        return initObservable(() -> {
            noteNetwork.init();
            return true;
        });
    }

    @Override
    public Observable<String> refreshNotes() {
        return null;
    }

    @Override
    public Observable<List<NoteResponse>> getNotes() {
        return initializedRequest(Observable.create(emitter -> noteNetwork.getNotes(emitter)));
    }

    @Override
    public Observable<NoteResponse> saveNote(String mid, String mTitle, String mDescription) {
        return initializedRequest(Observable.create(
                emitter -> noteNetwork.saveNote(emitter, new NoteEntity(mid, mTitle, mDescription))
        ));
    }

    @Override
    public Observable<NoteResponse> getNote(String taskId) {
        return initializedRequest(Observable.create(emitter -> noteNetwork.getNote(emitter, taskId)));
    }

    @Override
    public Observable<ArrayList<String>> deleteNote(ArrayList<String> mid) {
        return initializedRequest(Observable.create(emitter -> noteNetwork.deleteNote(emitter, mid)));
    }

    private <T> Observable<T> initObservable(Callable<T> callable) {
        return initializedRequest(Observable.fromCallable(callable));
    }

    private <T> Observable<T> initializedRequest(Observable<T> observable) {
        return observable.onErrorResumeNext(initAndRetry(observable));
    }

    private <T> Function<Throwable, ? extends Observable<? extends T>> initAndRetry(final Observable<T> resumedObservable) {
        return (Function<Throwable, Observable<? extends T>>) throwable -> {
            if (throwable instanceof NullPointerException) {
                return init(null).concatMap(aBoolean -> resumedObservable);
            }
            return Observable.error(throwable);
        };
    }
}
