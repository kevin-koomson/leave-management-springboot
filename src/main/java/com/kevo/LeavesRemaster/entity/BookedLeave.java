package com.kevo.LeavesRemaster.entity;

import com.kevo.LeavesRemaster.modules.organization.Organization;
import com.kevo.LeavesRemaster.modules.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class BookedLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Approval hrApproval;
    private Approval managerApproval;
    private Integer year;
    private Double daysOff;
    private Double carryOverUsed;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ElementCollection
    @CollectionTable
    private Set<LeaveDay> leaveDays;
    @ManyToOne
    private User user;
    @ManyToOne
    private User manager;
    @ManyToOne
    private User approvedByHr;
    @OneToMany
    private List<Comment> comments;
    @OneToOne
    private Organization organization;
    @OneToMany
    private List<LeaveDocument> documents;

    @ElementCollection
    @CollectionTable(name = "bookedLeave_day", joinColumns = @JoinColumn(name = "bookedLeave_id"))
    @MapKeyColumn(name = "date")
    @Column(name = "duration")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<LocalDateTime, Double> days = new HashMap<>();
}
