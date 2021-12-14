package com.packet.indoor.util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorMessage {

    MISSING_AUTHENTICATION("missing authorization header"),
    WRONG_AUTHORIZATION_FORMAT("authorization header does not start with Bearer"),
    INVALID_JWT_TOKEN("wrong jwt format"),
    USER_NOT_ACTIVE("user is not active"),
    USER_DELETED("user is deleted"),
    WRONG_PASSWORD("worng password"),

    USER_NOT_FOUND("user does not exist"),
    TAG_NOT_FOUND("tag does not exits")
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
