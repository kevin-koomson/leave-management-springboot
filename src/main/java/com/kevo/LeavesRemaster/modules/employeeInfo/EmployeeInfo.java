package com.kevo.LeavesRemaster.modules.employeeInfo;

import com.kevo.LeavesRemaster.modules.organization.Organization;
import com.kevo.LeavesRemaster.modules.position.Position;
import com.kevo.LeavesRemaster.modules.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class EmployeeInfo {
    @Id
    private Long id;
    private Boolean active;
    private LocalDateTime effective_date;
    private LocalDateTime end_date;
    @OneToOne
    private Organization organization;
    @ManyToOne
    private User user;
    @OneToOne
    private Position position;
    @OneToOne
    private User manager;
}
