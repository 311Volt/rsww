package com.yetistudios.rsww.messages.event;

public class BaseEvent<TID> {

    private final TID id;

    public BaseEvent(TID id) {
        this.id = id;
    }

    public TID getId() {
        return id;
    }
}
