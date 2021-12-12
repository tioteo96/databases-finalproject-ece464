package com.packet.indoor.repository.assignedTag;

import com.packet.indoor.domain.assignedTag.AssignedTag;
import com.packet.indoor.domain.assignedTag.AssignedTagId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignedTagRepository extends JpaRepository<AssignedTag, AssignedTagId>{
    
}
