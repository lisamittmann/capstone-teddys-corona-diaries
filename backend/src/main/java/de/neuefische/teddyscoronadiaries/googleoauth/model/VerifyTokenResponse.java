package de.neuefische.teddyscoronadiaries.googleoauth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyTokenResponse {

    @JsonProperty("aud")
    private String clientId;
    @JsonProperty("sub")
    private String googleId;
    private String email;
    private String name;
}
