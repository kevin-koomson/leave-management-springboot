package com.kevo.LeavesRemaster.modules.position;

import com.kevo.LeavesRemaster.modules.leavePolicy.LeavePolicy;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Position {
    @Id
    private Long id;
    private String position_name;
    private String description;
    private Boolean deleted;
    @ManyToOne
    private LeavePolicy leavePolicy;
}
