package com.yetistudios.rsww.rswwsaga;

import com.yetistudios.rsww.common.dto.ReservationDto;
import com.yetistudios.rsww.common.messages.command.*;
import com.yetistudios.rsww.common.messages.entity.Offer;
import com.yetistudios.rsww.common.messages.event.*;
import com.yetistudios.rsww.common.messages.query.GetOfferQuery;
import com.yetistudios.rsww.common.messages.query.GetReservationQuery;
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
public class
ReservationProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;


    @StartSaga
    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(ReservationCreatedEvent event){
        log.info("\nReservationCreatedEvent for reservation #{}", event.getReservationId());
        GetOfferQuery getOfferQuery = new GetOfferQuery(event.getOfferId());

        Offer offer;
        try {
            offer = queryGateway.query(getOfferQuery, ResponseTypes.instanceOf(Offer.class)).join();
        } catch (Exception e){
            log.error("GetOfferQuery for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getCause());
            cancelReservationCommand(event.getReservationId());
            return;
        }

        try {
            BookHotelCommand bookHotelCommand = BookHotelCommand.builder()
                    .reservationId(event.getReservationId())
                    .hotelCode(offer.getHotelBrief().getId())
                    .numSingleRooms(event.getNumSingleRooms())
                    .numDoubleRooms(event.getNumDoubleRooms())
                    .numTripleRooms(event.getNumTripleRooms())
                    .timestampBegin(offer.getStartDate().toEpochSecond(ZoneOffset.UTC))
                    .timestampEnd(offer.getEndDate().toEpochSecond(ZoneOffset.UTC))
                    .build();

            commandGateway.send(bookHotelCommand);
        }catch (Exception e){
            log.error("BookHotelCommand for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getCause());
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
        log.info("ReservationCanceledEvent for reservation #{}", event.getReservationId());
    }


    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(HotelReservationFailedEvent event){
        log.info("HotelReservationFailedEvent for reservation #{}", event.getReservationId());
        cancelReservationCommand(event.getReservationId());
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(HotelReservationSuccessfulEvent event){
        log.info("\nHotelReservationSuccessfulEvent for reservation #{}", event.getReservationId());
        GetReservationQuery getReservationQuery = new GetReservationQuery(event.getReservationId());

        ReservationDto reservation;
        try {
            reservation = queryGateway.query(getReservationQuery, ResponseTypes.instanceOf(ReservationDto.class)).join();
        } catch (Exception e){
            log.error("GetReservationQuery for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getCause());
            cancelHotelReservationCommand(event.getReservationId());
            return;
        }

        GetOfferQuery getOfferQuery = new GetOfferQuery(reservation.getOfferId());

        Offer offer;
        try {
            offer = queryGateway.query(getOfferQuery, ResponseTypes.instanceOf(Offer.class)).join();
        } catch (Exception e){
            log.error("GetOfferQuery for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getCause());
            cancelHotelReservationCommand(event.getReservationId());
            return;
        }

        try {
            ReservationDto finalReservation = reservation;
            String flightNumber = offer.getFlights().stream()
                    .filter(flight -> flight.getOutboundFlight().getDepartureAirportName().equals(finalReservation.getDepartureAirportName()))
                    .findFirst()
                    .get()
                    .getOutboundFlight()
                    .getId();
            BookFlightCommand bookFlightCommand = BookFlightCommand.builder()
                    .reservationId(event.getReservationId())
                    .flightNumber(Integer.parseInt(flightNumber))
                    .numSeats(reservation.getNrOfPeople())
                    .build();

            commandGateway.send(bookFlightCommand);
        } catch (Exception e){
            log.error("BookFlightCommand for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getMessage());
            cancelHotelReservationCommand(event.getReservationId());
        }
    }

    private void cancelHotelReservationCommand(String reservationId){
        CancelHotelBookingCommand command = new CancelHotelBookingCommand(reservationId);

        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(PlaneReservationFailedEvent event){
        log.info("PlaneReservationFailedEvent for reservation #{}", event.getReservationId());
        cancelHotelReservationCommand(event.getReservationId());
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(PlaneReservationSuccessfulEvent event){
        log.info("\nPlaneReservationSuccessfulEvent for reservation #{}", event.getReservationId());
        GetReservationQuery getReservationQuery = new GetReservationQuery(event.getReservationId());

        ReservationDto reservation;
        try {
            reservation = queryGateway.query(getReservationQuery, ResponseTypes.instanceOf(ReservationDto.class)).join();
        } catch (Exception e){
            log.error("GetReservationQuery for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getCause());
            cancelPlaneReservationRequest(event.getReservationId());
            return;
        }

        GetOfferQuery getOfferQuery = new GetOfferQuery(reservation.getOfferId());

        Offer offer;
        try {
            offer = queryGateway.query(getOfferQuery, ResponseTypes.instanceOf(Offer.class)).join();
        } catch (Exception e){
            log.error("GetOfferQuery for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getCause());
            cancelPlaneReservationRequest(event.getReservationId());
            return;
        }

        try {
            ReservationDto finalReservation = reservation;
            String flightNumber = offer.getFlights().stream()
                    .filter(flight -> flight.getOutboundFlight().getDepartureAirportName().equals(finalReservation.getDepartureAirportName()))
                    .findFirst()
                    .get()
                    .getReturnFlight()
                    .getId();
            BookReturnFlightCommand bookFlightCommand = BookReturnFlightCommand.builder()
                    .reservationId(event.getReservationId())
                    .flightNumber(Integer.parseInt(flightNumber))
                    .numSeats(reservation.getNrOfPeople())
                    .build();

            commandGateway.send(bookFlightCommand);
        } catch (Exception e){
            log.error("BookFlightCommand for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getCause());
            cancelPlaneReservationRequest(event.getReservationId());
        }
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(ReturnPlaneReservationFailedEvent event){
        log.info("ReturnPlaneReservationFailedEvent for reservation #{}", event.getReservationId());
        cancelPlaneReservationRequest(event.getReservationId());
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(ReturnPlaneReservationSuccessfulEvent event){
        log.info("\nReturnPlaneReservationSuccessfulEvent for reservation #{}", event.getReservationId());
        GetReservationQuery getReservationQuery = new GetReservationQuery(event.getReservationId());

        ReservationDto reservation;
        try {
            reservation = queryGateway.query(getReservationQuery, ResponseTypes.instanceOf(ReservationDto.class)).join();
        } catch (Exception e){
            log.error("GetReservationQuery for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getCause());
            cancelPlaneReservationRequest(event.getReservationId());
            return;
        }

        try {
            DecreaseOfferAmountCommand decreaseOfferAmountCommand = DecreaseOfferAmountCommand.builder()
                    .reservationId(event.getReservationId())
                    .offerId(reservation.getOfferId())
                    .numberOfOffers(reservation.getNrOfPeople())
                    .build();

            commandGateway.sendAndWait(decreaseOfferAmountCommand);
        } catch (Exception e){
            log.error("DecreaseOfferAmountCommand for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getCause());
            cancelPlaneReservationRequest(event.getReservationId());
        }
    }

    private void cancelPlaneReservationRequest(String reservationId){
        CancelFlightBookingCommand command = new CancelFlightBookingCommand(reservationId);

        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(OfferDecreaseAmountFailedEvent event){
        log.info("OfferDecreaseAmountFailedEvent for reservation #{}", event.getReservationId());
        cancelPlaneReservationRequest(event.getReservationId());
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(OfferDecreaseAmountSuccessfulEvent event){
        log.info("\nOfferDecreaseAmountEvent for reservation #{}", event.getReservationId());
        GetReservationQuery getReservationQuery = new GetReservationQuery(event.getReservationId());

        ReservationDto reservation;
        try {
            reservation = queryGateway.query(getReservationQuery, ResponseTypes.instanceOf(ReservationDto.class)).join();
        } catch (Exception e){
            log.error("GetReservationQuery for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getCause());
            cancelOfferDecreaseAmountCommand(event.getReservationId());
            return;
        }

        try {
            ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                    .reservationId(event.getReservationId())
                    .price(reservation.getPrice())
                    .clientId(reservation.getClientId())
                    .paid(reservation.isPaid())
                    .build();

            commandGateway.send(validatePaymentCommand);
        } catch (Exception e){
            log.error("ValidatePaymentCommand for reservation #{}, \n Cause: #{}", event.getReservationId(), e.getCause());
            cancelOfferDecreaseAmountCommand(event.getReservationId());
        }

    }


    private void cancelOfferDecreaseAmountCommand(String reservationId){
        GetReservationQuery getReservationQuery = new GetReservationQuery(reservationId);
        ReservationDto reservation;
        try {
            reservation = queryGateway.query(getReservationQuery, ResponseTypes.instanceOf(ReservationDto.class)).join();
        } catch (Exception e){
            log.error("GetReservationQuery for reservation #{}, \n Cause: #{}", reservationId, e.getCause());
            cancelPlaneReservationRequest(reservationId);
            return;
        }

        IncreaseOfferAmountCommand command = IncreaseOfferAmountCommand.builder()
                .reservationId(reservationId)
                .numberOfOffers(reservation.getNrOfPeople())
                .build();

        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(OfferIncreaseAmountEvent event){
        log.info("OfferIncreaseAmountEvent for reservation #{}", event.getReservationId());
        cancelPlaneReservationRequest(event.getReservationId());
    }

    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(PaymentInvalidEvent event){
        log.info("OfferIncreaseAmountEvent for reservation #{}", event.getReservationId());
        cancelOfferDecreaseAmountCommand(event.getReservationId());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "reservationId")
    private void handle(PaymentValidEvent event){
        log.info("PaymentValidEvent for reservation #{}", event.getReservationId());
        log.info("End of saga for reservation #{}", event.getReservationId());
    }

}
