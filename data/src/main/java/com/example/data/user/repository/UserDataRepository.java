package com.example.data.user.repository;

import com.example.data.Source;
import com.example.data.note.mapper.NoteRespondMapper;
import com.example.data.user.repository.mapper.UserRespondMapper;
import com.example.data.user.repository.source.UserEntityData;
import com.example.data.user.repository.source.UserEntityDataFactory;
import com.example.data.user.repository.source.network.request.UserRequest;
import com.example.domain.signin.SignInRequest;
import com.example.domain.signin.SignInResult;
import com.example.domain.signin.SignUpRequest;
import com.example.domain.signin.SignUpResult;
import com.example.domain.signin.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class UserDataRepository implements UserRepository{

    private final UserEntityDataFactory userEntityDataFactory;

    private final UserRespondMapper mapper;

    @Inject
    public UserDataRepository(UserEntityDataFactory userEntityDataFactory,
                              UserRespondMapper mapper)
    {
        this.userEntityDataFactory = userEntityDataFactory;
        this.mapper = mapper;
    }

    /*Instructs an ObservableSource to pass control to another ObservableSource rather than invoking onError if it encounters an error.
     * */
    protected <T> Observable<T> initializedRequest(Observable<T> observable) {
        return observable.onErrorResumeNext(observable);
    }


    private UserEntityData createUserData() {
        return userEntityDataFactory.createData(Source.NETWORK);
    }

    @Override
    public Observable<SignInResult> SignIn(SignInRequest signInRequest) {
        return initializedRequest(createUserData()
                .SignIn(new UserRequest(signInRequest.email, signInRequest.password))
                .map(mapper::transform)
        );

    }

    @Override
    public Observable<SignInResult> SignOut() {
        return null;
    }

    @Override
    public Observable<Boolean> CheckSignIn() {
        return initializedRequest(createUserData().CheckSignIn()
        );
    }

    @Override
    public Observable<SignUpResult> SignUp(SignUpRequest signUpRequest) {
        return initializedRequest(createUserData()
                .SignUp(new UserRequest(signUpRequest.email, signUpRequest.password))
                .map(mapper::transform)
        );
    }
}
