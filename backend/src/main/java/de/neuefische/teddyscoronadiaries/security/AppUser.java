package de.neuefische.teddyscoronadiaries.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "appusers")
public class AppUser {

    @Id
    private String id;
    private String name;
    private String emailSha256;
}
