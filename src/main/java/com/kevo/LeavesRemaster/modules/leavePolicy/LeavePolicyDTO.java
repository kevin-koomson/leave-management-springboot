package com.kevo.LeavesRemaster.modules.leavePolicy;

import com.kevo.LeavesRemaster.entity.DayRange;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class LeavePolicyDTO {
    private UUID id;
    @NotBlank
    @Max(value = 50)
    private String name;
    private String description;
    @Min(value = 0)
    @Digits(integer = 3, fraction = 2)
    private Double startDelay = 0.0;
    @Min(value = 0)
    @Digits(integer = 3, fraction = 2)
    @Max(value = 365)
    private Double maxAccrual;
    private Double carryOver = 5.0;
    private DayRange delayRange = DayRange.DAY;
    private DayRange accrualRate = DayRange.DAY;
    @NotBlank
    private String carryOverExpiry;
    private Boolean canNegate = true;
    @NotBlank
    private UUID leaveTypeId;
    @NotEmpty(message = "Please provide at least one position")
    private List<Long> positions;
    public LeavePolicy createLeavePolicy(){
        return LeavePolicy.builder()
                .name(name)
                .description(description)
                .startDelay(startDelay)
                .maxAccrual(maxAccrual)
                .carryOver(carryOver)
                .delayRange(delayRange)
                .accrualRate(accrualRate)
                .carryOverExpiry(LocalDateTime.parse(carryOverExpiry))
                .canNegate(canNegate)
                .build();
    }
}
