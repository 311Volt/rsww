package com.yetistudios.rsww.travelagency.command.command;

public class CreateAirportCommand extends BaseCommand<String>{
    private final String code;
    private final String name;
    private final Boolean forDeparture;

    public CreateAirportCommand(String id, String code, String name, Boolean forDeparture) {
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
