package com.packet.indoor.util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TagStatus {

    ASSIGNED("ASSIGNED"),
    AVAILABLE("AVAILABLE"),
    ;

    private String value;
    private TagStatus(String value) {
        this.value = value;
    }
    @JsonValue
    public String value() {
        return value;
    }
}
