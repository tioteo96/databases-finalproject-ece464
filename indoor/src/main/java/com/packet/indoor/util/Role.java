package com.packet.indoor.util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    ;

    private String value;
    private Role(String value) {
        this.value = value;
    }
    @JsonValue
    public String value() {
        return value;
    }
}
