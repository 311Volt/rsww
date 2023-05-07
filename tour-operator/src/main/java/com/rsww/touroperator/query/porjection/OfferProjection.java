package com.rsww.touroperator.query.porjection;

import com.rsww.touroperator.command.repository.OfferRepository;
import com.rsww.touroperator.entity.Offer;
import com.rsww.touroperator.query.queries.GetOffersQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OfferProjection {
    private OfferRepository offerRepository;

    public OfferProjection(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @QueryHandler
    public List<Offer> handle(GetOffersQuery getOffersQuery){
        return offerRepository.findAll();
    }
}
