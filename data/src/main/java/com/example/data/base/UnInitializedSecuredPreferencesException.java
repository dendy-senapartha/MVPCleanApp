/**
 * DANA.id
 * PT. Espay Debit Indonesia Koe.
 * Copyright (c) 2017-2018 All Rights Reserved.
 */
package com.example.data.base;

/**
 *
 */
public class UnInitializedSecuredPreferencesException extends Exception {

    public UnInitializedSecuredPreferencesException() {
        super("Secured Preferences has not been initialized");
    }
}