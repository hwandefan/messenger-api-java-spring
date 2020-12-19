package com.messenger.messenger.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

//Data handler for login
public class Login {
    @NotBlank
    private String login;
    @NotBlank
    private String password;

    public Login(@JsonProperty("login") String login,
            @JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
