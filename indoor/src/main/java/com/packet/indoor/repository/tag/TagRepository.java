package com.packet.indoor.repository.tag;

import com.packet.indoor.domain.tag.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID>{
    
}
