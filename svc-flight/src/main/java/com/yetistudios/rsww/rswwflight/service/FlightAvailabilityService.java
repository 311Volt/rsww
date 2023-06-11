package com.yetistudios.rsww.rswwflight.service;

import com.yetistudios.rsww.common.messages.command.BookFlightCommand;
import com.yetistudios.rsww.common.messages.command.CancelFlightBookingCommand;
import com.yetistudios.rsww.common.messages.query.CheckFlightAvailabilityQuery;
import com.yetistudios.rsww.rswwflight.entity.*;
import com.yetistudios.rsww.rswwflight.exception.CannotCancelBookingException;
import com.yetistudios.rsww.rswwflight.exception.FlightUnavailableException;
import com.yetistudios.rsww.rswwflight.exception.NoSuchFlightException;
import com.yetistudios.rsww.rswwflight.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class FlightAvailabilityService {
    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightAvailabilityDeltaEventRepository eventRepository;

    @Autowired
    private FlightAvailabilitySnapshotRepository snapshotRepository;

    private static final Random random = new Random();


    public FlightAvailabilitySnapshot getBestSnapshotFor(Integer flightNumber) {
        var prm = FlightNumAndTimestampId.builder()
                .flightNumber(flightNumber)
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return snapshotRepository
                .getLastSnapshotBefore(prm)
                .stream()
                .findFirst()
                .orElse(FlightAvailabilitySnapshot.empty(flightNumber));
    }

    public Optional<FlightAvailability> getCurrentAvailabilityOf(Integer flightNumber) {
        if(!flightRepository.existsById(flightNumber)) {
            return Optional.empty();
        }

        var prm = FlightNumAndTimestampId.builder()
                .flightNumber(flightNumber)
                .timestamp(Timestamp.from(Instant.now()))
                .build();

        var snapshot = getBestSnapshotFor(flightNumber);
        var availability = FlightAvailability.ofSnapshot(snapshot);
        var events = eventRepository.findAllEventsAfter(prm);
        availability.consumeEvents(events);
        return Optional.of(availability);
    }


    public Boolean isAvailable(CheckFlightAvailabilityQuery query) {
        var availability = getCurrentAvailabilityOf(query.flightNumber);
        if(availability.isEmpty()) {
            return false;
        }

        var flight = flightRepository.findById(query.flightNumber).orElseThrow();

        return availability.get().numTakenSeats + query.numSeats <= flight.numSeats;
    }

    public void saveSnapshot(FlightAvailability availability) {
        var snapshot = FlightAvailabilitySnapshot.builder()
                .id(new FlightNumAndTimestampId(availability.flightNumber, Timestamp.from(Instant.now())))
                .numTakenSeats(availability.numTakenSeats)
                .build();
        snapshotRepository.save(snapshot);
    }

    public void bookFlight(BookFlightCommand command) {
        if(!flightRepository.existsById(command.flightNumber)) {
            throw new NoSuchFlightException("No such flight: #" + command.flightNumber);
        }
        if(command.numSeats < 1) {
            throw new FlightUnavailableException("Ill-formed command: cannot book less than 1 seat");
        }
        if(!isAvailable(CheckFlightAvailabilityQuery.builder()
                .flightNumber(command.flightNumber)
                .numSeats(command.numSeats).build())) {
            throw new FlightUnavailableException("Cannot book any more seats for flight #" + command.flightNumber);
        }
        var event = FlightAvailabilityDeltaEventEntity.builder()
                .id(FlightNumAndTimestampId.builder()
                        .timestamp(Timestamp.from(Instant.now()))
                        .flightNumber(command.flightNumber)
                        .build())
                .reservationId(command.reservationId)
                .deltaSeats(command.numSeats)
                .build();

        eventRepository.save(event);
        log.info("{} seats have been reserved for flight #{}", command.numSeats, command.flightNumber);

        if(random.nextInt(100) == 0) { // xDDDDDD
            saveSnapshot(getCurrentAvailabilityOf(command.flightNumber).orElseThrow());
            log.info("snapshot saved for flight {}", command.flightNumber);
        }

    }

    public void cancelFlightBooking(CancelFlightBookingCommand command) {
        var reservationEvents = eventRepository.findByReservationId(command.reservationId);
        if(reservationEvents.size() != 1) {
            throw new CannotCancelBookingException("Reservation does not exist or has already been cancelled");
        }
        var originalEvent = reservationEvents.stream().findFirst().orElseThrow();
        originalEvent.id.timestamp = Timestamp.from(Instant.now());
        originalEvent.deltaSeats = -originalEvent.deltaSeats;
        eventRepository.save(originalEvent);
        log.info("Reservation {} for flight #{} has been cancelled ({} newly free seats)",
                command.reservationId, originalEvent.id.flightNumber, -originalEvent.deltaSeats
        );

    }
}
