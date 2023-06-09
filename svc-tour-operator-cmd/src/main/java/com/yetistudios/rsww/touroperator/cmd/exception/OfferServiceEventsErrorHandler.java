package com.yetistudios.rsww.touroperator.cmd.exception;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

import javax.annotation.Nonnull;

public class OfferServiceEventsErrorHandler implements ListenerInvocationErrorHandler {
    @Override
    public void onError(@Nonnull Exception e, @Nonnull EventMessage<?> eventMessage, EventMessageHandler eventMessageHandler) throws Exception {
        throw e;
    }
}
