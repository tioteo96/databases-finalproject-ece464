package com.packet.indoor.repository.anchor;

import com.packet.indoor.domain.anchor.Anchor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnchorRepository extends JpaRepository<Anchor, UUID>{
    //could be defined as findByAnchorIdId if I only wanted to filter by anchorId.id
    // Optional<Anchor> findByAnchorId(AnchorId anchorId);


}
