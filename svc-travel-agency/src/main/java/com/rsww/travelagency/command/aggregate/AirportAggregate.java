package com.rsww.travelagency.command.aggregate;

import com.rsww.travelagency.command.command.CreateAirportCommand;
import com.rsww.travelagency.common.event.AirportCreatedEvent;
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
public class AirportAggregate {
    @AggregateIdentifier
    private String id;
    private String code;
    private String name;
    private Boolean forDeparture;

    @CommandHandler
    public AirportAggregate(CreateAirportCommand createAirportCommand) {
        log.info("CreateAirportCommand received.");
        AggregateLifecycle.apply(new AirportCreatedEvent(createAirportCommand.getId(), createAirportCommand.getCode(),
                createAirportCommand.getName(), createAirportCommand.getForDeparture()));
    }

    @EventSourcingHandler
    public void on(AirportCreatedEvent airportCreatedEvent) {
        log.info("An AirportCreatedEvent occurred");
        this.id = airportCreatedEvent.getId();
        this.code = airportCreatedEvent.getCode();
        this.name = airportCreatedEvent.getName();
        this.forDeparture = airportCreatedEvent.getForDeparture();
    }
}
