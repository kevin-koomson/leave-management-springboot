package com.kevo.LeavesRemaster.modules.organization;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class ClientDTO {
    private Long id;
    private String client_name;
    private ClientAddress address;

    @Data
    public static class ClientAddress {
        private Long id;
        private String country;
    }
    public Organization createOrganizationFromDto(ClientDTO dto){
        return Objects.isNull(dto.address) ?
                Organization.builder()
                .id(id)
                .name(client_name)
                .build()
                : Organization.builder()
                .id(id)
                .name(client_name)
                .country(address.country)
                .build();
    }
}
