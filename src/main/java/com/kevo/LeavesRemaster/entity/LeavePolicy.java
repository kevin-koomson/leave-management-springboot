package com.kevo.LeavesRemaster.entity;

import com.kevo.LeavesRemaster.modules.leaveType.LeaveType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class LeavePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private Double startDelay;
    private Double maxAccrual;
    private Double carryOver;
    private DayRange delayRange;
    private DayRange accrualRate;
    private LocalDateTime carryOverExpiry;
    private Boolean canNegate = true;
    private Boolean archived = false;
    @ManyToOne
    private LeaveType leaveType;
}
