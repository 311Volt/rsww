package com.rsww.travelagency.command.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsww.travelagency.command.command.CreateAirportCommand;
import com.rsww.travelagency.command.dto.CreateAirportRequest;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TravelAgencyCommandService {

    private final CommandGateway commandGateway;

    public TravelAgencyCommandService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> createAirport(CreateAirportRequest createAirportRequest) {
        return commandGateway.send(new CreateAirportCommand(
                UUID.randomUUID().toString(),
                createAirportRequest.getCode(),
                createAirportRequest.getName(),
                createAirportRequest.getForDeparture()
        ));
    }

    public void initAirportData() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<CreateAirportRequest>> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/airports.json");
        try {
            List<CreateAirportRequest> airportRequests = mapper.readValue(inputStream, typeReference);
            for (CreateAirportRequest airportRequest : airportRequests) {
                commandGateway.send(new CreateAirportCommand(
                        UUID.randomUUID().toString(),
                        airportRequest.getCode(),
                        airportRequest.getName(),
                        airportRequest.getForDeparture()
                ));
            }
            log.info("Airports Saved!");
        } catch (IOException e) {
            log.info("Unable to save airports: " + e.getMessage());
        }
    }
}
