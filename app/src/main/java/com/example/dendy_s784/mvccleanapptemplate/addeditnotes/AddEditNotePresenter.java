package com.example.dendy_s784.mvccleanapptemplate.addeditnotes;

import android.content.Context;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.mapper.NotesResultMapper;
import com.example.dendy_s784.mvccleanapptemplate.model.Note;
import com.example.domain.DefaultObserver;
import com.example.domain.note.NoteResult;
import com.example.domain.note.interactor.GetNote;
import com.example.domain.note.interactor.SaveNote;

import java.util.UUID;

import javax.inject.Inject;


public class AddEditNotePresenter implements AddEditNoteContract.Presenter
{
    private final Context context;
    private final AddEditNoteContract.View mAddNoteView;
    private final GetNote getNote;
    private final SaveNote saveNote;
    private final NotesResultMapper notesResultMapper;

    @Inject
    public AddEditNotePresenter(Context context, AddEditNoteContract.View mAddNoteView,
                                GetNote getNote, SaveNote saveNote, NotesResultMapper notesResultMapper) {
        this.context = context;
        this.mAddNoteView = mAddNoteView;
        this.getNote = getNote;
        this.notesResultMapper = notesResultMapper;
        this.saveNote = saveNote;
    }

    @Override
    public void saveNote(Note note) {
        if (note.getmTitle().isEmpty()) {
            mAddNoteView.onNoteSaveFailed(context.getResources().getString(R.string.nothing_to_save));
        } else {
            saveNote.execute(new DefaultObserver<NoteResult>(){
                @Override
                public void onNext(NoteResult noteResult) {
                    mAddNoteView.onNoteSaveSuccess(notesResultMapper.transform(noteResult));
                }
                @Override
                public void onError(Throwable e) {
                    mAddNoteView.onNoteSaveFailed(e.getMessage());
                }
            }, SaveNote.Params.forSaveNote(note.getmId(), note.getmTitle(), note.getmDescription()));
        }
    }

    @Override
    public void getNoteDetails(String mId) {
        if (mId.isEmpty() ) {

        } else {
            getNote.execute(new DefaultObserver<NoteResult>(){
                @Override
                public void onNext(NoteResult noteResult) {
                    mAddNoteView.onNoteDetails(notesResultMapper.transform(noteResult));
                }
                @Override
                public void onError(Throwable e) {

                }
            }, mId);
        }
    }

    @Override
    public void populateNote() {

    }

    @Override
    public boolean isDataMissing() {
        return false;
    }

    @Override
    public void onDestroy() {

    }
}
