package com.example.dendy_s784.mvccleanapptemplate.signin;

import com.example.dendy_s784.mvccleanapptemplate.base.AbstractContract;

public interface SignInContract {

    interface View{
       void OnSignInSuccess();
       void OnSignOutSuccess();
    }

    interface Presenter extends AbstractContract.AbstractPresenter{
        void SignIn(String email, String password);
        void SignUp(String email, String password);
        void SignOut();
        void IsSignIn();
    }
}
