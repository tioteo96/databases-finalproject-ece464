package com.packet.indoor.repository.assignedTag;

import com.packet.indoor.domain.assignedTag.AssignedTag;

import com.packet.indoor.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssignedTagRepository extends JpaRepository<AssignedTag, UUID>{
    Optional<AssignedTag> findByUserAndAssignedIsTrue(User user);
    List<AssignedTag> findAllByAssignedIsTrue();
}
