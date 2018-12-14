package com.example.dendy_s784.mvccleanapptemplate.signin;

import android.content.Context;

import com.example.domain.DefaultObserver;
import com.example.domain.signin.SignInRequest;
import com.example.domain.signin.SignInResult;
import com.example.domain.signin.SignUpRequest;
import com.example.domain.signin.SignUpResult;
import com.example.domain.signin.interactor.CheckSignIn;
import com.example.domain.signin.interactor.SignIn;
import com.example.domain.signin.interactor.SignUp;

import javax.inject.Inject;

public class SignInPresenter implements SignInContract.Presenter {

    private final Context context;

    private final SignInContract.View view;

    private SignIn signIn;
    private CheckSignIn checkSignIn;
    private SignUp signUp;

    @Inject
    public SignInPresenter(Context context, SignInContract.View view, SignIn signIn, CheckSignIn checkSignIn,
                           SignUp signUp)
    {
        this.context = context;
        this.view = view;
        this.signIn = signIn;
        this.checkSignIn = checkSignIn;
        this.signUp = signUp;
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
    public void SignUp(String email, String password) {
        signUp.execute(new DefaultObserver<SignUpResult>(){
            @Override
            public void onNext(SignUpResult signUpResult  ) {
                //TODO: if user are new, it should redirect user to confirm email page
                view.OnSignInSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }
        }, new SignUpRequest(email, password));
    }

    @Override
    public void SignOut() {

    }

    @Override
    public void IsSignIn() {
        checkSignIn.execute(new DefaultObserver<Boolean>(){
            @Override
            public void onNext(Boolean result) {
                if(result)
                {
                    view.OnSignInSuccess();
                }

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void onDestroy() {

    }
}
