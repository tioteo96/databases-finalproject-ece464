package com.packet.indoor.util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorMessage {

    MISSING_AUTHENTICATION("missing authorization header"),
    WRONG_AUTHORIZATION_FORMAT("authorization header does not start with Bearer"),
    INVALID_JWT_TOKEN("wrong jwt format"),
    USER_NOT_ACTIVE("user is not active"),
    USER_DELETED("user is deleted"),
    ;

    private String value;
    private ErrorMessage(String value){
        this.value = value;
    }
    @JsonValue
    public String value(){
        return value;
    }
}
