package com.yetistudios.rsww.rswwsaga;

import com.yetistudios.rsww.messages.command.*;
import com.yetistudios.rsww.messages.entity.Offer;
import com.yetistudios.rsww.messages.entity.ReservationDto;
import com.yetistudios.rsww.messages.event.*;
import com.yetistudios.rsww.messages.query.GetOfferQuery;
import com.yetistudios.rsww.messages.query.GetReservationQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneOffset;

@Saga
@Slf4j
public class ReservationProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;


    @StartSaga
    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(ReservationCreatedEvent event){
        log.info("ReservationCreatedEvent for reservation {}", event.getReservationId());
        GetOfferQuery getOfferQuery = new GetOfferQuery(event.getOfferId());

        Offer offer = null;
        try {
            offer = queryGateway.query(getOfferQuery, ResponseTypes.instanceOf(Offer.class)).join();
        } catch (Exception e){
            log.error("GetOfferQuery for reservation {}, \n Cause: {}", event.getReservationId(), e.getCause());
            cancelReservationCommand(event.getReservationId());
        }

        log.info("GetOfferQuery for reservation {}", event.getReservationId());

        try {
            BookHotelCommand bookHotelCommand = BookHotelCommand.builder()
                    .reservationId(event.getReservationId())
                    .hotelCode(offer.getHotel().getCode())
                    .numSingleRooms(event.getNumSingleRooms())
                    .numDoubleRooms(event.getNumDoubleRooms())
                    .numTripleRooms(event.getNumTripleRooms())
                    .timestampBegin(offer.getStartDate().toEpochSecond(ZoneOffset.UTC))
                    .timestampEnd(offer.getEndDate().toEpochSecond(ZoneOffset.UTC))
                    .build();

            commandGateway.send(bookHotelCommand);
        }catch (Exception e){
            log.error("BookHotelCommand for reservation {}, \n Cause: {}", event.getReservationId(), e.getCause());
            cancelReservationCommand(event.getReservationId());
        }

    }

    private void cancelReservationCommand(String reservationId){
        CancelReservationCommand command = new CancelReservationCommand(reservationId);

        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(ReservationCanceledEvent event){
        log.info("ReservationCanceledEvent for reservation {}", event.getReservationId());
    }


    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(HotelReservationFailedEvent event){
        log.info("HotelReservationFailedEvent for reservation {}", event.getReservationId());
        cancelReservationCommand(event.getReservationId());
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(HotelReservationSuccessfulEvent event){
        log.info("HotelReservationSuccessfulEvent for reservation {}", event.getReservationId());
        GetReservationQuery getReservationQuery = new GetReservationQuery(event.getReservationId());

        ReservationDto reservation = null;
        try {
            reservation = queryGateway.query(getReservationQuery, ResponseTypes.instanceOf(ReservationDto.class)).join();
        } catch (Exception e){
            log.error("GetReservationQuery for reservation {}, \n Cause: {}", event.getReservationId(), e.getCause());
            cancelHotelReservationCommand(event.getReservationId());
        }

        GetOfferQuery getOfferQuery = new GetOfferQuery(event.getReservationId());

        Offer offer = null;
        try {
            offer = queryGateway.query(getOfferQuery, ResponseTypes.instanceOf(Offer.class)).join();
        } catch (Exception e){
            log.error("GetOfferQuery for reservation {}, \n Cause: {}", event.getReservationId(), e.getCause());
            cancelHotelReservationCommand(event.getReservationId());
        }

        try {
            ReservationDto finalReservation = reservation;
            BookFlightCommand bookFlightCommand = BookFlightCommand.builder()
                    .reservationId(event.getReservationId())
                    //.flightNumber(offer.getFlights().stream().filter(flight -> flight.getDepartureAirportName() == finalReservation.getDepartureAirportName()).findFirst().get().getId())
                    .build();

            commandGateway.send(bookFlightCommand);
        } catch (Exception e){
            log.error("ValidatePaymentCommand for reservation {}, \n Cause: {}", event.getReservationId(), e.getCause());
            cancelHotelReservationCommand(event.getReservationId());
        }
    }

    private void cancelHotelReservationCommand(String reservationId){
        CancelHotelBookingCommand command = new CancelHotelBookingCommand(reservationId);

        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(PlaneReservationFailedEvent event){
        log.info("PlaneReservationFailedEvent for reservation {}", event.getReservationId());
        cancelHotelReservationCommand(event.getReservationId());
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(PlaneReservationSuccessfulEvent event){
        log.info("PlaneReservationSuccessfulEvent for reservation {}", event.getReservationId());
        GetReservationQuery getReservationQuery = new GetReservationQuery(event.getReservationId());

        ReservationDto reservation = null;
        try {
            reservation = queryGateway.query(getReservationQuery, ResponseTypes.instanceOf(ReservationDto.class)).join();
        } catch (Exception e){
            log.error("GetReservationQuery for reservation {}, \n Cause: {}", event.getReservationId(), e.getCause());
            cancelPlaneReservationRequest(event.getReservationId());
        }

        try {
            DecreaseOfferAmountCommand decreaseOfferAmountCommand = DecreaseOfferAmountCommand.builder()
                    .reservationId(event.getReservationId())
                    .offerId(reservation.getOfferId())
                    .numberOfOffers(reservation.getNrOfPeople())
                    .build();

            commandGateway.send(decreaseOfferAmountCommand);
        } catch (Exception e){
            log.error("DecreaseOfferAmountCommand for reservation {}, \n Cause: {}", event.getReservationId(), e.getCause());
            cancelPlaneReservationRequest(event.getReservationId());
        }

    }

    private void cancelPlaneReservationRequest(String reservationId){
        CancelFlightBookingCommand command = new CancelFlightBookingCommand(reservationId);

        commandGateway.send(command);
    }



    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(OfferDecreaseAmountEvent event){
        log.info("OfferDecreaseAmountEvent for reservation {}", event.getReservationId());
        GetReservationQuery getReservationQuery = new GetReservationQuery(event.getReservationId());

        ReservationDto reservation = null;
        try {
            reservation = queryGateway.query(getReservationQuery, ResponseTypes.instanceOf(ReservationDto.class)).join();
        } catch (Exception e){
            log.error("GetReservationQuery for reservation {}, \n Cause: {}", event.getReservationId(), e.getCause());
            cancelOfferDecreaseAmountCommand(event.getReservationId());
        }

        try {
            ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                    .reservationId(event.getReservationId())
                    .price(reservation.getPrice())
                    .build();

            commandGateway.send(validatePaymentCommand);
        } catch (Exception e){
            log.error("ValidatePaymentCommand for reservation {}, \n Cause: {}", event.getReservationId(), e.getCause());
            cancelOfferDecreaseAmountCommand(event.getReservationId());
        }

    }

    private void cancelOfferDecreaseAmountCommand(String reservationId){
        GetReservationQuery getReservationQuery = new GetReservationQuery(reservationId);
        ReservationDto reservation = null;
        try {
            reservation = queryGateway.query(getReservationQuery, ResponseTypes.instanceOf(ReservationDto.class)).join();
        } catch (Exception e){
            log.error("GetReservationQuery for reservation {}, \n Cause: {}", reservationId, e.getCause());
            cancelPlaneReservationRequest(reservationId);
        }

        IncreaseOfferAmountCommand command = IncreaseOfferAmountCommand.builder()
                .reservationId(reservationId)
                .numberOfOffers(reservation.getNrOfPeople())
                .build();

        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(OfferIncreaseAmountEvent event){
        log.info("OfferIncreaseAmountEvent for reservation {}", event.getReservationId());
        cancelPlaneReservationRequest(event.getReservationId());
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(PaymentInvalidEvent event){
        log.info("OfferIncreaseAmountEvent for reservation {}", event.getReservationId());
        cancelOfferDecreaseAmountCommand(event.getReservationId());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(PaymentValidEvent event){
        log.info("PaymentValidEvent for reservation {}", event.getReservationId());
        log.info("End of saga for reservation {}", event.getReservationId());
    }

}
