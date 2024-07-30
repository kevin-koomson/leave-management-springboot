package com.kevo.LeavesRemaster.modules.leavePolicy;

import com.kevo.LeavesRemaster.entity.DayRange;
import com.kevo.LeavesRemaster.modules.leaveType.LeaveType;
import com.kevo.LeavesRemaster.modules.position.Position;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
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
    @NotBlank
    @Size(min = 1, max = 50)
    @Column(unique = true)
    private String name;
    @Size(max = 300)
    private String description;
    private Double startDelay = 0.0;
    @Column(nullable = false)
    private Double maxAccrual;
    @Column(nullable = false)
    private Double carryOver = 5.0;
    private DayRange delayRange = DayRange.DAY;
    private DayRange accrualRate = DayRange.DAY;
    private LocalDateTime carryOverExpiry = LocalDateTime.of(LocalDate.now().getYear(),3,3,0,0);
    private Boolean canNegate = true;
    private Boolean archived = false;
    @ManyToOne
    private LeaveType leaveType;
    @OneToMany
    private Set<Position> positions;
}
