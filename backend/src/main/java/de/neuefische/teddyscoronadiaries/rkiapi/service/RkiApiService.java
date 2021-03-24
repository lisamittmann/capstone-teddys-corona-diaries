package de.neuefische.teddyscoronadiaries.rkiapi.service;

import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiIncidenceValue;
import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiIncidenceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class RkiApiService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/Coronaf%C3%A4lle_in_den_Bundesl%C3%A4ndern/FeatureServer/0/query?";

    @Autowired
    public RkiApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RkiIncidenceWrapper getIncidenceValueForProvince(String province) {

        String url = baseUrl + "where=LAN_ew_GEN%20%3D%20'"
                + province.toUpperCase()
                + "'&outFields=LAN_ew_GEN,LAN_ew_BEZ,Fallzahl,Aktualisierung,faelle_100000_EW,cases7_bl_per_100k,cases7_bl,cases7_bl_per_100k_txt&outSR=4326&f=json&returnGeometry=false";

        String testUrl = "https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/Coronaf%C3%A4lle_in_den_Bundesl%C3%A4ndern/FeatureServer/0/query?where=LAN_ew_GEN%20%3D%20%27HAMBURG%27&outFields=LAN_ew_GEN,LAN_ew_BEZ,Fallzahl,Aktualisierung,faelle_100000_EW,cases7_bl_per_100k,cases7_bl,cases7_bl_per_100k_txt&outSR=4326&f=json&returnGeometry=false";

        ResponseEntity<RkiIncidenceWrapper> response = restTemplate.getForEntity(testUrl, RkiIncidenceWrapper.class);

        return response.getBody();
    }
}
