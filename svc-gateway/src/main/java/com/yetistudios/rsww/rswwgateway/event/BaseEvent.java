package com.yetistudios.rsww.rswwgateway.event;

public class BaseEvent<T> {

    private final T id;

    public BaseEvent(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
