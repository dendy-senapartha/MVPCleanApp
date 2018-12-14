package com.example.domain.signin.repository;

import com.example.domain.signin.SignInRequest;
import com.example.domain.signin.SignInResult;
import com.example.domain.signin.SignUpRequest;
import com.example.domain.signin.SignUpResult;

import io.reactivex.Observable;

public interface UserRepository {
    Observable<SignInResult> SignIn(SignInRequest signInRequest);

    Observable<SignInResult> SignOut();
    Observable<Boolean> CheckSignIn();

    Observable<SignUpResult> SignUp(SignUpRequest signUpRequest);
}
