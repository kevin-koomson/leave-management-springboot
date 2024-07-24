package com.kevo.LeavesRemaster.modules.user;

import com.kevo.LeavesRemaster.entity.AccruedLeave;
import com.kevo.LeavesRemaster.entity.BookedLeave;
import com.kevo.LeavesRemaster.modules.accessLevel.AccessLevel;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfo;
import com.kevo.LeavesRemaster.modules.organization.Organization;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private Long userId;
    private String firstName;
    private String lastName;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String profileImage;
    private Boolean isEmployee = true;
    private Boolean deleted;
    @ManyToOne
    @JoinColumn(name = "access_level_id", referencedColumnName = "id")
    private AccessLevel accessLevel;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Organization organization;
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<EmployeeInfo> employeeInfos;
    @OneToMany
    private Set<BookedLeave> bookedLeaves;
    @OneToOne
    private AccruedLeave accruedLeave;
}
