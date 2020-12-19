package com.messenger.messenger.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class User {
    private final UUID id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String login;
    @NotBlank
    private final String password;

    public User(@JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("login") String login,
                @JsonProperty("password") String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
