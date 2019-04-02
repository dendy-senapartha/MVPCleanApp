
package com.example.domain;

import java.util.NoSuchElementException;

/**
 */
public class Optional<T> {

    private final T optional;

    public Optional(T optional) {
        this.optional = optional;
    }

    public boolean isEmpty() {
        return optional == null;
    }

    public T get() {
        if (optional == null) {
            throw new NoSuchElementException("No value present");
        }
        return optional;
    }
}