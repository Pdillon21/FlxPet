package com.example.fluxpet;

public interface ResultListener<T> {
    void finish(T result);
}
