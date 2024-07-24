package com.kevo.LeavesRemaster.modules.accessLevel;

import com.kevo.LeavesRemaster.modules.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessLevelService {
    private final AccessLevelRepository accessLevelRepository;

    public AccessLevel save(AccessLevelDTO accessLevelDTO) {
        // incoming data validated
        // create access level
        AccessLevel accessLevel = AccessLevel.builder()
                .name(accessLevelDTO.getAccessLevelName())
                .permissions(accessLevelDTO.getPermissions())
                .createdBy(new User())
                .users(new ArrayList<>())
                .build();
        // publish employee access level for assigned employees

        // publish access level to kafka

        return accessLevelRepository.save(accessLevel);
    }

    public List<AccessLevel> listAccessLevels() {
        return accessLevelRepository.findAll();
    }
}
