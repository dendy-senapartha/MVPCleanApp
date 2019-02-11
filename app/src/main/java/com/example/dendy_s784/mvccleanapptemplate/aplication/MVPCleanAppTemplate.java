package com.example.dendy_s784.mvccleanapptemplate.aplication;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import com.example.dendy_s784.mvccleanapptemplate.BuildConfig;
import com.example.dendy_s784.mvccleanapptemplate.base.RxBus;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component.ApplicationComponent;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component.DaggerApplicationComponent;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules.ApplicationModule;
import com.google.firebase.database.FirebaseDatabase;

import timber.log.Timber;

public class MVPCleanAppTemplate extends MultiDexApplication{

    private ApplicationComponent applicationComponent;
    private static Context context;
    private RxBus bus;

    public void onCreate()
    {
        super.onCreate();
        //Enabling Offline Capabilities
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //eventbus using RX java init
        bus = new RxBus();
        if (BuildConfig.DEBUG) {
            /**
             * Debug only available to logcat when the application set debug on
             */
            Timber.plant(new Timber.DebugTree());
        }

        registerActivityLifecycleCallbacks(new ApplicationOrientationCallback());
        initializeContext();
        initInjector();
    }

    private void initializeContext() {
        MVPCleanAppTemplate.context = getApplicationContext();
    }

    private void initInjector() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public RxBus getBus() {
        return bus;
    }
}
