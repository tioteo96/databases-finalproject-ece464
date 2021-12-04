package com.packet.indoor.domain.packet;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

@Embeddable
public class PacketId implements Serializable {
    @GeneratedValue
    private Long id;
}
