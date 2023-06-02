package com.yetistudios.rsww.touroperator.cmd.command.commands;

import com.yetistudios.rsww.touroperator.cmd.entity.Flight;
import com.yetistudios.rsww.touroperator.cmd.entity.Hotel;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateOfferCommand {

    @TargetAggregateIdentifier
    private String offerId;
    private Hotel hotel;
    private Double price;
    private Integer numberOfOffers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Flight> flights;

}
