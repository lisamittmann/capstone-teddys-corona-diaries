package de.neuefische.teddyscoronadiaries.covid19api.service;

import de.neuefische.teddyscoronadiaries.covid19api.model.ConfirmedCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Covid19ApiServiceTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);

    private final String baseUrl = "https://api.covid19api.com/";
    private final Covid19ApiService covid19ApiService = new Covid19ApiService(restTemplate);

    @Test
    @DisplayName("Get confirmed cases for quarantine date period")
    public void getConfirmedCasesReturnsListOfConfirmedCases() {
        //Given
        String from = "2020-04-24T00:00:00Z";
        String to = "2020-04-26T00:00:00Z";
        String mockUrl = baseUrl + "country/germany/status/confirmed/live?from=" + from + "&to=" + to;
        when(restTemplate.getForEntity(mockUrl, ConfirmedCase[].class)).thenReturn(ResponseEntity.ok(
                new ConfirmedCase[]{
                        new ConfirmedCase("Confirmed", 250, "2020-04-24T00:00:00Z"),
                        new ConfirmedCase("Confirmed", 260, "2020-04-25T00:00:00Z"),
                        new ConfirmedCase("Confirmed", 270, "2020-04-26T00:00:00Z")
                }
        ));

        // When
        List<ConfirmedCase> result = covid19ApiService.getConfirmedCases(from, to);

        // Then
        assertThat(result, is(List.of(
                new ConfirmedCase("Confirmed", 250, "2020-04-24T00:00:00Z"),
                new ConfirmedCase("Confirmed", 260, "2020-04-25T00:00:00Z"),
                new ConfirmedCase("Confirmed", 270, "2020-04-26T00:00:00Z")
        )));
    }

    @Test
    @DisplayName("get confirmed cases should return empty list when API is unavailable")
    public void getConfirmedCasesShouldReturnNull(){
        //Given
        String from = "2020-04-24T00:00:00Z";
        String to = "2020-04-26T00:00:00Z";
        String url = baseUrl + "country/germany/status/confirmed/live?from=" + from + "&to=" + to;
        when(restTemplate.getForEntity(url, ConfirmedCase[].class))
                .thenThrow(new RestClientException("404 not found"));

        // When
        List<ConfirmedCase> result = covid19ApiService.getConfirmedCases(from, to);

        // Then
        assertThat(result, is(List.of()));
    }

}