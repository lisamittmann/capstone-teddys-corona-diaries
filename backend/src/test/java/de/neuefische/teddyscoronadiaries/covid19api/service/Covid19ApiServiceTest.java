package de.neuefische.teddyscoronadiaries.covid19api.service;

import de.neuefische.teddyscoronadiaries.covid19api.model.ConfirmedCase;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.time.Instant;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
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
        String mockUrl = baseUrl + "country/germany/status/confirmed/live?from=2020-04-20T00:00:00Z&to=2020-04-26T00:00:00Z";
        int quarantineDay = 42;
        ConfirmedCase[] caseNumbers = {
                new ConfirmedCase("Confirmed", 200, Instant.ofEpochSecond(1587254400L))
        };
        when(restTemplate.getForEntity(mockUrl, ConfirmedCase[].class)).thenReturn(ResponseEntity.ok(
                new ConfirmedCase[]{
                        new ConfirmedCase("Confirmed", 210, Instant.ofEpochSecond(1587340800L)),
                        new ConfirmedCase("Confirmed", 220, Instant.ofEpochSecond(1587427200L)),
                        new ConfirmedCase("Confirmed", 230, Instant.ofEpochSecond(1587513600L)),
                        new ConfirmedCase("Confirmed", 240, Instant.ofEpochSecond(1587600000L)),
                        new ConfirmedCase("Confirmed", 250, Instant.ofEpochSecond(1587686400L)),
                        new ConfirmedCase("Confirmed", 260, Instant.ofEpochSecond(1587772800L)),
                        new ConfirmedCase("Confirmed", 270, Instant.ofEpochSecond(1587859200L))
                }
        ));

        // When
        List<ConfirmedCase> result = covid19ApiService.getConfirmedCases(42);

        // Then
        assertThat(result, is(List.of(
                new ConfirmedCase("Confirmed", 210, Instant.ofEpochSecond(1587340800L)),
                new ConfirmedCase("Confirmed", 220, Instant.ofEpochSecond(1587427200L)),
                new ConfirmedCase("Confirmed", 230, Instant.ofEpochSecond(1587513600L)),
                new ConfirmedCase("Confirmed", 240, Instant.ofEpochSecond(1587600000L)),
                new ConfirmedCase("Confirmed", 250, Instant.ofEpochSecond(1587686400L)),
                new ConfirmedCase("Confirmed", 260, Instant.ofEpochSecond(1587772800L)),
                new ConfirmedCase("Confirmed", 270, Instant.ofEpochSecond(1587859200L))
        )));
    }

}