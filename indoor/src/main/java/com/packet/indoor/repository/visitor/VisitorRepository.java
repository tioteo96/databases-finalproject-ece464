package com.packet.indoor.repository.visitor;

import com.packet.indoor.domain.visitor.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VisitorRepository extends JpaRepository<Visitor, UUID> {
    Optional<Visitor> findByEmail(String email);
}
