package com.packet.indoor.service;

import com.packet.indoor.domain.user.User;
import com.packet.indoor.domain.user.dto.LoginRequestDto;
import com.packet.indoor.repository.user.JpaUserRepository;
import com.packet.indoor.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private JpaUserRepository jpaUserRepository;

    public AuthenticationResponseDto verifyAndCreateJsonWebToken(String email, String password){
        User user = userService.findUser(email);

        if(!passwordEncoder.matches(password, user.getPassword())) throw new CustomAuthenticationException(ErrorMessage.INVALID_PASSWORD);
        String accessToken = jwtUtil.verifyAndGenerateJsonWebToken(user);
        AuthenticationResponseDto authenticationResponseDto = AuthenticationResponseDto.create(accessToken, JwtUtil.JWT_TOKEN_VALIDITY, user.getRole().getValue(), user.getIsActive());

        return authenticationResponseDto;
    }

    public String verifyAndGenerateJsonWebToken(LoginRequestDto requestDto) {
        Optional<User> userOptional = jpaUserRepository.findUserByUsername()
    }
}
