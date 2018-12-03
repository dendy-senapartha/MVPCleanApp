/**
 * DANA.id
 * PT. Espay Debit Indonesia Koe.
 * Copyright (c) 2017-2018 All Rights Reserved.
 */
package com.example.domain;

/**
 *
 */
public interface ErrorBundle {

    /**
     * getErrorMessage from {@link Exception}
     */
    String getErrorMessage();

    /**
     * get {@link Exception}
     */
    Exception getException();
}