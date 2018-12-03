package com.example.domain.note.interactor;

import com.example.domain.PostExecutionThread;
import com.example.domain.ThreadExecutor;
import com.example.domain.UseCase;
import com.example.domain.note.NoteResult;
import com.example.domain.note.repository.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetNote extends UseCase<NoteResult, String> {

    private final NoteRepository noteRepository;

    @Inject
    public GetNote(ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread,
                    NoteRepository noteRepository) {
        super(threadExecutor,postExecutionThread);
        this.noteRepository = noteRepository;
    }

    @Override
    protected Observable<NoteResult> buildUseCaseObservable(String mid) {
        return noteRepository.getNote(mid);
    }
}
