package de.neuefische.teddyscoronadiaries.model.province;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "provinces")
public class ProvinceData {

    @Id
    private String name;
    private String capital;
    private String capitalWoeid;
}
