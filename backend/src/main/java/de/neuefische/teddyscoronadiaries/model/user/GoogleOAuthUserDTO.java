package de.neuefische.teddyscoronadiaries.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleOAuthUserDTO {

    @JsonProperty("profileObj")
    private GoogleProfileObject profile;
    @JsonProperty("tokenId")
    private String token;

}
