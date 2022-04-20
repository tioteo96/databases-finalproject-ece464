package com.packet.indoor.domain.board;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BoardStatus {

    ASSIGNED("ASSIGNED"),
    AVAILABLE("AVAILABLE"),
    ;

    private String value;
    private BoardStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {return value;}
}
