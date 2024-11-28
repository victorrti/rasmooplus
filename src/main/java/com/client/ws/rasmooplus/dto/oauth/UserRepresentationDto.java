package com.client.ws.rasmooplus.dto.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRepresentationDto {
    private Boolean enable;
    private String username;
    private Boolean emailVerified;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> groups;
    private List<CredentialsRepresentationDto> credentials;

    @Data
    @Builder
    public static class CredentialsRepresentationDto{
        private String username ;
        private String password;
        private String value;
        private Boolean temporary;
    }
}
