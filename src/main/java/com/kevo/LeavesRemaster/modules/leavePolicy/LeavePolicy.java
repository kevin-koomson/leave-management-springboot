package com.kevo.LeavesRemaster.modules.leavePolicy;

import com.kevo.LeavesRemaster.enums.DayRange;
import com.kevo.LeavesRemaster.modules.leaveType.LeaveType;
import com.kevo.LeavesRemaster.modules.position.Position;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    @Column(unique = true, nullable = false)
    private String name;
    @Size(max = 300)
    private String description;
    private Double startDelay = 0.0;
    private Double maxAccrual;
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
