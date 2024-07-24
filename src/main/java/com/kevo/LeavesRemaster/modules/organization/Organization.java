package com.kevo.LeavesRemaster.modules.organization;

import com.kevo.LeavesRemaster.modules.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Organization {
    @Id
    private Long id;
    private String name;
    private String country;
    @OneToMany
    private Set<User> approverSet;
}
