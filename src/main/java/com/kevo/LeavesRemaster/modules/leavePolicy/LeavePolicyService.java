package com.kevo.LeavesRemaster.modules.leavePolicy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevo.LeavesRemaster.modules.leaveType.LeaveTypeService;
import com.kevo.LeavesRemaster.modules.position.Position;
import com.kevo.LeavesRemaster.modules.position.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeavePolicyService {
    private final LeavePolicyRepository policyRepository;
    private final PositionRepository positionRepository;
    private final LeaveTypeService leaveTypeService;

    public LeavePolicy getLeavePolicyById(UUID id) {
        return policyRepository.findById(id).orElseThrow();
    }

    public List<LeavePolicy> listLeavePolicies() {
        return policyRepository.findAll();
    }

    public LeavePolicy createLeavePolicy(LeavePolicyDTO policyDTO) {
        LeavePolicy policy = policyDTO.createLeavePolicy();
        policy.setLeaveType(leaveTypeService.getLeaveTypeById(policyDTO.getLeaveTypeId()));
        policy.setPositions(parsePositionsFromInput(policyDTO.getPositions()));
        System.out.println(policy);
        return policyRepository.save(policy);
    }

    public Set<Position> parsePositionsFromInput(List<Long> input) {
        return new HashSet<>(positionRepository.findAllByIdIn(input));
    }
}
