package com.kevo.LeavesRemaster.modules.leaveType;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaveTypeService {
    private final LeaveTypeRepository typeRepository;

    public List<LeaveType> listLeaveTypes() {
        return null;
    }

    public LeaveType getLeaveTypeById(UUID id) {
        return typeRepository.findById(id).orElseThrow();
    }

    public LeaveType saveLeaveType(LeaveType data) {
        return typeRepository.save(data);
    }

    public LeaveType updateLeaveType(LeaveType data) {
        LeaveType type = typeRepository.findById(data.getId()).orElseThrow();
        type.setName(data.getName());
        type.setDescription(data.getDescription());
        return typeRepository.save(type);
    }

    public LeaveType archiveLeaveType(UUID id) {
        LeaveType type = typeRepository.findById(id).orElseThrow();
        type.setDeleted(true);
        return typeRepository.save(type);
    }
}
