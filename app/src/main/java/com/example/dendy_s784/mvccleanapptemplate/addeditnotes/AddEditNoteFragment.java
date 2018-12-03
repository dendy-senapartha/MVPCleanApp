package com.example.dendy_s784.mvccleanapptemplate.addeditnotes;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.base.BaseFragment;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component.AddEditNoteComponent;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component.DaggerAddEditNoteComponent;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules.AddEditNoteModule;
import com.example.dendy_s784.mvccleanapptemplate.model.Note;


import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;

public class AddEditNoteFragment extends BaseFragment implements AddEditNoteContract.View{

    public static final String ARGUMENT_DETAILS_NOTE_ID = "EDIT_NOTE_ID";
    public static final String ARGUMENT_MESSAGE = "ERROR_MESSAGE";

    @BindView(R.id.add_task_title)
    TextView mTitle;
    @BindView(R.id.add_task_description)
    TextView mDescription;

    @Inject
    AddEditNoteContract.Presenter mPresenter;

    private AddEditNoteComponent component;

    public static AddEditNoteFragment newInstance(){
        return new AddEditNoteFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_addeditnote;
    }

    @Override
    protected void init() {
        initComponent();
        //get argument sent from parent activity
        if(getArguments()!=null)
        {
            String noteIdForNoteDetail = getArguments().getString(ARGUMENT_DETAILS_NOTE_ID);
            if(!noteIdForNoteDetail.isEmpty()){
                mPresenter.getNoteDetails(noteIdForNoteDetail);
            }
        }
    }

    private void initComponent() {
        if (component == null) {
            component = DaggerAddEditNoteComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .addEditNoteModule(new AddEditNoteModule(this))
                    .build();
        }
        component.inject(this);

        registerPresenter(mPresenter);
    }

    @Override
    public void showNotesList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void onNoteSaveSuccess(Note note) {
        Intent intent = new Intent();
        intent.putExtra(ARGUMENT_MESSAGE,note.getmId());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void onNoteSaveFailed(String message) {
        Intent intent = new Intent();
        intent.putExtra(ARGUMENT_MESSAGE,message);
        getActivity().setResult(Activity.RESULT_CANCELED, intent);
        getActivity().finish();
    }

    @Override
    public void onNoteDetails(Note note) {
        mTitle.setText(note.getmTitle());
        mDescription.setText(note.getmDescription());
    }

    public void saveNote(String noteId)
    {
        if(noteId!=null && !noteId.isEmpty())
        {
            mPresenter.saveNote(new Note(noteId,mTitle.getText().toString(), mDescription.getText().toString()));
        }
        else
        {
            mPresenter.saveNote(new Note(UUID.randomUUID().toString(), mTitle.getText().toString(), mDescription.getText().toString()));
        }
    }

}
