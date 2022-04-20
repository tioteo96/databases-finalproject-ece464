package com.packet.indoor.domain.visitor;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VisitorType {

    STUDENT("STUDENT"),
    FACULTY("FACULTY"),
    GUEST("GUEST"),
    ;

    private String value;
    private VisitorType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

}
