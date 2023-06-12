package com.yetistudios.rsww.rswwgateway.controller;

import com.yetistudios.rsww.common.messages.command.AdminGenerateOffersCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminOfferGeneratorController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/generate-offers")
    public void generateOffers(@RequestParam("amount") Integer amount) {
        commandGateway.sendAndWait(AdminGenerateOffersCommand.builder().numOffers(amount).build());
    }
}
