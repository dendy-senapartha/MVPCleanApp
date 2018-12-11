package com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component;

import android.app.Application;
import android.content.Context;

import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules.ApplicationModule;
import com.example.dendy_s784.mvccleanapptemplate.aplication.MVPCleanAppTemplate;
import com.example.dendy_s784.mvccleanapptemplate.base.BaseActivity;
import com.example.domain.PostExecutionThread;
import com.example.domain.ThreadExecutor;
import com.example.domain.note.repository.NoteRepository;
import com.example.domain.signin.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(MVPCleanAppTemplate mvpCleanAppTemplate);
    void inject(BaseActivity baseActivity);
    Context context();
    Application application();

    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    NoteRepository provideNoteRepository();
    UserRepository provideUserRepository();
}
