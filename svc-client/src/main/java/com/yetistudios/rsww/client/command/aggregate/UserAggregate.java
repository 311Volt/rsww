package com.yetistudios.rsww.client.command.aggregate;

import com.yetistudios.rsww.client.command.command.CreateUserCommand;
import com.yetistudios.rsww.client.common.event.UserCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Slf4j
@Aggregate
@NoArgsConstructor
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private String email;
    private String password;

    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand) {
        log.info("CreateUserCommand received.");
        AggregateLifecycle.apply(new UserCreatedEvent(createUserCommand.getId(), createUserCommand.getEmail(),
                createUserCommand.getPassword()));
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent userCreatedEvent) {
        log.info("An UserCreatedEvent occurred");
        this.id = userCreatedEvent.getId();
        this.email = userCreatedEvent.getEmail();
        this.password = userCreatedEvent.getPassword();
    }
}
