package com.packet.indoor.domain.assignedTag;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

@Embeddable
public class AssignedTagId implements Serializable{
    @GeneratedValue
    private Long id;
}
