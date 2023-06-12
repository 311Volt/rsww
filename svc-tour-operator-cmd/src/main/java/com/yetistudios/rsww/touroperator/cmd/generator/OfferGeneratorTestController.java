package com.yetistudios.rsww.touroperator.cmd.generator;

import com.yetistudios.rsww.common.messages.entity.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class OfferGeneratorTestController {

    @Autowired
    private OfferGenerator offerGenerator;

    @PostMapping("/generate-offers")
    public void generateOffers(@RequestParam("amount") Integer amount) {
        offerGenerator.generateOffers(amount);
    }

    @GetMapping("/example-random-offer")
    public Offer generateExampleOffer() {
        return offerGenerator.generateOffer();
    }
}
