package com.kevo.LeavesRemaster.modules.leaveType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LeaveTypeService {
    private final LeaveTypeRepository typeRepository;

    public List<LeaveType> listLeaveTypes() {
        return null;
    }

    public LeaveType getLeaveTypeById(UUID id) {
        return typeRepository.findById(id).orElseThrow(()->new NoSuchElementException("Leave type does not exist"));
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
    public LeaveType getAnnualDefault() {
        return typeRepository.findByAnnualDefaultIsTrue();
    }
}
