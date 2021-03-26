package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.covid19api.model.ConfirmedCase;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceDetails;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceDetailsProvince;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceLevel;
import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiAttributes;
import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiIncidenceValue;
import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiIncidenceWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CovidControllerTest {

    @LocalServerPort
    private int serverPort;

    private String getUrl(){
        return "http://localhost:" + serverPort + "api/covid";
    }

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Get incidence value should return incidence value")
    public void getIncidenceValueShouldReturnIncidenceValue(){
        //Given
        int quarantineDay = 42;
        String from = "2020-04-20T00:00:00Z";
        String to = "2020-04-26T00:00:00Z";
        String mockUrl = "https://api.covid19api.com/country/germany/status/confirmed/live?from=" + from + "&to=" + to;
        when(restTemplate.getForEntity(mockUrl, ConfirmedCase[].class)).thenReturn(ResponseEntity.ok(
                new ConfirmedCase[]{
                        new ConfirmedCase("Confirmed", 210000, "2020-04-20T00:00:00Z"),
                        new ConfirmedCase("Confirmed", 220000, "2020-04-21T00:00:00Z"),
                        new ConfirmedCase("Confirmed", 230000, "2020-04-22T00:00:00Z"),
                        new ConfirmedCase("Confirmed", 240000, "2020-04-23T00:00:00Z"),
                        new ConfirmedCase("Confirmed", 250000, "2020-04-24T00:00:00Z"),
                        new ConfirmedCase("Confirmed", 260000, "2020-04-25T00:00:00Z"),
                        new ConfirmedCase("Confirmed", 270000, "2020-04-26T00:00:00Z")
                }
        ));

        // When
        ResponseEntity<IncidenceDetails> response = testRestTemplate.getForEntity(getUrl() + "/" + quarantineDay, IncidenceDetails.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new IncidenceDetails(72, IncidenceLevel.ORANGE)));
    }

    @Test
    @DisplayName("Get incidence value should throw error when API unavailable")
    public void getIncidenceValueShouldThrowError() {
        // Given
        int quarantineDay = 42;
        String from = "2020-04-20T00:00:00Z";
        String to = "2020-04-26T00:00:00Z";
        String mockUrl = "https://api.covid19api.com/country/germany/status/confirmed/live?from=" + from + "&to=" + to;
        when(restTemplate.getForEntity(mockUrl, ConfirmedCase[].class)).thenThrow(new RestClientException("No data available"));

        // When
        ResponseEntity<Void> response = testRestTemplate.getForEntity(getUrl() + "/" + quarantineDay, Void.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    @DisplayName("Get seven day incidence value for province should return incidence details for province")
    public void getSevenDayIncidenceValueForProvinceShouldReturnDetails(){
        //Given
        String province = "Hamburg";
        URI url = URI.create("https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/Coronaf%C3%A4lle_in_den_Bundesl%C3%A4ndern/FeatureServer/0/query?where=LAN_ew_GEN%20%3D%20'HAMBURG'&outFields=LAN_ew_GEN%2CLAN_ew_BEZ%2CFallzahl%2CAktualisierung%2Cfaelle_100000_EW%2Ccases7_bl_per_100k%2Ccases7_bl%2Ccases7_bl_per_100k_txt&outSR=4326&f=json&returnGeometry=false");
        RkiIncidenceWrapper rkiIncidenceWrapper = new RkiIncidenceWrapper(List.of(
                new RkiAttributes(
                        new RkiIncidenceValue("Hamburg", 50000, 103.7)
                )
        ));

        when(restTemplate.getForEntity(url, RkiIncidenceWrapper.class)).thenReturn(ResponseEntity.ok(rkiIncidenceWrapper));

        // When
        ResponseEntity<IncidenceDetailsProvince> response = testRestTemplate.getForEntity(getUrl() + "/province/" + province, IncidenceDetailsProvince.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new IncidenceDetailsProvince("Hamburg", 50000, 104, IncidenceLevel.RED)));
    }

    @Test
    @DisplayName("get seven day incidence value for province should return error when API not available")
    public void getSevenDayIncidenceValueForProvinceShouldThrowError() {
        // Given
        String province = "Hamburg";
        URI url = URI.create("https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/Coronaf%C3%A4lle_in_den_Bundesl%C3%A4ndern/FeatureServer/0/query?where=LAN_ew_GEN%20%3D%20'HAMBURG'&outFields=LAN_ew_GEN%2CLAN_ew_BEZ%2CFallzahl%2CAktualisierung%2Cfaelle_100000_EW%2Ccases7_bl_per_100k%2Ccases7_bl%2Ccases7_bl_per_100k_txt&outSR=4326&f=json&returnGeometry=false");

        when(restTemplate.getForEntity(url, RkiIncidenceWrapper.class)).thenThrow(new RestClientException("Not available"));

        // When
        ResponseEntity<Void> response = testRestTemplate.getForEntity(getUrl() + "/province/" + province, Void.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

}