package com.kevo.LeavesRemaster.modules.position;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PositionDTO {
    private Long id;
    private String position_name;
    private String description;
    private Boolean deleted;
}
