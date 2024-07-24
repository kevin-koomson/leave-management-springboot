package com.kevo.LeavesRemaster.modules.accessLevel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccessLevelRepository extends JpaRepository<AccessLevel, UUID> {

}
