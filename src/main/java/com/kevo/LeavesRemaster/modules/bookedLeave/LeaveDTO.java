package com.kevo.LeavesRemaster.modules.bookedLeave;

import com.kevo.LeavesRemaster.enums.Approval;
import com.kevo.LeavesRemaster.modules.leaveType.LeaveType;
import com.kevo.LeavesRemaster.modules.organization.Organization;
import com.kevo.LeavesRemaster.modules.user.User;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class LeaveDTO {
    private UUID id;
    private Approval hrApproval;
    private Approval managerApproval;
    private Integer year;
    private Double daysOff;
    private Double carryOverUsed;
    private UUID leaveTypeId;
    private String createdAt;
    private String updatedAt;
    private List<LeaveDay> leaveDays;
    private UUID userId;
    private User manager;
    private User approvedByHr;
    private String comment;
    private UUID organizationId;
    private List<LeaveDocument> documents;
}
