package com.example.data;

public abstract class AbstractEntityDataFactory<T> {
    public abstract T createData(@Source String source);
}
