package com.kevo.LeavesRemaster.modules.bookedLeave;

import com.kevo.LeavesRemaster.codegen.types.LeaveDayInput;
import com.kevo.LeavesRemaster.enums.Approval;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class LeaveDTO {
    private UUID id;
    private Approval hrApproval = Approval.PENDING;
    private Approval managerApproval = Approval.PENDING;
    @Positive(message = "Please enter valid year")
    private Integer year;
    @Positive(message = "Please enter valid carry over used")
    private Double carryOverUsed = 0.0;
    @NotEmpty(message = "Please provide a valid Leave Type")
    private UUID leaveTypeId;
    private List<LeaveDayInput> leaveDays;
    private Long userId;
    private Long manager;
    private Long approvedByHr;
    private String comment;
    private List<LeaveDocument> documents;

    public BookedLeave createBookLeave() {
        Set<LeaveDay> days  = new HashSet<>(processLeaveDays(leaveDays));
        return BookedLeave.builder()
                .hrApproval(hrApproval)
                .managerApproval(managerApproval)
                .daysOff(sumLeaveDays())
                .carryOverUsed(carryOverUsed)
                .leaveDays(days)
                .comments(List.of(processComment()))
                .documents(documents)
                .build();
    }
    private Double sumLeaveDays() {
        return leaveDays.stream()
                .mapToDouble(LeaveDayInput::getDuration)
                .sum();
    }
    private List<LeaveDay> processLeaveDays(List<LeaveDayInput> days) {
        return days.stream().map(
                day-> LeaveDay.builder()
                        .date(LocalDateTime.parse(day.getDate()))
                        .duration(day.getDuration())
                        .build()).toList();
    }
    private Comment processComment() {
        return Comment.builder()
                .message(comment)
                .build();
    }
}
