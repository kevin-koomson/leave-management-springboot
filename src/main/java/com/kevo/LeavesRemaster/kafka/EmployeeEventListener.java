package com.kevo.LeavesRemaster.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfoService;
import com.kevo.LeavesRemaster.modules.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeEventListener {
    public final EmployeeInfoService infoService;
    public final UserService userService;

    @KafkaListener(topics = "employee_job_info")
    void employeeJobInfoListener(String payload) throws JsonProcessingException {
        infoService.upsertEmployeeInfo(payload);
    }
    @KafkaListener(topics = "employee_added")
    void employeeAddedListener(String payload) throws JsonProcessingException {
        userService.upsertUser(payload);
    }
    @KafkaListener(topics = "employee_bio")
    void employeeBioListener(String payload) throws JsonProcessingException {
        userService.upsertUserBio(payload);
    }
    @KafkaListener(topics = "employee_contact")
    void employeeContactListener(String payload) throws JsonProcessingException {
        userService.upsertUserContact(payload);
    }
}
