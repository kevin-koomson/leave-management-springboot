package com.kevo.LeavesRemaster.modules.employeeInfo;

import com.kevo.LeavesRemaster.modules.organization.Organization;
import com.kevo.LeavesRemaster.modules.position.Position;
import com.kevo.LeavesRemaster.modules.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Persistent;

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
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId",
            foreignKey = @ForeignKey(name = "fk_employeeinfo_user", value = ConstraintMode.NO_CONSTRAINT)
    )
    private User user;
    @OneToOne
    private Position position;
    @ManyToOne
    @JoinColumn(
            name = "manager_id",
            referencedColumnName = "userId",
            foreignKey = @ForeignKey(foreignKeyDefinition = "fk_employeeinfo_manager", value = ConstraintMode.NO_CONSTRAINT))
    private User manager;
}
