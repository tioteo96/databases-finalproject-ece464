package com.packet.indoor.domain.assignedTag;

import com.packet.indoor.domain.BaseEntity;
import com.packet.indoor.domain.tag.Tag;
import com.packet.indoor.domain.user.User;

import lombok.*;

import java.time.LocalDateTime;

import javax.persistence.*;


@Builder(builderClassName = "Builder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assigned_tag")
@Entity
public class AssignedTag extends BaseEntity{
    
    @EmbeddedId
    private AssignedTagId assignedTagId;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "tagId")
    private Tag tag;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    public static AssignedTag create(User user, Tag tag, LocalDateTime registeredAt) {
        return AssignedTag.builder()
                .user(user)
                .tag(tag)
                .registeredAt(registeredAt)
                .build();
    }
}
