
package com.example.domain;

/**
 *
 */
public class DefaultErrorBundle implements ErrorBundle {

    private static final String DEFAULT_ERROR_MESSAGE = "Unknown Error";

    private final Exception exception;

    public DefaultErrorBundle(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String getErrorMessage() {
        return (exception != null) ? exception.getMessage() : DEFAULT_ERROR_MESSAGE;
    }

    @Override
    public Exception getException() {
        return exception;
    }
}