package com.packet.indoor.domain.user.dto;

import com.packet.indoor.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class UserResponseDto {
    private String username;
    private String groupname;
    private Role role;

    protected UserResponseDto(){}
}
