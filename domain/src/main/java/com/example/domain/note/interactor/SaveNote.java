package com.example.domain.note.interactor;

import com.example.domain.PostExecutionThread;
import com.example.domain.ThreadExecutor;
import com.example.domain.UseCase;
import com.example.domain.note.NoteResult;
import com.example.domain.note.repository.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SaveNote extends UseCase<NoteResult,SaveNote.Params> {

    private final NoteRepository noteRepository;

    @Inject
    public SaveNote(ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread,
                    NoteRepository noteRepository) {
        super(threadExecutor, postExecutionThread);
        this.noteRepository = noteRepository;
    }

    @Override
    protected Observable<NoteResult> buildUseCaseObservable(Params params) {
        return noteRepository.saveNote(params.mId,params.mTitle,params.mDescription);
    }

    public static class Params {

        private final String mId;
        private final String mTitle;
        private final String mDescription;

        private Params(String mId, String mTitle, String mDescription) {
            this.mId =  mId;
            this.mTitle = mTitle;
            this.mDescription = mDescription;

        }

        public static Params forSaveNote(String mId, String mTitle, String mDescription) {
            return new Params(mId, mTitle,mDescription);
        }
    }
}
