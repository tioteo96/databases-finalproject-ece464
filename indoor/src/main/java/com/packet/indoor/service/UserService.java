package com.packet.indoor.service;

import com.packet.indoor.domain.user.*;
import com.packet.indoor.domain.user.dto.UserCreateRequestDto;
import com.packet.indoor.domain.user.dto.UserResponseDto;
import com.packet.indoor.repository.user.JpaUserRepository;
import com.packet.indoor.util.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private PasswordService passwordService;
    private JpaUserRepository jpaUserRepository;

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserResponseDto createAdmin(UserCreateRequestDto userCreateRequestDto){
        UserId userId = UserId.create();
        Username username = Username.create(userCreateRequestDto.getUsername(), userCreateRequestDto.getGroupname());
        Password password = passwordService.hashPasswordWithSalt(userCreateRequestDto.getPassword());
        UserStatus userStatus = UserStatus.create();
        User admin = User.create(userId, username, password, userStatus, Role.ROLE_ADMIN);
        jpaUserRepository.save(admin);
        return admin.toResponseDto();
    }

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto){
        UserId userId = UserId.create();
        Username username = Username.create(userCreateRequestDto.getUsername(), userCreateRequestDto.getGroupname());
        Password password = passwordService.hashPasswordWithSalt(userCreateRequestDto.getPassword());
        UserStatus userStatus = UserStatus.create();
        User user = User.create(userId, username, password, userStatus, Role.ROLE_USER);
        jpaUserRepository.save(user);
        return user.toResponseDto();
    }

    @Transactional
    public List<UserResponseDto> findAllActiveUsers() {
        Role roleUser = Role.ROLE_USER;
        UserStatus activeStatus = UserStatus.active();
        List<User> users = jpaUserRepository.findAllByRoleAndUserStatus(roleUser, activeStatus);

        List<UserResponseDto> responseDtos = users.stream()
                .map(user -> user.toResponseDto())
                .collect(Collectors.toList());
        return responseDtos;
    }

}
