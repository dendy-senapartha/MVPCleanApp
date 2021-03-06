package com.example.dendy_s784.mvccleanapptemplate.mainactivity;

import android.support.annotation.NonNull;

import com.example.dendy_s784.mvccleanapptemplate.base.AbstractContract;
import com.example.dendy_s784.mvccleanapptemplate.model.Note;

import java.util.ArrayList;
import java.util.List;

public interface NotesContract {

    interface View{
        void showNotes(List<Note> tasks);

        void showAddNote();

        void showNoteDetails(String taskId);

        void setLoadingIndicator(boolean active);

        void showNoNotes();

        void onNoteDelete(ArrayList<String> mid);

        void onSignOutSuccess();
    }

    interface Presenter extends AbstractContract.AbstractPresenter{
        void loadNotes();
        void deleteNote(ArrayList<Note> notes);

        void signOut();
    }
}
