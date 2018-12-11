package com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component;

import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.PerActivity;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules.SignInActivityModule;
import com.example.dendy_s784.mvccleanapptemplate.signin.SignInActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {SignInActivityModule.class})
public interface SignInActivityComponent {
    void inject (SignInActivity activity);
}
