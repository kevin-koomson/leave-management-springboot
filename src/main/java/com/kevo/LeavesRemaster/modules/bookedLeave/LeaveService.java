package com.kevo.LeavesRemaster.modules.bookedLeave;

import com.kevo.LeavesRemaster.enums.HistoryAction;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfo;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfoService;
import com.kevo.LeavesRemaster.modules.leavePolicy.LeavePolicyService;
import com.kevo.LeavesRemaster.modules.leaveType.LeaveType;
import com.kevo.LeavesRemaster.modules.leaveType.LeaveTypeService;
import com.kevo.LeavesRemaster.modules.position.Position;
import com.kevo.LeavesRemaster.modules.user.User;
import com.kevo.LeavesRemaster.modules.user.UserService;
import com.kevo.LeavesRemaster.utilites.EmailServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final LeaveTypeService typeService;
    private final EmployeeInfoService infoService;
    private final UserService userService;
    private final LeavePolicyService policyService;
    private final LeaveTypeService leaveTypeService;
    private final HistoryRepository historyRepository;
    private final EmailServiceImpl emailService;

    @Transactional
    public BookedLeave bookLeave(LeaveDTO data) {
        // verify user making request
        User user = userService.getUserByUserId(data.getUserId());
        boolean userIsHr = verifyUserPermission();
        BookedLeave leave = data.createBookLeave();
        // validate days
        validateLeaveDays(leave.getLeaveDays(), userIsHr);
        // get leave type on request: if (!userIsHr and !isAnnualDefault) unauthorised
        LeaveType leaveType = typeService.getLeaveTypeById(data.getLeaveTypeId());
        if(!userIsHr && !leaveType.getAnnualDefault())
            throw new SecurityException("User does not have sufficient access");
        // get can negate from user active policy
        // get active info
        // and then when I get can negate, get user's remaining accrued
        // if accrued <= 0 and cannot negate, reject request
        // else book leave
        EmployeeInfo activeInfo = infoService.getUserActiveInfo(user.getUserId());
        Position position = policyService.findPositionByPositionId(activeInfo.getPosition().getId());
        // run calculation
        // verify available balance if can negate is false
        if(position.getLeavePolicy().getCanNegate() || userIsHr) verifyUserHasEnoughBalance();
        leave.setLeaveType(leaveTypeService.getLeaveTypeById(data.getLeaveTypeId()));
        leave.setUser(user);
        leave.setManager(activeInfo.getManager());
//        System.out.println(data);
        leave.setOrganization(activeInfo.getOrganization());
        leave = leaveRepository.save(leave);
//        System.out.println(leave);
        historyRepository.save(
                createHistoryUpdate(data.getComment(),
                        HistoryAction.CREATE,
                        leave));

        // send email to manager
        emailService.sendMessage(
                activeInfo.getManager().getEmail(),
                "New Leave request from " + user.getFullName(),
                "New leave request from " + user.getFullName()
        );
        // return saved leave
        System.out.println("Leave booked");
        return leave;
        // re-run calculation asynchronously
    }

    private void verifyUserHasEnoughBalance() {
        /*
        * goal: verify if user has enough balance to book
        * params:
        * */
    }

    private boolean verifyUserPermission() {
        return true;
    }

    private void validateLeaveDays(Set<LeaveDay> leaveDays, boolean userIsHr) {
        // if given day is in the past, if hr is false, throw unauthorized error

        for(LeaveDay day : leaveDays) {
//            System.out.println(day.getDuration());
            if(!day.getDate().isBefore(LocalDateTime.now().toLocalDate().atStartOfDay()) && !userIsHr)
                throw new SecurityException("User does not have sufficient access to book past dates");
        }
    }
    private BookedLeaveHistory createHistoryUpdate(String message, HistoryAction action, BookedLeave leave){
        return BookedLeaveHistory.builder()
                .message(message)
                .action(action)
                .bookedLeave(leave)
                .build();
    }


    public List<BookedLeave> listLeavesByUserId(Long userId) {
        return leaveRepository.findAllByUser_userId(userId);
    }
}
