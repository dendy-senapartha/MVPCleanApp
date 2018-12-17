package com.example.data.user.repository.source;

import com.example.data.user.repository.source.network.request.UserRequest;
import com.example.data.user.repository.source.network.response.SignInResponse;
import com.example.data.user.repository.source.network.response.SignUpResponse;

import io.reactivex.Observable;

public interface UserEntityData {

    Observable<Boolean> init();
    Observable<SignInResponse> SignIn(UserRequest userRequest);
    Observable<SignUpResponse> SignUp(UserRequest userRequest);
    Observable<Boolean> CheckSignIn();

    Observable<Boolean> SignOut();
}
