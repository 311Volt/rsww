package com.yetistudios.rsww.rswwworld.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yetistudios.rsww.rswwworld.RswwWorldApplication;
import com.yetistudios.rsww.rswwworld.entity.Airport;
import com.yetistudios.rsww.rswwworld.entity.Flight;
import com.yetistudios.rsww.rswwworld.entity.Hotel;
import com.yetistudios.rsww.rswwworld.repository.AirportRepository;
import com.yetistudios.rsww.rswwworld.repository.FlightRepository;
import com.yetistudios.rsww.rswwworld.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Profile("!test")
public class AdminImportService {

    private static final Logger logger = LoggerFactory.getLogger(RswwWorldApplication.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public void importAirports(List<Airport> airports) {
        airportRepository.insert(airports);
    }

    public void importFlights(List<Flight> flights) {
        flightRepository.insert(flights);
    }

    public void importHotels(Map<String, Hotel> hotels) {
        hotelRepository.insert(hotels.values());
    }

}
