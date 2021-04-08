package de.neuefische.teddyscoronadiaries.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleProfileObject {

    private String email;
    private String familyName;
    private String givenName;
    private String googleId;
    private String imageUrl;
    private String name;

}
