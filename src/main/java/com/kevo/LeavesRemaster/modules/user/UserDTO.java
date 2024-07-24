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
    public static class BioData {
        private String first_name;
        private String last_name;
        private String full_name;
        private String profile_image;
        private Long user_id;
        private Boolean deleted;
    }
    @Data
    @Builder
    public static class Contact {
        private Long id;
        private String work_email;
    }
    public User createUserFromDto(){
        return User.builder()
                .userId(bioData.user_id)
                .firstName(bioData.first_name)
                .lastName(bioData.last_name)
                .fullName(bioData.full_name)
                .profileImage(bioData.profile_image)
                .deleted(bioData.deleted)
                .email(contact.work_email)
                .build();
    }
}
