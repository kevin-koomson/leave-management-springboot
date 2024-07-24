package com.kevo.LeavesRemaster.modules.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevo.LeavesRemaster.codegen.types.LeaveUser;

import com.kevo.LeavesRemaster.utilites.JsonProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JsonProcessingService jsonProcessingService;
    private final ObjectMapper objectMapper;

    public User getUserByUuid(UUID id) {
        return userRepository.findById(id).orElse(null);
    }
    public User getUserByUserId(Long userId) {
        return userRepository.findUserByUserId(userId);
    }

    public LeaveUser upsertUser(String json) throws JsonProcessingException {
        UserDTO dto = jsonProcessingService.processJsonFile(json, UserDTO.class);
        return transformUserToLeaveUser(save(dto));
    }

    public User save(UserDTO dto){
        User user = getUserByUserId(dto.getEmployeeInfo().getUser_id());
        if(!Objects.isNull(user)){
            user.setFirstName(dto.getBioData().getFirst_name());
            user.setLastName(dto.getBioData().getLast_name());
            user.setFullName(dto.getBioData().getFull_name());
            user.setEmail(dto.getContact().getWork_email());
            user.setProfileImage(dto.getBioData().getProfile_image());
            user.setDeleted(dto.getBioData().getDeleted());
            return userRepository.save(user);
        }

        return userRepository.save(
                User.builder()
                        .userId(dto.getEmployeeInfo().getUser_id())
                        .firstName(dto.getBioData().getFirst_name())
                        .lastName(dto.getBioData().getLast_name())
                        .fullName(dto.getBioData().getFull_name())
                        .email(dto.getContact().getWork_email())
                        .profileImage(dto.getBioData().getProfile_image())
                        .deleted(dto.getBioData().getDeleted())
                        .build()
        );
    }

    public LeaveUser transformUserToLeaveUser(User user) throws JsonProcessingException {
        String userJson = objectMapper.writeValueAsString(user);
        return objectMapper.readValue(userJson, LeaveUser.class);
    }
}
