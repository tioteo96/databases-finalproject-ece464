package com.packet.indoor.service;

import com.packet.indoor.domain.user.PasswordService;
import com.packet.indoor.domain.user.User;
import com.packet.indoor.domain.user.Username;
import com.packet.indoor.domain.user.dto.LoginRequestDto;
import com.packet.indoor.domain.user.dto.LoginResponseDto;
import com.packet.indoor.exception.AuthenticationException;
import com.packet.indoor.exception.NotFoundException;
import com.packet.indoor.repository.user.JpaUserRepository;
import com.packet.indoor.util.ErrorMessage;
import com.packet.indoor.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private JwtUtil jwtUtil;
    private PasswordService passwordService;
    private JpaUserRepository jpaUserRepository;

    public LoginResponseDto verifyAndGenerateJsonWebToken(Username username, String rawPassword) {
        Optional<User> userOptional = jpaUserRepository.findUserByUsername(username);
        if (userOptional.isEmpty()) throw new NotFoundException(ErrorMessage.USER_NOT_FOUND);
        User user = userOptional.get();
        if (!passwordService.matchPassword(user.getPassword(), rawPassword)) throw new AuthenticationException(ErrorMessage.WRONG_PASSWORD);
        LoginResponseDto responseDto = jwtUtil.verifyAndGenerateJsonWebToken(user);
        return responseDto;
    }
}
