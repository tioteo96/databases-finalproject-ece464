package com.packet.indoor.domain.tag;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

@Embeddable
public class TagId implements Serializable{
    @GeneratedValue
    private Long id;
}
