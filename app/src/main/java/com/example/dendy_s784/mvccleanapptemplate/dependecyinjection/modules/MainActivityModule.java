package com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules;

import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.PerActivity;
import com.example.dendy_s784.mvccleanapptemplate.mainactivity.NotesContract;
import com.example.dendy_s784.mvccleanapptemplate.mainactivity.NotesPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class MainActivityModule {

    private NotesContract.View view;

    public MainActivityModule (NotesContract.View view){
        this.view = view;
    }

    @Provides
    @PerActivity
    NotesContract.View provideView()
    {
        return view;
    }

    @Provides
    @PerActivity
    NotesContract.Presenter providePresenter(NotesPresenter presenter)
    {
        return presenter;
    }
}
