package com.kevo.LeavesRemaster.modules.position;

import com.kevo.LeavesRemaster.modules.leavePolicy.LeavePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    public List<Position> findAllByIdIn (List<Long> ids);
    public Position findByLeavePolicy_Id(UUID id);
}
