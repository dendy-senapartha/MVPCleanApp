package com.example.dendy_s784.mvccleanapptemplate.signin;

import android.content.Context;
import android.util.Log;

import com.example.dendy_s784.mvccleanapptemplate.aplication.MVPCleanAppTemplate;
import com.example.dendy_s784.mvccleanapptemplate.base.Events;
import com.example.domain.DefaultObserver;
import com.example.domain.signin.SignInRequest;
import com.example.domain.signin.SignInResult;
import com.example.domain.signin.SignUpRequest;
import com.example.domain.signin.SignUpResult;
import com.example.domain.signin.interactor.CheckSignIn;
import com.example.domain.signin.interactor.SignIn;
import com.example.domain.signin.interactor.SignUp;


import javax.inject.Inject;

import io.reactivex.functions.Consumer;

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
                Log.d("SignIn","onNext : "+signInResult.exception);
                //eventbus using RXJava example
                ((MVPCleanAppTemplate)context).getBus().send(new Events.AutoEvent());
                view.OnSignInSuccess();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("SignIn","onError: "+e.toString());
            }
        }, new SignInRequest(email, password));
    }

    @Override
    public void SignUp(String email, String password) {
        signUp.execute(new DefaultObserver<SignUpResult>(){
            @Override
            public void onNext(SignUpResult signUpResult  ) {
                //TODO: if user are new, it should redirect user to confirm email page
                Log.d("SignUp","onNext : "+signUpResult.exception);
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
        Log.i("context.getClass() : ",""+context.getClass());
        context.getApplicationContext();
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
