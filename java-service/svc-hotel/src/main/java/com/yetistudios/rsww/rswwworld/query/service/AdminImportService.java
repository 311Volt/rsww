package com.yetistudios.rsww.rswwworld.query.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yetistudios.rsww.rswwworld.RswwWorldApplication;
import com.yetistudios.rsww.rswwworld.query.entity.Hotel;
import com.yetistudios.rsww.rswwworld.query.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Profile("!test")
public class AdminImportService {

    private static final Logger logger = LoggerFactory.getLogger(RswwWorldApplication.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HotelRepository hotelRepository;

    public void importHotels(Map<String, Hotel> hotels) {
        hotelRepository.insert(hotels.values());
    }

}
