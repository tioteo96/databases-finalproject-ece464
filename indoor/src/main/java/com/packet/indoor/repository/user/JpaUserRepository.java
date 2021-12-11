package com.packet.indoor.repository.user;

import com.packet.indoor.domain.user.User;
import com.packet.indoor.domain.user.UserId;
import com.packet.indoor.domain.user.Username;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, UserId>, UserRepository{
    Optional<User> findUserByUsername(Username username);
}
