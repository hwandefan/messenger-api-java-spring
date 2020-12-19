package com.messenger.messenger.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Chat {
    private final UUID id;
    @NotBlank
    private final UUID SenderId;
    @NotBlank
    private final UUID RecieverId;
    @NotBlank
    private final int encodeStep;
    @NotBlank
    private final String message;

    public Chat(@JsonProperty("id") UUID id,
                @JsonProperty("senderid")UUID senderId,
                @JsonProperty("recieverid") UUID recieverId,
                @JsonProperty("endoce") int encodeStep,
                @JsonProperty("msg") String message) {
        this.id = id;
        SenderId = senderId;
        RecieverId = recieverId;
        this.encodeStep = encodeStep;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSenderId() {
        return SenderId;
    }

    public UUID getRecieverId() {
        return RecieverId;
    }

    public int getEncodeStep() {
        return encodeStep;
    }

    public String getMessage() {
        return message;
    }

}
