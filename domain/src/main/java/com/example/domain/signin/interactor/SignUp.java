package com.example.domain.signin.interactor;

import com.example.domain.PostExecutionThread;
import com.example.domain.ThreadExecutor;
import com.example.domain.UseCase;
import com.example.domain.signin.*;
import com.example.domain.signin.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SignUp extends UseCase<SignUpResult, SignUpRequest> {

    private final UserRepository userRepository;

    @Inject
    public SignUp(ThreadExecutor threadExecutor,
                  PostExecutionThread postExecutionThread,
                  UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<SignUpResult> buildUseCaseObservable(SignUpRequest signUpRequest) {
        return userRepository.SignUp(signUpRequest);
    }
}
