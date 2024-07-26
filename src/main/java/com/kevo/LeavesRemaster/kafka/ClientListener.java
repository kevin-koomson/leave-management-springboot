package com.kevo.LeavesRemaster.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kevo.LeavesRemaster.modules.accessLevel.AccessLevelService;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfoService;
import com.kevo.LeavesRemaster.modules.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientListener {
    public final UserService userService;
    public final EmployeeInfoService infoService;
    private final AccessLevelService accessLevelService;

    @KafkaListener(topics = "client")
    public void clientListener(String payload) throws JsonProcessingException {
        infoService.upsertOrganization(payload);
    }
    @KafkaListener(topics = "position")
    public void positionListener(String payload) throws JsonProcessingException {
        infoService.upsertPosition(payload);
    }
    @KafkaListener(topics = "hr_domain")
    public void adminAccessListener(String payload) {
//        accessLevelService.storeUsersWithAdminAccessLevel(payload);
    }
}
