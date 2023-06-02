package com.yetistudios.rsww.touroperator.cmd.query.controller;

import com.yetistudios.rsww.touroperator.cmd.entity.Offer;
import com.yetistudios.rsww.touroperator.cmd.query.queries.GetOffersQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferQuertController {

    private QueryGateway queryGateway;

    public OfferQuertController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<Offer> getAllOffers() {
        GetOffersQuery getOffersQuery = new GetOffersQuery();

        return queryGateway.query(getOffersQuery, ResponseTypes.multipleInstancesOf(Offer.class)).join();
    }
}
