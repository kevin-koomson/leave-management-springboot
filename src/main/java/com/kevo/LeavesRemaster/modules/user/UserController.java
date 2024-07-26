package com.kevo.LeavesRemaster.modules.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kevo.LeavesRemaster.codegen.types.LeaveOrganization;
import com.kevo.LeavesRemaster.codegen.types.LeavePosition;
import com.kevo.LeavesRemaster.codegen.types.LeaveUser;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfo;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfoService;
import com.kevo.LeavesRemaster.modules.organization.Organization;
import com.kevo.LeavesRemaster.modules.position.Position;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmployeeInfoService infoService;

    @DgsQuery
    public List<User> listLeaveUsers (){
        return userService.listUsers();
    }
    @DgsQuery
    public List<Organization> listLeaveOrganizations(){
        return userService.listOrganizations();
    }
    @DgsQuery
    public List<Position> listLeavePositions(){
        return userService.listPositions();
    }
    @DgsQuery
    public List<EmployeeInfo> listEmployeeInfoByUserId(@InputArgument String user_id) {
        return userService.listEmployeeInfosByUserId(Long.parseLong(user_id));
    }

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
    @DgsMutation
    public User upsertUserBio(@InputArgument String jsonPayload) throws JsonProcessingException {
        return userService.upsertUserBio(jsonPayload);
    }
    @DgsMutation
    public User upsertUserContact(@InputArgument String jsonPayload) throws JsonProcessingException {
        return userService.upsertUserContact(jsonPayload);
    }

}
