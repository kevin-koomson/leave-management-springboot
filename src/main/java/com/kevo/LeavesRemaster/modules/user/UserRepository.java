package com.kevo.LeavesRemaster.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public User findUserByUserId(Long userId);
}
