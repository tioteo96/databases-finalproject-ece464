package com.packet.indoor.domain.user;

import javax.persistence.Embeddable;

@Embeddable
public class Password {
    private String password;
    private String salt;
}
