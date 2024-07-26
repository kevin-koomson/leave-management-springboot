package com.kevo.LeavesRemaster.modules.user;

import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfoDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private BioData bioData;
    private EmployeeInfoDTO employeeInfo;
    private Contact contact;

    @Data
    @Builder
    public static class Contact {
        private Long id;
        private String work_email;
    }
    public User createUserFromDto(){
        return User.builder()
                .userId(bioData.getUser_id())
                .firstName(bioData.getFirst_name())
                .lastName(bioData.getLast_name())
                .fullName(bioData.getFull_name())
                .profileImage(bioData.getProfile_image())
                .deleted(bioData.getDeleted())
                .email(contact.work_email)
                .build();
    }
}
