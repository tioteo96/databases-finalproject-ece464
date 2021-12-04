package com.packet.indoor.domain.user;

import com.packet.indoor.domain.BaseEntity;
import com.packet.indoor.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder(builderClassName = "Builder")
@AllArgsConstructor
@NoArgsConstructor
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
    private Role role;

    public static User create(Username username, Password password, UserStatus userStatus, Role role) {
        return User.builder()
                .username(username)
                .password(password)
                .userStatus(userStatus)
                .role(role)
                .build();
    }
}
