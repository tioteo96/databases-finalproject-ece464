package com.packet.indoor.domain.anchor;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

@Embeddable
public class AnchorId implements Serializable{
    @GeneratedValue
    private Long id;
}
