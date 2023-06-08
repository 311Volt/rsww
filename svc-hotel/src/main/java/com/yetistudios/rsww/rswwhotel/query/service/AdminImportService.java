package com.yetistudios.rsww.rswwhotel.query.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yetistudios.rsww.rswwhotel.RswwHotelApplication;
import com.yetistudios.rsww.rswwhotel.query.entity.Hotel;
import com.yetistudios.rsww.rswwhotel.query.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Profile("!test")
public class AdminImportService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HotelRepository hotelRepository;

    public void importHotels(Map<String, Hotel> hotels) {
        hotelRepository.insert(hotels.values());
    }

}
