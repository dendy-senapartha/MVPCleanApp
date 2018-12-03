package com.example.dendy_s784.mvccleanapptemplate.addeditnotes;

import com.example.dendy_s784.mvccleanapptemplate.base.AbstractContract;
import com.example.dendy_s784.mvccleanapptemplate.model.Note;

public interface AddEditNoteContract {

    interface View
    {
        void showNotesList();

        void onNoteSaveSuccess(Note note);

        void onNoteSaveFailed(String message) ;

        void onNoteDetails(Note note);
    }

    interface Presenter  extends AbstractContract.AbstractPresenter{

        void saveNote(Note note);

        void getNoteDetails(String mId);

        void populateNote();

        boolean isDataMissing();
    }
}
