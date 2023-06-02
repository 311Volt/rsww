package com.yetistudios.rsww.rswwgateway.event;

public class UserCreatedEvent extends BaseEvent<String>{

    private final String email;
    private final String password;

    public UserCreatedEvent(String id, String email, String password) {
        super(id);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
