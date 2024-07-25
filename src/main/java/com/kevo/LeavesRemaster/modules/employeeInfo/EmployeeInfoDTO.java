package com.kevo.LeavesRemaster.modules.employeeInfo;

import com.kevo.LeavesRemaster.modules.organization.ClientDTO;
import com.kevo.LeavesRemaster.modules.position.PositionDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmployeeInfoDTO {
    private Long id;
    private Long user_id;
    private Long position_id;
    private Long organization_id;
    private Long manager_id;
    private Boolean active;
    private LocalDateTime end_date;
    private LocalDateTime effective_date;
    private EmployeeOrganization employee_organization;
    private PositionDTO position;
    private EmployeeBio employee_bio;
    @Data
    public static class EmployeeOrganization {
        private Long id;
        private String name;
    }
    public ClientDTO convertToClientDto(){
        return ClientDTO.builder()
                .id(employee_organization.id)
                .client_name(employee_organization.getName())
                .build();
    }
    @Data
    public static class EmployeeBio {
        private String first_name;
        private String last_name;
        private String full_name;
        private String profile_image;
        private Boolean deleted;
        private Contact[] employee_contacts;
    }
    @Data
    public static class Contact {
        private String work_email;
    }
}
