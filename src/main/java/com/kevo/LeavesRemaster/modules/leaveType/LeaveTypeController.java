package com.kevo.LeavesRemaster.modules.leaveType;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@DgsComponent
@RequiredArgsConstructor
public class LeaveTypeController {
    private final LeaveTypeService typeService;

    @DgsQuery
    public List<LeaveType> listLeaveTypes() {
        return typeService.listLeaveTypes();
    }
    @DgsQuery
    public LeaveType getLeaveTypeById(@InputArgument UUID id) {
        return typeService.getLeaveTypeById(id);
    }
    @DgsMutation
    public LeaveType createLeaveType(@InputArgument LeaveType data) {
        return typeService.saveLeaveType(data);
    }
    @DgsMutation
    public LeaveType updateLeaveType(@InputArgument LeaveType data) {
        return typeService.updateLeaveType(data);
    }
    @DgsMutation
    public LeaveType archiveLeaveType(@InputArgument UUID id) {
        return typeService.archiveLeaveType(id);
    }
}
