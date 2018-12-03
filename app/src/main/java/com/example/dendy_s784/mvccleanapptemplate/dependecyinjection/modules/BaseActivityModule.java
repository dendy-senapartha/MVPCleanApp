package com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules;

import com.example.dendy_s784.mvccleanapptemplate.base.BaseActivity;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseActivityModule {

    private final BaseActivity baseActivity;

    public BaseActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Provides
    @PerActivity
    BaseActivity baseActivity(){
        return  baseActivity;
    }
}
