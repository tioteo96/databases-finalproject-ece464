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
    TAG_NOT_FOUND("tag does not exits"),
    BOARD_NOT_FOUND("board does not exists"),

    USER_ALREADY_ASSIGNED("user is already assigned to a tag"),
    USER_NOT_ASSIGNED("user does not have an assigned tag"),

    BOARD_ALREADY_ASSIGNED("Board is already assigned"),
    BOARD_ALREADY_UNASSIGNED("Board is already unassigned"),
    VISITOR_ALREADY_ASSIGNED("Visitor is already assigned"),
    DATA_NOT_SYNCED("Data Not Synced Properly"),

    BOARD_NAME_REQUIRED("Board Name Required"),
    USERNAME_REQUIRED("Username Required"),
    VISITOR_TYPE_REQUIRED("Visitor Type Required"),
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
