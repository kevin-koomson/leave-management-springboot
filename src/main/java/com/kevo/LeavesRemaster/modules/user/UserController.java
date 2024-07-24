package com.kevo.LeavesRemaster.modules.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kevo.LeavesRemaster.codegen.types.LeaveEmployeeInfo;
import com.kevo.LeavesRemaster.codegen.types.LeaveOrganization;
import com.kevo.LeavesRemaster.codegen.types.LeavePosition;
import com.kevo.LeavesRemaster.codegen.types.LeaveUser;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfo;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfoService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmployeeInfoService infoService;

    @DgsMutation
    public LeaveUser upsertUser(@InputArgument String jsonPayload) throws JsonProcessingException {
        return userService.upsertUser(jsonPayload);
    }
    @DgsMutation
    public LeavePosition upsertPosition(@InputArgument String jsonPayload) throws JsonProcessingException {
        return infoService.upsertPosition(jsonPayload);
    }
    @DgsMutation
    public LeaveOrganization upsertOrganization(@InputArgument String jsonPayload) throws JsonProcessingException {
        return infoService.upsertOrganization(jsonPayload);
    }
    @DgsMutation
    public EmployeeInfo upsertEmployeeInfo(@InputArgument String jsonPayload) throws JsonProcessingException, IllegalAccessException {
        return infoService.upsertEmployeeInfo(jsonPayload);
    }

}
