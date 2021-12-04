package com.packet.indoor.domain.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String username;
    private String groupname;
    private String password;
}
