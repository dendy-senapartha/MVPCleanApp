package com.example.domain.signin.interactor;

import com.example.domain.PostExecutionThread;
import com.example.domain.ThreadExecutor;
import com.example.domain.UseCase;
import com.example.domain.signin.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CheckSignIn extends UseCase<Boolean, Void> {

    private final UserRepository userRepository;

    @Inject
    public CheckSignIn(ThreadExecutor threadExecutor,
                  PostExecutionThread postExecutionThread,
                  UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(Void aVoid) {
        return userRepository.CheckSignIn();
    }
}
