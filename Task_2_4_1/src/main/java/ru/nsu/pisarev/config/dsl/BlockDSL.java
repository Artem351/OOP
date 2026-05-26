package ru.nsu.pisarev.config.dsl;

import groovy.lang.Closure;


public record BlockDSL<T>(T delegate) {


    public void call(Closure<?> closure) {
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.setDelegate(delegate);
        closure.call();
    }
}
