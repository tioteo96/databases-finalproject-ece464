package com.packet.indoor.domain.tag;

import com.packet.indoor.domain.BaseEntity;
import com.packet.indoor.domain.tag.dto.TagResponseDto;
import com.packet.indoor.util.TagStatus;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@Builder(builderClassName = "Builder")
@Getter
@AllArgsConstructor
@Table(name = "tag")
@Entity
public class Tag extends BaseEntity{
    
    @Id
    private UUID id;

    @Column(length = 100)
    private String manufacturer;
    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag_status", nullable = false)
    private TagStatus tagStatus;

    protected Tag(){}

    public static Tag create(String manufacturer, String description) {
        return Tag.builder()
                .id(UUID.randomUUID())
                .manufacturer(manufacturer)
                .description(description)
                .tagStatus(TagStatus.AVAILABLE)
                .build();
    }

    public TagResponseDto toResponseDto(){
        return TagResponseDto.builder()
                .id(this.id.toString())
                .manufacturer(this.manufacturer)
                .description(this.description)
                .tagStatus(this.tagStatus)
                .build();
    }

}
