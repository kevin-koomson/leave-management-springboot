package com.kevo.LeavesRemaster.modules.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevo.LeavesRemaster.modules.employeeInfo.EmployeeInfoRepository;
import com.kevo.LeavesRemaster.utilites.JsonProcessingService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;
    private User user;
    @Mock
    private JsonProcessingService jsonProcessingService;
    private final ResourceLoader resourceLoader;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private EmployeeInfoRepository infoRepository;

    @BeforeEach
    void setUp() {
        userService = new UserService(
                userRepository,
                jsonProcessingService,
                objectMapper,
                infoRepository
        );
        jsonProcessingService = new JsonProcessingService( objectMapper);
        user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Sarah")
                .lastName("Owusu")
                .email("sarah.owusu@amalitech.com")
                .fullName("Sarah Owusu")
                .profileImage("https://arms-enterprise-assets.s3.eu-west-1.amazonaws.com/employee-profile-pics/1708450403638WhatsApp%20Image%202024-02-20%20at%2017.32.31_8c9ac751.jpg")
                .isEmployee(true)
                .deleted(false)
                .build();
    }
    @Test
    void getUserByIdWithValidUuidTest() {
        Mockito.when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.ofNullable(user));
        assertEquals(user, userService.getUserByUuid(user.getId()));
    }

}