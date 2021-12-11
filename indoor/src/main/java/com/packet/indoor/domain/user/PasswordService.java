package com.packet.indoor.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Random;

@AllArgsConstructor
@Component
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    public Password hashPasswordWithSalt(String rawPassword){
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String salt = new String(array, Charset.forName("UTF-8"));

        String hashedPassword = passwordEncoder.encode(rawPassword+salt);
        Password password = Password.create(hashedPassword, salt);
        return password;
    }

    public Boolean matchPassword(Password password, String rawPassword) {
        return passwordEncoder.matches(rawPassword+password.getSalt(), password.getPassword());
    }
}
