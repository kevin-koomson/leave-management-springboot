package com.kevo.LeavesRemaster.modules.bookedLeave;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;

    public BookedLeave bookedLeave(LeaveDTO leaveDTO) {
        return null;
    }
}
