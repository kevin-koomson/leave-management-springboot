package com.kevo.LeavesRemaster.modules.position;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
}
