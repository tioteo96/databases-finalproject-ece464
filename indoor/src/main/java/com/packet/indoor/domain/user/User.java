package com.packet.indoor.domain.user;

import com.packet.indoor.domain.BaseEntity;
import com.packet.indoor.domain.user.dto.UserResponseDto;
import com.packet.indoor.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Builder(builderClassName = "Builder")
@Getter
@AllArgsConstructor
@Table(name = "usr")
@Entity
public class User extends BaseEntity {

    @EmbeddedId
    private UserId userId;
    @Embedded
    private Username username;
    @Embedded
    private Password password;
    @Embedded
    private UserStatus userStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "usr_role")
    private Role role;

    protected User(){}

    public static User create(UserId userId, Username username, Password password, UserStatus userStatus, Role role) {
        return User.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .userStatus(userStatus)
                .role(role)
                .build();
    }

    public UserResponseDto toResponseDto(){
        return UserResponseDto.builder()
                .username(this.username.getName())
                .groupname(this.username.getGroup())
                .role((this.role))
                .build();
    }
}
