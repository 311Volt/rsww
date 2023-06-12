package com.yetistudios.rsww.rswwgateway.websocket;

import com.yetistudios.rsww.common.messages.entity.Offer;
import com.yetistudios.rsww.common.messages.event.OfferDecreaseAmountEvent;
import com.yetistudios.rsww.common.messages.event.OfferIncreaseAmountEvent;
import com.yetistudios.rsww.common.messages.event.ReservationCreatedEvent;
import com.yetistudios.rsww.common.messages.query.GetOfferQuery;
import com.yetistudios.rsww.rswwgateway.entity.AirportsPopularityEntity;
import com.yetistudios.rsww.rswwgateway.entity.DestinationEntity;
import com.yetistudios.rsww.rswwgateway.entity.HotelPopularityEntity;
import com.yetistudios.rsww.rswwgateway.entity.RoomTypesPopularityEntity;
import com.yetistudios.rsww.rswwgateway.repository.AirportPopularityRepository;
import com.yetistudios.rsww.rswwgateway.repository.DestinationRepository;
import com.yetistudios.rsww.rswwgateway.repository.HotelPopularityRepository;
import com.yetistudios.rsww.rswwgateway.repository.RoomTypesPopularityRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Req7Service {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private transient QueryGateway queryGateway;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private AirportPopularityRepository airportPopularityRepository;

    @Autowired
    private HotelPopularityRepository hotelPopularityRepository;

    @Autowired
    private RoomTypesPopularityRepository roomTypesPopularityRepository;

    @EventHandler
    public void handle(OfferDecreaseAmountEvent event) {
        simpMessagingTemplate.convertAndSend("/req7topic/purchase/" + event.getOfferId(), event);
    }

    @EventHandler
    public void handle(OfferIncreaseAmountEvent event) {
        event.setNumberOfOffers(-event.getNumberOfOffers());
        simpMessagingTemplate.convertAndSend("/req7topic/purchase/" + event.getOfferId(), event);
    }

    @EventHandler
    public void handle(ReservationCreatedEvent event) {
        event.getOfferId();
        GetOfferQuery getOfferQuery = new GetOfferQuery(event.getOfferId());

        Offer offer;
        try {
            offer = queryGateway.query(getOfferQuery, ResponseTypes.instanceOf(Offer.class)).join();
        } catch (Exception e){
            return;
        }
        destinationRepository.findById(offer.getHotelBrief().getCountry())
                .ifPresentOrElse(destinationEntity -> {

            destinationEntity.setPopularity(destinationEntity.getPopularity() + event.getNrOfPeople());
            destinationRepository.save(destinationEntity);
        }, () -> {
            DestinationEntity destinationEntity = DestinationEntity.builder()
                    .destination(offer.getHotelBrief().getCountry())
                    .popularity(event.getNrOfPeople())
                    .build();

            destinationRepository.save(destinationEntity);
        });

        List<DestinationEntity> popularDestinations = destinationRepository.findTop3ByOrderByPopularityDesc();
        simpMessagingTemplate.convertAndSend("/req7topic/popularDestinations", popularDestinations);

        airportPopularityRepository.findById(event.getDepartureAirportName())
                .ifPresentOrElse(airportsPopularity -> {
                    airportsPopularity.setPopularity(airportsPopularity.getPopularity() + event.getNrOfPeople());
                    airportPopularityRepository.save(airportsPopularity);
                }, () -> {
                    AirportsPopularityEntity airportsPopularity = AirportsPopularityEntity.builder()
                            .airportName(event.getDepartureAirportName())
                            .popularity(event.getNrOfPeople())
                            .build();
                    airportPopularityRepository.save(airportsPopularity);
        });

        List<AirportsPopularityEntity> popularAirports = airportPopularityRepository.findTop3ByOrderByPopularityDesc();
        simpMessagingTemplate.convertAndSend("/req7topic/popularAirports", popularAirports);

        hotelPopularityRepository.findById(offer.getHotelBrief().getId())
                .ifPresentOrElse(hotelPopularity -> {
                    hotelPopularity.setPopularity(hotelPopularity.getPopularity() + event.getNrOfPeople());
                    hotelPopularityRepository.save(hotelPopularity);
                }, () -> {
                    HotelPopularityEntity airportsPopularity = HotelPopularityEntity.builder()
                            .hotelId(offer.getHotelBrief().getId())
                            .hotelName(offer.getHotelBrief().getName())
                            .popularity(event.getNrOfPeople())
                            .build();
                    hotelPopularityRepository.save(airportsPopularity);
                });

        List<HotelPopularityEntity> popularHotels = hotelPopularityRepository.findTop3ByOrderByPopularityDesc();
        simpMessagingTemplate.convertAndSend("/req7topic/popularHotels", popularHotels);

        List<RoomTypesPopularityEntity> roomTypesPopularities = roomTypesPopularityRepository.findAll();
        if(roomTypesPopularities.isEmpty()){
            roomTypesPopularities.add(new RoomTypesPopularityEntity("singleRoom", event.getNumSingleRooms()));
            roomTypesPopularities.add(new RoomTypesPopularityEntity("doubleRoom", event.getNumDoubleRooms()));
            roomTypesPopularities.add(new RoomTypesPopularityEntity("tripleRoom", event.getNumTripleRooms()));
        } else {
            for (var roomTypePopularity: roomTypesPopularities) {
                if(roomTypePopularity.equals("singleRoom")){
                    roomTypePopularity.setPopularity(roomTypePopularity.getPopularity() + event.getNumSingleRooms());
                } else if(roomTypePopularity.equals("doubleRoom")){
                    roomTypePopularity.setPopularity(roomTypePopularity.getPopularity() + event.getNumDoubleRooms());
                } else {
                    roomTypePopularity.setPopularity(roomTypePopularity.getPopularity() + event.getNumTripleRooms());
                }
            }
        }

        roomTypesPopularityRepository.saveAll(roomTypesPopularities);

        simpMessagingTemplate.convertAndSend("/req7topic/popularRoomTypes", roomTypesPopularities);

    }
}
