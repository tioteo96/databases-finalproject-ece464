package com.packet.indoor.repository.anchor;

import java.util.Optional;

import com.packet.indoor.domain.anchor.Anchor;
import com.packet.indoor.domain.anchor.AnchorId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnchorRepository extends JpaRepository<Anchor,AnchorId>{
    //could be defined as findByAnchorIdId if I only wanted to filter by anchorId.id
    // Optional<Anchor> findByAnchorId(AnchorId anchorId);
}
