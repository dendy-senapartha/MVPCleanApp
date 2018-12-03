package com.example.domain.note.interactor;

import com.example.domain.PostExecutionThread;
import com.example.domain.ThreadExecutor;
import com.example.domain.UseCase;
import com.example.domain.note.NoteResult;
import com.example.domain.note.repository.NoteRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DeleteNote extends UseCase<ArrayList<String>,DeleteNote.Params> {

    private final NoteRepository noteRepository;

    @Inject
    public DeleteNote(ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread,
                    NoteRepository noteRepository) {
        super(threadExecutor, postExecutionThread);
        this.noteRepository = noteRepository;
    }

    @Override
    protected Observable<ArrayList<String>> buildUseCaseObservable(DeleteNote.Params params) {
        return noteRepository.deleteNotes(params.mId);
    }

    public static class Params {

        private final ArrayList<String> mId;

        private Params(ArrayList<String>  mId) {
            this.mId =  mId;
        }

        public static Params forDeleteNote(ArrayList<String>  mId) {
            return new Params(mId);
        }
    }
}
