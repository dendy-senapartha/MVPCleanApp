package com.example.dendy_s784.mvccleanapptemplate.addeditnotes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AddEditNoteActivity extends BaseActivity {

    public static final int REQUEST_ADD_EDIT_NOTE = 1;

    AddEditNoteFragment addEditNoteFragment;

    @BindView(R.id.fab_edit_note_done)
    FloatingActionButton floatingActionButton;

    String noteIdForNoteDetail = null;

    @Override
    public int getLayout() {
        return R.layout.activity_addeditnote;
    }

    @Override
    public void init() {
        addEditNoteFragment = (AddEditNoteFragment) this.getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (addEditNoteFragment == null) {
            //create fragment
            addEditNoteFragment = AddEditNoteFragment.newInstance();
            this.getSupportFragmentManager().beginTransaction().
                    add(R.id.contentFrame, addEditNoteFragment).commit();
        }
        if (getIntent().hasExtra(AddEditNoteFragment.ARGUMENT_DETAILS_NOTE_ID)) {
            String noteId = getIntent().getStringExtra(AddEditNoteFragment.ARGUMENT_DETAILS_NOTE_ID);
            setToolbarTitle(noteId);
            this.noteIdForNoteDetail = noteId;
            Bundle bundle = new Bundle();
            bundle.putString(AddEditNoteFragment.ARGUMENT_DETAILS_NOTE_ID, noteId);
            addEditNoteFragment.setArguments(bundle);
        }
    }

    @Override
    protected void configToolbar() {
        setTitle(R.string.new_note);
        setMenuLeftButton(getResources().getString(R.string.menu_back));
    }

    private void setToolbarTitle(@Nullable String taskId) {
        if (taskId == null) {
            setTitle(R.string.new_note);
        } else {
            setTitle(R.string.edit_note);
        }
    }

    @Override
    public void onClickLeftMenuButton(View view) {
        onBackPressed();
    }

    @OnClick(R.id.fab_edit_note_done)
    public void submitNote(View view) {
        addEditNoteFragment.saveNote(noteIdForNoteDetail);
    }
}
