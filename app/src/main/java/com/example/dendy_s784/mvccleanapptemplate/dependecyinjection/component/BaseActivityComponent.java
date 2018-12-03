package com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component;

import com.example.dendy_s784.mvccleanapptemplate.base.BaseActivity;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.PerActivity;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules.BaseActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {BaseActivityModule.class})
public interface BaseActivityComponent {
    BaseActivity baseActivity();
}
