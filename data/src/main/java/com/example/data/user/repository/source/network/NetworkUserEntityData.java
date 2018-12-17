package com.example.data.user.repository.source.network;

import android.text.TextUtils;

import com.example.data.base.UnInitializedSecuredPreferencesException;
import com.example.data.user.repository.source.UserEntityData;
import com.example.data.user.repository.source.network.request.UserRequest;
import com.example.data.user.repository.source.network.response.SignInResponse;
import com.example.data.user.repository.source.network.response.SignUpResponse;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class NetworkUserEntityData implements UserEntityData {

    private final UserNetwork userNetwork;

    public NetworkUserEntityData(UserNetwork userNetwork) {
        this.userNetwork = userNetwork;
    }

    private <T> Observable<T> initObservable(Callable<T> callable) {
        return initializedRequest(Observable.fromCallable(callable));
    }

    private <T> Observable<T> initializedRequest(Observable<T> observable) {
        return observable.onErrorResumeNext(initAndRetry(observable));
    }

    private <T> Function<Throwable, ? extends Observable<? extends T>>
    initAndRetry(final Observable<T> resumedObservable) {
        return (Function<Throwable, Observable<? extends T>>) throwable -> {
            if (throwable instanceof NullPointerException) {
                return init().concatMap(aBoolean -> resumedObservable);
            }
            return Observable.error(throwable);
        };
    }

    @Override
    public Observable<Boolean> init() {
        return initObservable(() -> {
            userNetwork.init();
            return true;
        });
    }

    @Override
    public Observable<SignInResponse> SignIn(UserRequest userRequest) {
        return initObservable(()->{
            return userNetwork.SignIn(userRequest);
        });
    }

    @Override
    public Observable<Boolean> CheckSignIn() {
        return initObservable(()->{
            return userNetwork.CheckSignIn();
        });
    }

    @Override
    public Observable<Boolean> SignOut() {
        return initObservable(()->{
            return userNetwork.SignOut();
        });
    }

    @Override
    public Observable<SignUpResponse> SignUp(UserRequest userRequest) {
        return initObservable(()->{
            return userNetwork.SignUp(userRequest);
        });
    }
}
