package com.packet.indoor.repository.user;

import com.packet.indoor.domain.user.User;
import com.packet.indoor.domain.user.UserId;
import com.packet.indoor.domain.user.UserStatus;
import com.packet.indoor.domain.user.Username;
import com.packet.indoor.util.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, UserId>, UserRepository{
    Optional<User> findUserByUsername(Username username);

    Optional<User> findUserByUsernameName(String username);

    List<User> findAllByRoleAndUserStatus(Role role, UserStatus userStatus);

    List<User> findAllByUserStatus(UserStatus userStatus);
}
