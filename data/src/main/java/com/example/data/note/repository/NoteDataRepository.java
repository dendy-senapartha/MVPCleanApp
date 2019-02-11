package com.example.data.note.repository;

import android.support.annotation.NonNull;

import com.example.data.Source;
import com.example.data.note.mapper.NoteRespondMapper;
import com.example.data.note.repository.source.NoteEntityData;
import com.example.data.note.repository.source.NoteEntityDataFactory;
import com.example.domain.note.NoteResult;
import com.example.domain.note.repository.NoteRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Observable;
/**
 * Note data repository. Used to passing request to retrieve or push data from specific usecase to data factory.
 *
 * **/
@Singleton
public class NoteDataRepository implements NoteRepository{

    private final NoteEntityDataFactory noteEntityDataFactory;

    private final NoteRespondMapper mapper;

    @Inject
    public NoteDataRepository(NoteEntityDataFactory noteEntityDataFactory,
                              NoteRespondMapper mapper)
    {
        this.noteEntityDataFactory = noteEntityDataFactory;
        this.mapper = mapper;
    }

    /*Instructs an ObservableSource to pass control to another ObservableSource rather than invoking onError if it encounters an error.
    * */
    protected <T> Observable<T> initializedRequest(Observable<T> observable) {
        return observable.onErrorResumeNext(observable);
    }

    private NoteEntityData createNoteDataOffline() {
        return noteEntityDataFactory.createData(Source.LOCAL);
    }

    private NoteEntityData createNoteDataNetwork() {
        return noteEntityDataFactory.createData(Source.NETWORK);
    }

    private NoteEntityData createNoteDataMock() {
        return noteEntityDataFactory.createData(Source.MOCK);
    }

    @Override
    public Observable<List<NoteResult>> getNotes() {
        return initializedRequest(createNoteDataNetwork().getNotes().map(mapper::transformFromList));
    }

    @Override
    public Observable<NoteResult> getNote(@NonNull String taskId) {
        return initializedRequest(createNoteDataNetwork().getNote(taskId).map(mapper::transform));
    }

    @Override
    public Observable<NoteResult> saveNote(@NonNull String mid, @NonNull String mTitle,
                                             @NonNull String mDescription) {
        return initializedRequest(createNoteDataNetwork().saveNote(mid, mTitle, mDescription).map(mapper::transform));
    }

    @Override
    public Observable<String> refreshNotes() {
        return null;
    }

    @Override
    public Observable<String> deleteAllNotes() {
        return null;
    }

    @Override
    public Observable<ArrayList<String>> deleteNotes(@NonNull ArrayList<String>  mid) {
        return initializedRequest(createNoteDataNetwork().deleteNote(mid));

    }
}
