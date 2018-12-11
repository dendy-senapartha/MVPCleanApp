package com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules;

import android.app.Application;
import android.content.Context;
import android.service.autofill.UserData;

import com.example.data.JobExecutor;
import com.example.data.note.repository.NoteDataRepository;
import com.example.data.user.repository.UserDataRepository;
import com.example.dendy_s784.mvccleanapptemplate.base.UIThread;
import com.example.domain.PostExecutionThread;
import com.example.domain.ThreadExecutor;
import com.example.domain.note.repository.NoteRepository;
import com.example.domain.signin.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext()
    {
        return application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    NoteRepository provideNoteRepository(NoteDataRepository noteDataRepository) {
        return noteDataRepository;
    }


    @Provides
    @Singleton
    UserRepository provideUserRepository(UserDataRepository userDataRepository) {
        return userDataRepository;
    }
}
