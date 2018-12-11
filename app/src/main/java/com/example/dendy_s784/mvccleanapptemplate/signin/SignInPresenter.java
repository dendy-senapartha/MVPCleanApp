package com.example.dendy_s784.mvccleanapptemplate.signin;

import android.content.Context;

import com.example.domain.DefaultObserver;
import com.example.domain.signin.SignInRequest;
import com.example.domain.signin.SignInResult;
import com.example.domain.signin.interactor.SignIn;

import javax.inject.Inject;

public class SignInPresenter implements SignInContract.Presenter {

    private final Context context;

    private final SignInContract.View view;

    private SignIn signIn;

    @Inject
    public SignInPresenter(Context context, SignInContract.View view, SignIn signIn)
    {
        this.context = context;
        this.view = view;
        this.signIn = signIn;
    }

    @Override
    public void SignIn(String email, String password) {
        signIn.execute(new DefaultObserver<SignInResult>(){
            @Override
            public void onNext(SignInResult signInResult  ) {
                //System.out.printf("noteResult " + noteResult);
                view.OnSignInSuccess();

            }

            @Override
            public void onError(Throwable e) {

            }
        }, new SignInRequest(email, password));
    }

    @Override
    public void SignOut() {

    }

    @Override
    public void onDestroy() {

    }
}
