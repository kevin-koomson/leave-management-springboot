package com.kevo.LeavesRemaster.modules.organization;

import lombok.Builder;
import lombok.Data;

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
    public Organization createOrganizationFromDto(){
        return Organization.builder()
                .id(id)
                .name(client_name)
                .country(address.country)
                .build();
    }
}
