package com.yetistudios.rsww.rswwflight.service;

import com.yetistudios.rsww.messages.query.CheckFlightAvailabilityQuery;
import com.yetistudios.rsww.rswwflight.entity.Flight;
import com.yetistudios.rsww.rswwflight.entity.FlightAvailability;
import com.yetistudios.rsww.rswwflight.repository.AirportRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightAvailabilityRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlightAvailabilityService {
    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightAvailabilityRepository flightAvailabilityRepository;

    public void registerFlight(Integer flightNumber) {
        flightAvailabilityRepository.save(
                FlightAvailability.builder()
                        .flightNumber(flightNumber)
                        .build()
        );
    }

    public Optional<FlightAvailability> findById(Integer flightNumber) {
        return flightAvailabilityRepository.findById(flightNumber);
    }

    public Boolean isAvailable(CheckFlightAvailabilityQuery query) {
        Optional<Flight> flight = flightRepository.findById(query.flightNumber);
        if(flight.isEmpty()) {
            return false;
        }

        Optional<FlightAvailability> availability = findById(query.flightNumber);
        if(availability.isEmpty()) {
            registerFlight(query.flightNumber);
            availability = findById(query.flightNumber);
            if(availability.isEmpty()) {
                throw new RuntimeException("flight registration silently failed (should never happen)");
            }
        }

        return availability.get().numTakenSeats + query.numSeats < flight.get().numSeats;
    }


}
