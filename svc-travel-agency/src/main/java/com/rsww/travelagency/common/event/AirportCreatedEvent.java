package com.rsww.travelagency.common.event;

public class AirportCreatedEvent extends BaseEvent<String>{

    private final String code;
    private final String name;
    private final Boolean forDeparture;

    public AirportCreatedEvent(String id, String code, String name, Boolean forDeparture) {
        super(id);
        this.code = code;
        this.name = name;
        this.forDeparture = forDeparture;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Boolean getForDeparture() {
        return forDeparture;
    }
}
