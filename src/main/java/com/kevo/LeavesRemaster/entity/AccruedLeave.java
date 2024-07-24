package com.kevo.LeavesRemaster.entity;

import com.kevo.LeavesRemaster.modules.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class AccruedLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Double accruedDays;
    private Double carryOverDays;
    private Double carryOverHistory;
    private Double annualDaysUsed;
    private Double hrAddition;
    private Double hrDeduction;
    private Double debt;
    private String activeAccrual;
    private String detailedAccrual;
    private Boolean onLeave;
    private Boolean isCarryOverExecuted;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @OneToOne(optional = false)
    private User user;
    @OneToOne(optional = false)
    private LeavePolicy leavePolicy;
    @OneToMany
    private List<HrComment> hrComments;
}
