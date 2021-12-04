package com.packet.indoor.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Getter
@Embeddable
public class UserId implements Serializable {

    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "usr_id")
    private UUID id;

    protected UserId(){}

    public static UserId create(){
        return UserId.builder()
                .id(UUID.randomUUID())
                .build();
    }
}
