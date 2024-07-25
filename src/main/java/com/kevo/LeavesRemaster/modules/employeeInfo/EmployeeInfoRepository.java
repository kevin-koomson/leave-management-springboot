package com.kevo.LeavesRemaster.modules.employeeInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Long> {
    public List<EmployeeInfo> findAllByUser_UserId(Long userId);
}
