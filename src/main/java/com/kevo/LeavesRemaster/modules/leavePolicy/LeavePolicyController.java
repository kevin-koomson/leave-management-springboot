package com.kevo.LeavesRemaster.modules.leavePolicy;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@DgsComponent
@RequiredArgsConstructor
public class LeavePolicyController {
    private final LeavePolicyService policyService;

    @DgsQuery
    public LeavePolicy getLeavePolicyById(@InputArgument UUID id) {
        return policyService.getLeavePolicyById(id);
    }
    @DgsQuery
    public List<LeavePolicy> listLeavePolicies() {
        return policyService.listLeavePolicies();
    }
    @DgsMutation
    public LeavePolicy createLeavePolicy(@InputArgument @Valid LeavePolicyDTO data) {
        return policyService.createLeavePolicy(data);
        // cb12f00c-58dd-46e7-a561-6bba0d16b065
    }
}
