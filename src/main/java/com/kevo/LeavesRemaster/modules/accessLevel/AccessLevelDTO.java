package com.kevo.LeavesRemaster.modules.accessLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Data
@Builder
public class AccessLevelDTO {
    @NotBlank
    @Size(min = 1, max = 50)
    private String accessLevelName;
    private String description;
    @NotNull
    private String permissions;
    @NotNull
    private UUID createdBy;
    private UUID[] users;
}
