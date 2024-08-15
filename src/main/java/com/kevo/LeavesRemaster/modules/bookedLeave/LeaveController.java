package com.kevo.LeavesRemaster.modules.bookedLeave;

import com.kevo.LeavesRemaster.enums.Approval;
import com.kevo.LeavesRemaster.modules.user.User;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.UUID;

@DgsComponent
@RequiredArgsConstructor
public class LeaveController {
    private final LeaveService leaveService;
    @DgsQuery
    @Secured("VIEW_OWN_LEAVES")
    public List<BookedLeave> listAllLeavesByUserId(@AuthenticationPrincipal User user) {
        return leaveService.listLeavesByUserId(user.getUserId());
    }

    @DgsMutation
    @Secured("MANAGE_OWN_LEAVES")
    public BookedLeave bookLeave(@InputArgument @Valid LeaveDTO data) {
        return leaveService.bookLeave(data);
    }
    @DgsMutation
    public BookedLeave updateLeave(@InputArgument LeaveDTO data) {
        return null;
    }
    @DgsMutation
    public BookedLeave archiveLeaveById(@InputArgument UUID id) {
        return null;
    }
    @DgsMutation
    public BookedLeave addManagerApproval(@InputArgument UUID id, @InputArgument Approval approval) {
        return null;
    }
    @DgsMutation
    public BookedLeave addHrApproval(@InputArgument UUID id, @InputArgument Approval approval) {
        return null;
    }
    @DgsMutation
    public BookedLeave addLeaveComment(@InputArgument UUID id, @InputArgument String message) {
        return null;
    }
}
