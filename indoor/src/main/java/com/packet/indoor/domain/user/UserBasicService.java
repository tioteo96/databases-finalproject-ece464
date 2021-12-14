package com.packet.indoor.domain.user;

import com.packet.indoor.exception.NotFoundException;
import com.packet.indoor.repository.user.JpaUserRepository;
import com.packet.indoor.util.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserBasicService {

    private JpaUserRepository jpaUserRepository;

    public User findUser(String username) {
        Optional<User> userOptional = jpaUserRepository.findUserByUsernameName(username);
        if (userOptional.isEmpty()) throw new NotFoundException(ErrorMessage.USER_NOT_FOUND);
        return userOptional.get();
    }

}
