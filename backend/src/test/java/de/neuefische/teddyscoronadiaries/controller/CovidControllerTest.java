package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.covid19api.model.ConfirmedCase;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceDetails;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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

}