package de.neuefische.teddyscoronadiaries.rkiapi.service;

import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiIncidenceValue;
import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiIncidenceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Repository
public class RkiApiService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/Coronaf%C3%A4lle_in_den_Bundesl%C3%A4ndern/FeatureServer/0/query";

    @Autowired
    public RkiApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<RkiIncidenceValue> getIncidenceValueForProvince(String province) {

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(0, converter);

        String provinceParameter = "LAN_ew_GEN%20%3D%20'" + province.toUpperCase() + "'";

        try {

            StringBuilder builder = new StringBuilder(baseUrl);
            builder.append("?where=");
            builder.append(provinceParameter);
            builder.append("&outFields=");
            builder.append(URLEncoder.encode("LAN_ew_GEN,LAN_ew_BEZ,Fallzahl,Aktualisierung,faelle_100000_EW,cases7_bl_per_100k,cases7_bl,cases7_bl_per_100k_txt", StandardCharsets.UTF_8.toString()));
            builder.append("&outSR=4326&f=json&returnGeometry=false");

            URI uri = URI.create(builder.toString());

            ResponseEntity<RkiIncidenceWrapper> response = restTemplate.getForEntity(uri, RkiIncidenceWrapper.class);

            return Optional.of(response.getBody().getFeatures().get(0).getAttributes());
        } catch(Exception e) {
            return Optional.empty();
        }
    }
}
