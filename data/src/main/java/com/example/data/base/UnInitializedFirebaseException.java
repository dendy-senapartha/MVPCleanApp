package com.example.data.base;

/*
 * Created by dendy-prtha on 08/02/2019.
 * exception when firebase not initialized
 */
public class UnInitializedFirebaseException extends Exception{
    public UnInitializedFirebaseException() {
        super("Firebase has not been initialized");
    }
}
