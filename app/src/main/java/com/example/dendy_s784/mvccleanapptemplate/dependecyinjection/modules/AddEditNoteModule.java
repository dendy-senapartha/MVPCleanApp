package com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules;

import com.example.dendy_s784.mvccleanapptemplate.addeditnotes.AddEditNoteContract;
import com.example.dendy_s784.mvccleanapptemplate.addeditnotes.AddEditNotePresenter;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.PerActivity;
import com.example.dendy_s784.mvccleanapptemplate.mainactivity.NotesContract;
import com.example.dendy_s784.mvccleanapptemplate.mainactivity.NotesPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class AddEditNoteModule {

    private AddEditNoteContract.View view;

    public AddEditNoteModule(AddEditNoteContract.View view){
        this.view = view;
    }

    @Provides
    @PerActivity
    AddEditNoteContract.View provideView()
    {
        return view;
    }

    @Provides
    @PerActivity
    AddEditNoteContract.Presenter providePresenter(AddEditNotePresenter presenter)
    {
        return presenter;
    }
}
