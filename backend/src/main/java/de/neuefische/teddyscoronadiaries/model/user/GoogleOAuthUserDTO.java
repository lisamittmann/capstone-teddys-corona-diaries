package de.neuefische.teddyscoronadiaries.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleOAuthUserDTO {

    @JsonProperty("profileObj")
    private GoogleProfileObject profile;
    @JsonProperty("tokenId")
    private String token;

}
