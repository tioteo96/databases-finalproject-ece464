package com.packet.indoor.domain.assignedTag;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class AssignedTagId implements Serializable{
    @GeneratedValue
    private Long id;
}
