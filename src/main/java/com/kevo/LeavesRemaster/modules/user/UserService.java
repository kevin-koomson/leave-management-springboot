package com.kevo.LeavesRemaster.modules.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevo.LeavesRemaster.codegen.types.LeaveUser;

import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfo;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfoRepository;
import com.kevo.LeavesRemaster.modules.organization.Organization;
import com.kevo.LeavesRemaster.modules.organization.OrganizationRepository;
import com.kevo.LeavesRemaster.modules.position.Position;
import com.kevo.LeavesRemaster.modules.position.PositionRepository;
import com.kevo.LeavesRemaster.utilites.JsonProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JsonProcessingService jsonProcessingService;
    private final ObjectMapper objectMapper;
    private final EmployeeInfoRepository infoRepository;
    private final OrganizationRepository organizationRepository;
    private final PositionRepository positionRepository;

    public User getUserByUuid(UUID id) {
        return userRepository.findById(id).orElse(null);
    }
    public User getUserByUserId(Long userId) {
        return userRepository.findByUserId(userId);
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

    public List<EmployeeInfo> listEmployeeInfosByUserId(Long user_id) {
        return infoRepository.findAllByUser_UserId(user_id);
    }

    public List<User> listUsers() {
        return userRepository.findAllByDeleted(false);
    }

    public List<Organization> listOrganizations() {
        return organizationRepository.findAll();
    }

    public List<Position> listPositions() {return positionRepository.findAll();}

    public User upsertUserBio(String jsonPayload) throws JsonProcessingException {
        // get bio data dto from string
        BioData bioData = jsonProcessingService.processJsonFile(jsonPayload, BioData.class);
        // get user from repo with user id
        User user = userRepository.findByUserId(bioData.getUser_id());
        // if user doesn't exist, create new user from bio data
        user = Objects.isNull(user) ?
                bioData.createUserFromBio() : bioData.updateUserWithBio(user);
        return userRepository.save(user);
    }

    public User upsertUserContact(String payload) throws JsonProcessingException {

        Long userId = objectMapper.readTree(payload).get("data").get("user_id").asLong();
        String email = objectMapper.readTree(payload).get("data").get("work_email").asText();

        User user = getUserByUserId(userId);
        if(Objects.isNull(user)){
            return userRepository.save(
                    User.builder().userId(userId).email(email).build());
        }
        user.setEmail(email);
        return userRepository.save(user);
    }
}
