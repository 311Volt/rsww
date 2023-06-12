package com.yetistudios.rsww.rswwhotel.command.service;

import com.yetistudios.rsww.common.dto.HotelRoomVector;
import com.yetistudios.rsww.common.messages.command.BookHotelCommand;
import com.yetistudios.rsww.common.messages.command.CancelHotelBookingCommand;
import com.yetistudios.rsww.common.messages.event.HotelRoomBookedEvent;
import com.yetistudios.rsww.rswwhotel.command.event.HotelOccupationDeltaEvent;
import com.yetistudios.rsww.rswwhotel.command.exception.HotelDoesNotExistException;
import com.yetistudios.rsww.rswwhotel.command.exception.HotelUnavailableException;
import com.yetistudios.rsww.rswwhotel.command.repository.HotelOccupationDeltaEventRepository;
import com.yetistudios.rsww.rswwhotel.query.repository.HotelRepository;
import com.yetistudios.rsww.rswwhotel.query.service.HotelOccupationQueryService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HotelOccupationCommandService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelOccupationQueryService occupationQueryService;

    @Autowired
    private EventGateway eventGateway;

    @Autowired
    private HotelOccupationDeltaEventRepository eventRepository;


    public void bookRooms(BookHotelCommand request) {
        if(!hotelRepository.existsByCode(request.hotelCode)) {
            throw new HotelDoesNotExistException();
        }
        if(!occupationQueryService.checkReservationAvailability(request)) {
            throw new HotelUnavailableException();
        }

        var maxOccupation = occupationQueryService.getMaxOccupationDuring(request.hotelCode, request.timestampBegin, request.timestampEnd).orElseThrow();
        log.info(String.format("hotel %s max occupation: %s", request.hotelCode, maxOccupation.toString()));

        HotelOccupationDeltaEvent checkInEvent = HotelOccupationDeltaEvent.builder()
                ._id(new ObjectId())
                .hotelCode(request.hotelCode)
                .timestamp(request.timestampBegin)
                .reservationId(request.reservationId)
                .deltaSingleRooms(request.numSingleRooms)
                .deltaDoubleRooms(request.numDoubleRooms)
                .deltaTripleRooms(request.numTripleRooms)
                .build();

        HotelOccupationDeltaEvent checkOutEvent = HotelOccupationDeltaEvent.builder()
                ._id(new ObjectId())
                .hotelCode(request.hotelCode)
                .timestamp(request.timestampEnd)
                .reservationId(request.reservationId)
                .deltaSingleRooms(-request.numSingleRooms)
                .deltaDoubleRooms(-request.numDoubleRooms)
                .deltaTripleRooms(-request.numTripleRooms)
                .build();

        eventRepository.insert(checkInEvent);
        eventRepository.insert(checkOutEvent);

        eventGateway.publish(HotelRoomBookedEvent
                .builder()
                .hotelCode(request.hotelCode)
                .amount(new HotelRoomVector(request.numSingleRooms, request.numDoubleRooms, request.numTripleRooms))
                .build());

        log.info("hotel {} has been booked: -{} (reservationId: {})",
                request.hotelCode, request.getVec().toString(), request.reservationId);
    }

    public void cancelReservation(CancelHotelBookingCommand command) {
        eventRepository.deleteAllByReservationId(command.reservationId);
    }

}
