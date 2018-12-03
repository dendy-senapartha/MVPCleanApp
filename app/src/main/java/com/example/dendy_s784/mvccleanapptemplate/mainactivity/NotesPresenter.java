package com.example.dendy_s784.mvccleanapptemplate.mainactivity;

import android.content.Context;

import com.example.dendy_s784.mvccleanapptemplate.mapper.NotesResultMapper;
import com.example.dendy_s784.mvccleanapptemplate.model.Note;
import com.example.domain.DefaultObserver;
import com.example.domain.note.NoteResult;
import com.example.domain.note.interactor.DeleteNote;
import com.example.domain.note.interactor.GetNotes;

import java.util.List;

import javax.inject.Inject;

import static dagger.internal.Preconditions.checkNotNull;

public class NotesPresenter implements NotesContract.Presenter {

    private final Context context;

    private final NotesContract.View view;

    private final GetNotes getNotes;

    private final DeleteNote deleteNote;

    private final NotesResultMapper notesResultMapper;

    @Inject
    public NotesPresenter(Context context, NotesContract.View view,
                          GetNotes getNotes, DeleteNote deleteNote, NotesResultMapper notesResultMapper) {
        this.context = context;
        this.view = view;
        this.getNotes = getNotes;
        this.deleteNote = deleteNote;
        this.notesResultMapper = notesResultMapper;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadNotes() {
        // Simplification for sample: a network reload will be forced on first load.
        loadNotes(true);
    }

    @Override
    public void deleteNote(Note note) {
        deleteNote.execute(new DefaultObserver<String>() {
            @Override
            public void onNext(String result) {
                //todo add on action
                view.onNoteDelete(result);
            }

            @Override
            public void onError(Throwable e) {

            }
        }, DeleteNote.Params.forDeleteNote(note.getmId()));
    }

    /**
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadNotes(final boolean showLoadingUI) {
        if (showLoadingUI) {
            view.setLoadingIndicator(true);
        }

        getNotes.execute(new DefaultObserver<List<NoteResult>>() {
            @Override
            public void onNext(List<NoteResult> noteResult) {
                //System.out.printf("noteResult " + noteResult);
                processNotes(notesResultMapper.transformFromList(noteResult));
                if (showLoadingUI) {
                    view.setLoadingIndicator(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                view.showNoNotes();
                if (showLoadingUI) {
                    view.setLoadingIndicator(false);
                }
            }
        });

    }

    private void processNotes(List<Note> noteList) {
        if (noteList.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            view.showNoNotes();
        } else {
            // Show the list of tasks
            view.showNotes(noteList);
        }
    }
}
