package com.packet.indoor.domain.assignedTag;

import com.packet.indoor.domain.BaseEntity;
import com.packet.indoor.domain.assignedTag.dto.AssignedTagResponseDto;
import com.packet.indoor.domain.tag.Tag;
import com.packet.indoor.domain.user.User;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;


@Builder(builderClassName = "Builder")
@Getter
@AllArgsConstructor
@Table(name = "assigned_tag")
@Entity
public class AssignedTag extends BaseEntity{
    
    @Id
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tagId")
    private Tag tag;

    @Column(name = "assigned", nullable = false)
    private Boolean assigned;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    @Column(name = "unassigned_at")
    private LocalDateTime unAssignedAt;

    protected AssignedTag(){}

    public static AssignedTag create(User user, Tag tag, LocalDateTime assignedAt) {
        return AssignedTag.builder()
                .id(UUID.randomUUID())
                .user(user)
                .tag(tag)
                .assigned(true)
                .assignedAt(assignedAt)
                .build();
    }

    public AssignedTagResponseDto toResponseDto() {
        return AssignedTagResponseDto.builder()
                .username(this.user.getUsername().toString())
                .tagId(this.tag.getId().toString())
                .manufacturer(this.tag.getManufacturer())
                .tag_description(this.tag.getDescription())
                .assignedAt(this.assignedAt)
                .unAssignedAt(this.unAssignedAt)
                .build();
    }

    public void unAssign() {
        this.assigned = false;
        this.unAssignedAt = LocalDateTime.now();
        this.tag.unAssign();
    }
}
