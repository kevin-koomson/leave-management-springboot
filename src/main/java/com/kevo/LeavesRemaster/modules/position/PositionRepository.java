package com.kevo.LeavesRemaster.modules.position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    public List<Position> findAllByIdIn (List<Long> ids);
}
