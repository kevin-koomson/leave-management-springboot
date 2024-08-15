package com.kevo.LeavesRemaster.modules.employeeInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Long> {
    public List<EmployeeInfo> findAllByUser_UserId(Long userId);
    public EmployeeInfo findFirstByUser_userIdAndActiveIsTrue(Long userId);
}
