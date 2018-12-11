package com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules;

import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.PerActivity;
import com.example.dendy_s784.mvccleanapptemplate.signin.SignInContract;
import com.example.dendy_s784.mvccleanapptemplate.signin.SignInPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SignInActivityModule {

    private SignInContract.View view;

    public SignInActivityModule (SignInContract.View view){
        this.view = view;
    }

    @Provides
    @PerActivity
    SignInContract.View provideView()
    {
        return view;
    }

    @Provides
    @PerActivity
    SignInContract.Presenter providePresenter(SignInPresenter presenter)
    {
        return presenter;
    }
}
