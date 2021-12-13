package com.packet.indoor.domain.tag;

import com.packet.indoor.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;


@Builder(builderClassName = "Builder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tag")
@Entity
public class Tag extends BaseEntity{
    
    @EmbeddedId
    private TagId tagId;
    private String manufacturer;
    private String description;

    @Column(nullable = false)
    @NonNull
    private Boolean use_status;

    public static Tag create(String manufacturer, String description, Boolean use_status) {
        return Tag.builder()
                .manufacturer(manufacturer)
                .description(description)
                .use_status(use_status)
                .build();
    }

}
