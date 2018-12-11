package com.example.domain.signin.interactor;

import com.example.domain.PostExecutionThread;
import com.example.domain.ThreadExecutor;
import com.example.domain.signin.SignInRequest;
import com.example.domain.signin.SignInResult;
import com.example.domain.UseCase;
import com.example.domain.signin.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SignIn extends UseCase<SignInResult, SignInRequest> {

    private final UserRepository userRepository;

    @Inject
    public SignIn(ThreadExecutor threadExecutor,
                  PostExecutionThread postExecutionThread,
                  UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<SignInResult> buildUseCaseObservable(SignInRequest signInRequest) {
        return userRepository.SignIn(signInRequest);
    }
}
