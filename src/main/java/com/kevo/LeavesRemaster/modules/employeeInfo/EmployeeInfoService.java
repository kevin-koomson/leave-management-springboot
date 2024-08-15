package com.kevo.LeavesRemaster.modules.employeeInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevo.LeavesRemaster.codegen.types.LeaveOrganization;
import com.kevo.LeavesRemaster.codegen.types.LeavePosition;
import com.kevo.LeavesRemaster.modules.organization.ClientDTO;
import com.kevo.LeavesRemaster.modules.organization.Organization;
import com.kevo.LeavesRemaster.modules.organization.OrganizationRepository;
import com.kevo.LeavesRemaster.modules.position.Position;
import com.kevo.LeavesRemaster.modules.position.PositionDTO;
import com.kevo.LeavesRemaster.modules.position.PositionRepository;
import com.kevo.LeavesRemaster.modules.user.User;
import com.kevo.LeavesRemaster.modules.user.UserRepository;
import com.kevo.LeavesRemaster.modules.user.UserService;
import com.kevo.LeavesRemaster.utilites.JsonProcessingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeInfoService {
    private final UserService userService;
    private final JsonProcessingService jsonProcessingService;
    private final ObjectMapper objectMapper;
    private final PositionRepository positionRepository;
    private final EmployeeInfoRepository infoRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    public Organization saveOrganization (ClientDTO dto) {
        return organizationRepository.save(dto.createOrganizationFromDto(dto));
    }
    public Position savePosition (PositionDTO dto) {
        return positionRepository.save(objectMapper.convertValue(dto, Position.class));
    }
    public LeavePosition upsertPosition(String jsonPayload) throws JsonProcessingException {
        PositionDTO positionDTO = jsonProcessingService.processJsonFile(jsonPayload, PositionDTO.class);
        Position position = savePosition(positionDTO);
        return objectMapper.convertValue(position, LeavePosition.class);
    }
    public LeaveOrganization upsertOrganization(String jsonPayload) throws JsonProcessingException {
        ClientDTO dto = jsonProcessingService.processJsonFile(jsonPayload, ClientDTO.class);
        if(!dto.getIsOrganization() && dto.getArchive()) return null;
        Organization organization = saveOrganization(dto);
        return objectMapper.convertValue(organization, LeaveOrganization.class);
    }

    @Transactional
    public EmployeeInfo upsertEmployeeInfo(String jsonPayload) throws JsonProcessingException {
        // receive and process data
        EmployeeInfoDTO dto = jsonProcessingService.processJsonFile(jsonPayload, EmployeeInfoDTO.class);
        // affirm organization and position
        Organization organization = organizationRepository.findById(dto.getOrganization_id())
                .orElse(saveOrganization(dto.convertToClientDto()));
        Position position = positionRepository.findById(dto.getPosition_id())
                .orElseThrow();
        User manager = userRepository.findByUserIdAndDeletedIsFalse(dto.getManager_id());
        // get info object
        EmployeeInfo info = objectMapper.readValue(objectMapper.writeValueAsString(dto), EmployeeInfo.class);

        info.setOrganization(organization);
        info.setPosition(position);
        info.setManager(manager);

        // check if user exists
        User user = userService.getUserByUserId(dto.getUser_id());

        if(Objects.isNull(user)) {
            // user does not exist
            // create user from json string and dto
            user = createUserFromInfoDto(dto);
            user.setOrganization(organization);
            user = userRepository.save(user);
            user = userService.getUserByUserId(user.getUserId());
        }
        info.setUser(user);
        return infoRepository.save(info);
    }

    public User createUserFromInfoDto(EmployeeInfoDTO dto) {
        return User.builder()
                .userId(dto.getUser_id())
                .firstName(dto.getEmployee_bio().getFirst_name())
                .lastName(dto.getEmployee_bio().getLast_name())
                .fullName(dto.getEmployee_bio().getFull_name())
                .email(
                        Objects.requireNonNull(
                                Arrays.stream(dto.getEmployee_bio().getEmployee_contacts())
                                        .findFirst()
                                        .orElse(null)
                                )
                                .getWork_email())
                .profileImage(dto.getEmployee_bio().getProfile_image())
                .deleted(dto.getEmployee_bio().getDeleted())
                .build();
    }

    public EmployeeInfo getUserActiveInfo(Long userId) {
        return infoRepository.findFirstByUser_userIdAndActiveIsTrue(userId);
    }

    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id).orElseThrow(()->new NoSuchElementException("Organization does not exist"));
    }
    public Position getPositionById(Long positionId) {
        return positionRepository.findById(positionId).orElseThrow();
    }
}
