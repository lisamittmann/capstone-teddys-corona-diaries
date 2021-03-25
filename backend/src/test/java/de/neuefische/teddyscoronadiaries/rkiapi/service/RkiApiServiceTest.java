package de.neuefische.teddyscoronadiaries.rkiapi.service;

import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiAttributes;
import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiIncidenceValue;
import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiIncidenceWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RkiApiServiceTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final RkiApiService rkiApiService = new RkiApiService(restTemplate);

    @Test
    @DisplayName("Get incidence Value for province should return RKI incidence value object")
    public void getIncidenceForProvinceShouldReturnIncidenceValue(){
        // Given
        URI url = URI.create("https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/Coronaf%C3%A4lle_in_den_Bundesl%C3%A4ndern/FeatureServer/0/query?where=LAN_ew_GEN%20%3D%20'HAMBURG'&outFields=LAN_ew_GEN%2CLAN_ew_BEZ%2CFallzahl%2CAktualisierung%2Cfaelle_100000_EW%2Ccases7_bl_per_100k%2Ccases7_bl%2Ccases7_bl_per_100k_txt&outSR=4326&f=json&returnGeometry=false");
        RkiIncidenceWrapper rkiIncidenceWrapper = new RkiIncidenceWrapper(List.of(
                new RkiAttributes(
                        new RkiIncidenceValue("Hamburg", 50000, 103.7)
                )
        ));

        when(restTemplate.getForEntity(url, RkiIncidenceWrapper.class)).thenReturn(ResponseEntity.ok(rkiIncidenceWrapper));

        // When
        Optional<RkiIncidenceValue> result = rkiApiService.getIncidenceValueForProvince("Hamburg");

        // Then
        assertThat(result.get(), is(new RkiIncidenceValue("Hamburg", 50000, 103.7)));

    }

    @Test
    @DisplayName("Get incidence returns empty optional when request cannot be processed")
    public void getIncidenceValueForProvinceReturnsEmptyOptional(){
        // Given
        URI url = URI.create("https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/Coronaf%C3%A4lle_in_den_Bundesl%C3%A4ndern/FeatureServer/0/query?where=LAN_ew_GEN%20%3D%20'HAMBURG'&outFields=LAN_ew_GEN%2CLAN_ew_BEZ%2CFallzahl%2CAktualisierung%2Cfaelle_100000_EW%2Ccases7_bl_per_100k%2Ccases7_bl%2Ccases7_bl_per_100k_txt&outSR=4326&f=json&returnGeometry=false");

        when(restTemplate.getForEntity(url, RkiIncidenceWrapper.class)).thenThrow(new RestClientException("Not found"));

        // When
        Optional<RkiIncidenceValue> result = rkiApiService.getIncidenceValueForProvince("Hamburg");

        // Then
        assertTrue(result.isEmpty());

    }
}