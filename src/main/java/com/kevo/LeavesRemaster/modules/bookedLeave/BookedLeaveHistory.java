package com.kevo.LeavesRemaster.modules.bookedLeave;

import com.kevo.LeavesRemaster.enums.HistoryAction;
import com.kevo.LeavesRemaster.modules.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class BookedLeaveHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String message;
    private HistoryAction action;
    @ManyToOne
    private BookedLeave bookedLeave;
    @ManyToOne
    private User updatedBy;
    @CreationTimestamp
    private LocalDateTime timestamp;
}
