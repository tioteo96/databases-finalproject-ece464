package com.packet.indoor.domain.user;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Builder(builderClassName = "Builder")
@Embeddable
public class Password {
    @Column(name = "password", length = 255, nullable = false)
    private String password;
    @Column(name = "salt", length = 10, nullable = false)
    private String salt;

    protected Password() {}

    public static Password create(String hashedPassword, String salt) {
        return Password.builder()
                .password(hashedPassword)
                .salt(salt)
                .build();
    }
}
