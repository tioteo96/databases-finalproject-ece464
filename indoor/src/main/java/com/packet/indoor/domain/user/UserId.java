package com.packet.indoor.domain.user;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Embeddable
public class UserId implements Serializable {
    @GeneratedValue
    private Long id;
}
