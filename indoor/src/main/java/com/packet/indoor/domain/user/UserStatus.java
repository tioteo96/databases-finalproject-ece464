package com.packet.indoor.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Embeddable
public class UserStatus {
    @Column(name = "active", nullable = false)
    private Boolean isActive;
    @Column(name = "deleted", nullable = false)
    private Boolean isDeleted;

    protected UserStatus(){}

    public static UserStatus create() {
        return UserStatus.builder()
                .isActive(false)
                .isDeleted(false)
                .build();
    }
}
