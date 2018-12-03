package com.example.dendy_s784.mvccleanapptemplate.base;

import io.reactivex.disposables.Disposable;

/**
 * basic behavior to handle disposable observer
 */
public interface DisposableHandler {

    void addDisposable(Disposable disposable);

    void dispose();
}
