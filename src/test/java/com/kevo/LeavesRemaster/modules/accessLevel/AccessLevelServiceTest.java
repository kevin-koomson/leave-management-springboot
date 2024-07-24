package com.kevo.LeavesRemaster.modules.accessLevel;

import com.kevo.LeavesRemaster.modules.user.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class AccessLevelServiceTest {
    @Mock
    private AccessLevelRepository accessLevelRepository;
    private AccessLevelService accessLevelService;
    private AccessLevel accessLevel;
    private AccessLevelDTO accessLevelDTO;

    @BeforeEach
    void setUp() {
        accessLevelService = new AccessLevelService(accessLevelRepository);
        accessLevelDTO = AccessLevelDTO.builder()
                .accessLevelName("Test Access Level")
                .createdBy(UUID.randomUUID())
                .users(new UUID[]{UUID.randomUUID(), UUID.randomUUID()})
                .permissions("")
                .description("Description")
                .build();
        accessLevel = AccessLevel.builder()
                .name(accessLevelDTO.getAccessLevelName())
                .permissions(accessLevelDTO.getPermissions())
                .createdBy(new User())
                .users(new ArrayList<>())
                .build();
    }

    @Test
    public void whenNoAccessLevelReturnEmptyList(){
        Mockito.when(accessLevelRepository.findAll()).thenReturn(new ArrayList<>());
        List<AccessLevel> accessLevels = accessLevelService.listAccessLevels();
        assertTrue(accessLevels.isEmpty());
    }

    @Test
    void saveAccessLevelWithValidAccessLevelDTO(){
        when( accessLevelRepository.save( any( AccessLevel.class ) ) ).thenReturn( accessLevel );
        AccessLevel accessLevel = accessLevelService.save(accessLevelDTO);
//        assertEquals(creatorId, accessLevel.getCreatedBy().getId());
        assertEquals(accessLevelDTO.getAccessLevelName(), accessLevel.getName());
    }
}