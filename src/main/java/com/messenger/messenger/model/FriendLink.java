package com.messenger.messenger.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class FriendLink {
    @NotBlank
    private final UUID id;
    @NotBlank
    private final UUID idUser;
    @NotBlank
    private final UUID idFriend;

    public FriendLink(@JsonProperty("id") UUID id, @JsonProperty("iduser") UUID idUser, @JsonProperty("idfriend") UUID idFriend) {
        this.id = id;
        this.idUser = idUser;
        this.idFriend = idFriend;
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public UUID getIdFriend() {
        return idFriend;
    }
}
