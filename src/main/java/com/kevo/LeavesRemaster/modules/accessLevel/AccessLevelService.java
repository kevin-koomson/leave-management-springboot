package com.kevo.LeavesRemaster.modules.accessLevel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevo.LeavesRemaster.modules.user.User;
import com.kevo.LeavesRemaster.modules.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccessLevelService {
    private final AccessLevelRepository accessLevelRepository;
    private final ObjectMapper objectMapper;
    private final UserService userService;

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

    public void storeUsersWithAdminAccessLevel(String payload) throws JsonProcessingException {
        JsonNode headNode = objectMapper.readTree(payload).get("data").get("heads");
        if(headNode.isArray()){
            for(JsonNode head : headNode) {
                Long headId = head.asLong();
                User user = userService.getUserByUserId(headId);
                if(Objects.isNull(user)) continue;

            }
        }
    }
}
