package de.neuefische.teddyscoronadiaries.model.activity;

import de.neuefische.teddyscoronadiaries.model.covid.IncidenceLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "activities")
public class Activity {

    @Id
    private String name;
    private String description;
    private List<String> weatherCategory;
    private List<IncidenceLevel> incidenceLevel;
    private String imageUrl;

}
