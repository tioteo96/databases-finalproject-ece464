package com.packet.indoor.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class UserCreateResponseDto {
    private String username;
    private String groupname;

    protected UserCreateResponseDto(){}
}
