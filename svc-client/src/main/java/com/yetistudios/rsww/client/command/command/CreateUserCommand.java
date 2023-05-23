package com.yetistudios.rsww.client.command.command;

public class CreateUserCommand extends BaseCommand<String>{

    private final String email;
    private final String password;

    public CreateUserCommand(String id, String email, String password) {
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
