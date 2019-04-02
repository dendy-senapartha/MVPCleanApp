
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