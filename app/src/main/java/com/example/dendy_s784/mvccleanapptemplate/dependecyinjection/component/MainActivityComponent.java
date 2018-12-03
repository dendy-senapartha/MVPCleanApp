package com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component;

import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.PerActivity;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules.MainActivityModule;
import com.example.dendy_s784.mvccleanapptemplate.mainactivity.NotesFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {MainActivityModule.class})
public interface MainActivityComponent {
    void inject (NotesFragment fragment);
}
