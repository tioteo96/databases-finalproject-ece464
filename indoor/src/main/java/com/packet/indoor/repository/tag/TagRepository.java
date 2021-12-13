package com.packet.indoor.repository.tag;

import com.packet.indoor.domain.tag.Tag;
import com.packet.indoor.domain.tag.TagId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, TagId>{
    
}
