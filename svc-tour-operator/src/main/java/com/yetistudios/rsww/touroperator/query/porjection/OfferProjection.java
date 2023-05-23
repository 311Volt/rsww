package com.yetistudios.rsww.touroperator.query.porjection;

import com.yetistudios.rsww.touroperator.command.repository.OfferRepository;
import com.yetistudios.rsww.touroperator.entity.Offer;
import com.yetistudios.rsww.touroperator.query.queries.GetOffersQuery;
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
