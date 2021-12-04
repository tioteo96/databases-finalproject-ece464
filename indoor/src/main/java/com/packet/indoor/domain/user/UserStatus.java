package com.packet.indoor.domain.user;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder(builderClassName = "Builder")
@Embeddable
public class UserStatus {
    @Column(name = "active", nullable = false)
    private Boolean isActive;
    @Column(name = "deleted", nullable = false)
    private Boolean isDeleted;

    protected UserStatus(){}

    public UserStatus create() {
        return UserStatus.builder()
                .isActive(false)
                .isDeleted(false)
                .build();
    }
}
