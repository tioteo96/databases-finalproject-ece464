package com.packet.indoor.domain.assignedTag;

import com.packet.indoor.domain.BaseEntity;
import com.packet.indoor.domain.tag.Tag;
import com.packet.indoor.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

import javax.persistence.*;


@Builder(builderClassName = "Builder")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assigned_tag")
@Entity
public class AssignedTag extends BaseEntity{
    
    @EmbeddedId
    private AssignedTagId assignedTagId;
    @Embedded
    @NonNull
    @OneToOne
    private User user;
    @Embedded
    @NonNull
    @OneToOne
    private Tag tag;
    @Embedded
    @NonNull
    private LocalDateTime registeredAt;
    @Embedded
    private LocalDateTime unregisteredAt;

    public static AssignedTag create(User user, Tag tag, LocalDateTime registeredAt) {
        return AssignedTag.builder()
                .user(user)
                .tag(tag)
                .registeredAt(registeredAt)
                .build();
    }
}
