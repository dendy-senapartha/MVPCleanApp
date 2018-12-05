package com.example.domain.note.interactor;

import com.example.domain.PostExecutionThread;
import com.example.domain.ThreadExecutor;
import com.example.domain.UseCase;
import com.example.domain.note.NoteResult;
import com.example.domain.note.repository.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
/**
 * Use case to get notes from repository.
 * **/
public class GetNotes extends UseCase<List<NoteResult>, Void>{

    private final NoteRepository noteRepository;

    @Inject
    public GetNotes(ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread,
                    NoteRepository noteRepository) {
        super(threadExecutor,postExecutionThread);
        this.noteRepository = noteRepository;
    }

    @Override
    protected Observable<List<NoteResult>> buildUseCaseObservable(Void aVoid) {
        return noteRepository.getNotes();
    }
}
