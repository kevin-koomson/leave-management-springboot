package com.kevo.LeavesRemaster.entity;

import com.kevo.LeavesRemaster.modules.organization.Organization;
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
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String name;
    private String description;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
    private Boolean archived;
    @OneToOne
    private Organization organization;

}
