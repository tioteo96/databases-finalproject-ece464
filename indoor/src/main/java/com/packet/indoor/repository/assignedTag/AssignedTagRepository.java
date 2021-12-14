package com.packet.indoor.repository.assignedTag;

import com.packet.indoor.domain.assignedTag.AssignedTag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssignedTagRepository extends JpaRepository<AssignedTag, UUID>{
    
}
