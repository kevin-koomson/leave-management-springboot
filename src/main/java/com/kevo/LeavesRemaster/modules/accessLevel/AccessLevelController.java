package com.kevo.LeavesRemaster.modules.accessLevel;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@DgsComponent
public class AccessLevelController {
    private final AccessLevelService accessLevelService;

    @DgsQuery
    public List<AccessLevel> listAccessLevels() {
        return accessLevelService.listAccessLevels();
    }
}
