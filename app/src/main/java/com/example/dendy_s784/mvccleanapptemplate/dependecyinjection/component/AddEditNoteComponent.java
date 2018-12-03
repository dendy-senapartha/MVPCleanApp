package com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component;

import com.example.dendy_s784.mvccleanapptemplate.addeditnotes.AddEditNoteFragment;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.PerActivity;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules.AddEditNoteModule;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules.MainActivityModule;
import com.example.dendy_s784.mvccleanapptemplate.mainactivity.NotesFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {AddEditNoteModule.class})
public interface AddEditNoteComponent {
    void inject(AddEditNoteFragment fragment);
}
