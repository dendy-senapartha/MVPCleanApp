package com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules;

import android.app.Application;
import android.content.Context;

import com.example.data.JobExecutor;
import com.example.data.note.repository.NoteDataRepository;
import com.example.dendy_s784.mvccleanapptemplate.base.UIThread;
import com.example.domain.PostExecutionThread;
import com.example.domain.ThreadExecutor;
import com.example.domain.note.repository.NoteRepository;

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
}
