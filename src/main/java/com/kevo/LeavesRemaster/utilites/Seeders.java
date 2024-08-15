package com.kevo.LeavesRemaster.utilites;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevo.LeavesRemaster.enums.Permissions;
import com.kevo.LeavesRemaster.modules.accessLevel.AccessLevel;
import com.kevo.LeavesRemaster.modules.accessLevel.AccessLevelRepository;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfo;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfoRepository;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfoService;
import com.kevo.LeavesRemaster.modules.leavePolicy.LeavePolicy;
import com.kevo.LeavesRemaster.modules.leavePolicy.LeavePolicyRepository;
import com.kevo.LeavesRemaster.modules.leavePolicy.LeavePolicyService;
import com.kevo.LeavesRemaster.modules.leaveType.LeaveType;
import com.kevo.LeavesRemaster.modules.leaveType.LeaveTypeRepository;
import com.kevo.LeavesRemaster.modules.leaveType.LeaveTypeService;
import com.kevo.LeavesRemaster.modules.organization.Organization;
import com.kevo.LeavesRemaster.modules.organization.OrganizationRepository;
import com.kevo.LeavesRemaster.modules.position.Position;
import com.kevo.LeavesRemaster.modules.position.PositionRepository;
import com.kevo.LeavesRemaster.modules.user.User;
import com.kevo.LeavesRemaster.modules.user.UserRepository;
import com.kevo.LeavesRemaster.modules.user.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.money.MaxValidatorForMonetaryAmount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Seeders {
    private final JsonProcessingService jsonProcessingService;
    private final PositionRepository positionRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final EmployeeInfoService employeeInfoService;
    private final UserService userService;
    private final EmployeeInfoRepository employeeInfoRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveTypeService leaveTypeService;
    private final LeavePolicyService leavePolicyService;
    private final LeavePolicyRepository leavePolicyRepository;
    private final AccessLevelRepository accessLevelRepository;
    @Value("classpath:testFiles/positionsTable.json")
    private Resource positionsFile;
    @Value("classpath:testFiles/clientsTable.json")
    private Resource clientsFile;
    @Value("classpath:testFiles/clientAddress.json")
    private Resource clientAddressFile;
    @Value("classpath:testFiles/bioTable.json")
    private Resource bioTable;
    @Value("classpath:testFiles/infoTable.json")
    private Resource infoTable;
    @Value("classpath:testFiles/contactTable.json")
    private Resource contactTable;

    private final ObjectMapper objectMapper;

    public void dbSetup() throws IOException {
        seedClients();
        leaveTypes();
        seedPolicies();
        seedPositions();
        seedAccessLevels();
        seedUsers();
    }

    private void seedAccessLevels() {
        List<Permissions> employeePermissions = List.of(
                Permissions.VIEW_OWN_LEAVES,
                Permissions.MANAGE_OWN_LEAVES
        );
        List<Permissions> managerPermissions = new ArrayList<>(List.copyOf(employeePermissions));
        managerPermissions.addAll(
                List.of(Permissions.VIEW_MANAGER_DASHBOARD,Permissions.MANAGE_MANAGER_DASHBOARD)
        );
        List<Permissions> hrMember = new ArrayList<>(List.copyOf(employeePermissions));
        hrMember.add(Permissions.VIEW_HR_DASHBOARD);


        AccessLevel employee = new AccessLevel();
        employee.setName("Employee Access Level");
        employee.setDescription("Default access level for all employees");
        employee.setPermissions(employeePermissions);

        AccessLevel manager = new AccessLevel();
        manager.setName("Manager Access Level");
        manager.setDescription("Access level for Line Managers");
        manager.setPermissions(managerPermissions);

        AccessLevel hrAttendant = new AccessLevel();
        hrAttendant.setName("HR Access Level");
        hrAttendant.setDescription("Access level for HR attendant");
        hrAttendant.setPermissions(hrMember);

        AccessLevel fullAdmin = new AccessLevel();
        fullAdmin.setName("Super Admin Access Level");
        fullAdmin.setDescription("Access level with all permissions available");
        fullAdmin.setPermissions(List.of(Permissions.values()));

        accessLevelRepository.saveAll(List.of(employee, manager, fullAdmin, hrAttendant));
    }

    private void seedPolicies() {
        // loop through positions, divide total number by 3,
        Map<String, Double> map = new HashMap<>();
        LeaveType annualDefault = leaveTypeService.getAnnualDefault();
        map.put("Junior Policy", 18.0);
        map.put("Associate Policy", 22.0);
        map.put("Senior Policy", 25.0);

        map.forEach((item, value)->{
            LeavePolicy policy = new LeavePolicy();
            policy.setName(item);
            policy.setLeaveType(annualDefault);
            policy.setMaxAccrual(value);
            leavePolicyRepository.save(policy);
        });
    }

    private void seedUsers() throws IOException {
        seedBio();
        seedInfo();
    }
    private void seedPositions() throws IOException {
        JsonNode rows = objectMapper.readTree(positionsFile.getInputStream()).get("rows");
        List<Position> positionList = new ArrayList<>();
        List<LeavePolicy> policies = leavePolicyService.listLeavePolicies();
        int length = policies.size();
        if(rows.isArray()) {
            for(JsonNode node : rows) {
                Position position = objectMapper.treeToValue(node, Position.class);
                position.setLeavePolicy(policies.get(--length));
                if(length == 0) length = policies.size();
                positionList.add(position);
            }
        }
        positionRepository.saveAll(positionList);
    }
    private void seedClients() throws IOException {
        JsonNode clientRows = objectMapper.readTree(clientsFile.getInputStream()).get("rows");
        JsonNode addressRows = objectMapper.readTree(clientAddressFile.getInputStream()).get("rows");
        Map<Long, Organization> organizationMap = new HashMap<>();
        if(clientRows.isArray()) {
            for(JsonNode node : clientRows) {
                if(node.get("isOrganization").asBoolean()){
                    Organization organization = Organization.builder()
                            .id(node.get("id").asLong())
                            .name(node.get("client_name").asText())
                            .build();
                    organizationMap.put(organization.getId(), organization);
                }
            }
        }
        if(addressRows.isArray()){
            for(JsonNode node: addressRows) {
                Long id = node.get("id").asLong();
                if(organizationMap.containsKey(id)) {
                    Organization organization = organizationMap.get(id);
                    organization.setCountry(node.get("country").asText());
                    organizationMap.put(id, organization);
                }
            }
        }
        organizationRepository.saveAll(organizationMap.values());
    }
    private void seedInfo() throws IOException {
        JsonNode infoNode = objectMapper.readTree(infoTable.getInputStream()).get("rows");
        if(infoNode.isArray()){
            for(JsonNode row: infoNode) {
                try {
                    EmployeeInfo info = EmployeeInfo.builder()
                            .id(row.get("id").asLong())
                            .active(row.get("active").asBoolean())
                            .effective_date(LocalDateTime.parse(row.get("effective_date").asText(), DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")))
                            .end_date(LocalDateTime.parse(row.get("effective_date").asText(), DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")))
                            .organization(employeeInfoService.getOrganizationById(row.get("organization_id").asLong()))
                            .user(userService.getUserByUserId(row.get("user_id").asLong()))
                            .position(employeeInfoService.getPositionById(row.get("position_id").asLong()))
                            .manager(userService.getUserByUserId(row.get("manager_id").asLong()))
                            .build();
                    employeeInfoRepository.save(info);
                } catch (Exception e){
//                    System.out.println(row.asText() + " " + e.getMessage());
                }
            }
        }
    }
    private void leaveTypes() {
        String[] types = {"Annual Leave", "Sick Leave", "Compensatory Leave", "Maternity Leave"};
        for(int i = 0; i< types.length; i++){
            leaveTypeRepository.save(
                    LeaveType.builder()
                            .annualDefault(i==0)
                            .name(types[i])
                            .deleted(false)
                            .build()
            );
        }
    }
    private void seedBio() throws IOException {
        Map<Long, User> map = new HashMap<>();
        JsonNode bioNode = objectMapper.readTree(bioTable.getInputStream()).get("rows");

        if(bioNode.isArray()){
            for(JsonNode row : bioNode){
                Long id = row.get("user_id").asLong();
                AccessLevel employeeAccess = accessLevelRepository.findByName("Employee Access Level");
                User user = new User();
                user.setUserId(id);
                user.setFirstName(row.get("first_name").asText());
                user.setLastName(row.get("full_name").asText());
                user.setFullName(row.get("full_name").asText());
                user.setProfileImage(row.get("profile_image").asText());
                user.setAccessLevel(employeeAccess);

                map.put(id, user);
            }
        }
        JsonNode contactNode = objectMapper.readTree(contactTable.getInputStream()).get("rows");
        if(contactNode.isArray()){
            for(JsonNode row : contactNode){
                Long id = row.get("user_id").asLong();
                if(map.containsKey(id)){
                    User user = map.get(id);
                    user.setEmail(row.get("work_email").asText());
                    map.put(id,user);
                }
            }
        }
        userRepository.saveAll(map.values());
    }
}
