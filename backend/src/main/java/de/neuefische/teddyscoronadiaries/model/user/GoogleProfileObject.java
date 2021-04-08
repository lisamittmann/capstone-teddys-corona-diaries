package de.neuefische.teddyscoronadiaries.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleProfileObject {

    private String email;
    private String familyName;
    private String givenName;
    private String googleId;
    private String imageUrl;
    private String name;

}
