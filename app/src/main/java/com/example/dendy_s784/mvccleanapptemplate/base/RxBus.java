package com.example.dendy_s784.mvccleanapptemplate.base;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/*EventBus implementation using RxJava2
https://blog.mindorks.com/implementing-eventbus-with-rxjava-rxbus-e6c940a94bd8
* */
public class RxBus {

    public RxBus()
    {

    }

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }
}
