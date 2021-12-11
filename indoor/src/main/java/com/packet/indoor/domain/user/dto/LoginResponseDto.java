package com.packet.indoor.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(builderClassName = "Builder")
@AllArgsConstructor
@Getter
public class LoginResponseDto {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private Long expiresIn;
    @JsonProperty("role")
    private String role;
    @JsonProperty("is_active")
    private Boolean isActive;

    public static LoginResponseDto create(String accessToken, Long expiresIn, String role, Boolean isActive){
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .expiresIn(expiresIn)
                .role(role)
                .isActive(isActive)
                .build();
    }
}
