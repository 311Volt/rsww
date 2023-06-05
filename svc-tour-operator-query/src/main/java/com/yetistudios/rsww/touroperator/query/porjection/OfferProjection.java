package com.yetistudios.rsww.touroperator.query.porjection;

import com.yetistudios.rsww.touroperator.query.repository.OfferRepository;
import com.yetistudios.rsww.touroperator.query.entity.Offer;
import com.yetistudios.rsww.touroperator.query.dto.DetailedOfferDto;
import com.yetistudios.rsww.touroperator.query.queries.GetOfferDetailedQuery;
import com.yetistudios.rsww.touroperator.query.queries.GetOffersQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OfferProjection {
    private OfferRepository offerRepository;

    public OfferProjection(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @QueryHandler
    public List<Offer> handle(GetOffersQuery getOffersQuery){
        List<Offer> offers =  offerRepository.findAll();
        return offers;
    }

    @QueryHandler DetailedOfferDto handle(GetOfferDetailedQuery getOfferDetailedQuery){
        Optional<Offer> offerOptional = offerRepository.findById(getOfferDetailedQuery.getOfferId());
        if(offerOptional.isPresent()){
            Offer offer = offerOptional.get();

            DetailedOfferDto detailedOffer = DetailedOfferDto.builder()

                    .build();
        }

        return null;
    }
}
