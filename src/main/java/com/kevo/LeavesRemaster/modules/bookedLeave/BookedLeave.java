package com.kevo.LeavesRemaster.modules.bookedLeave;

import com.kevo.LeavesRemaster.enums.Approval;
import com.kevo.LeavesRemaster.modules.leaveType.LeaveType;
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
import java.time.Year;
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
    private Approval hrApproval = Approval.PENDING;
    private Approval managerApproval = Approval.PENDING;
    private Integer year = Year.now().getValue();
    private Double daysOff;
    private Double carryOverUsed;
    @ManyToOne
    private LeaveType leaveType;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private User user;
    @ManyToOne
    private User manager;
    @ManyToOne
    private User approvedByHr;
    @ElementCollection
    @CollectionTable
    private Set<LeaveDay> leaveDays;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(
            name = "organization_id",
            referencedColumnName = "id"
    )
    private Organization organization;
    @OneToMany(cascade = CascadeType.ALL)
    private List<LeaveDocument> documents;
    @OneToMany(mappedBy = "bookedLeave", cascade = CascadeType.ALL)
    private List<BookedLeaveHistory> histories;
}
