package com.packet.indoor.domain.visitor;

import com.packet.indoor.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Builder(builderClassName = "Builder")
@Getter
@AllArgsConstructor
@Table(name = "visitor")
@Entity
public class Visitor extends BaseEntity {

    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "visitor_id")
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "visitor_type", nullable = false)
    private VisitorType type;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    protected Visitor(){}

    public static Visitor create(VisitorType visitorType, String username, String email){
        return Visitor.builder()
                .type(visitorType)
                .username(username)
                .email(email)
                .build();
    }
}
