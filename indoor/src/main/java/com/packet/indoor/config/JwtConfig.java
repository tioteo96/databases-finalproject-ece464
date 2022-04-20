package com.packet.indoor.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class JwtConfig {
    @Value("${jwt.static-token}")
    private String token;
    @Value("${jwt.enabled}")
    private Boolean enabled;
}
