package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.covid19api.model.ConfirmedCase;
import de.neuefische.teddyscoronadiaries.covid19api.service.Covid19ApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CovidServiceTest {

    private final Covid19ApiService covid19ApiService = mock(Covid19ApiService.class);
    private final CovidService covidService = new CovidService(covid19ApiService);

    @Test
    @DisplayName("Get 7 day incidence should return value for quarantine day")
    public void getSevenDayIncidenceShouldReturnIncidenceValue() {
        // Given
        int quarantineDay = 42;
        String from = "2020-04-20T00:00:00Z";
        String to = "2020-04-26T00:00:00Z";
        when(covid19ApiService.getConfirmedCases(from, to)).thenReturn(List.of(
                new ConfirmedCase("Confirmed", 210000, "2020-04-20T00:00:00Z"),
                new ConfirmedCase("Confirmed", 220000, "2020-04-21T00:00:00Z"),
                new ConfirmedCase("Confirmed", 230000, "2020-04-22T00:00:00Z"),
                new ConfirmedCase("Confirmed", 240000, "2020-04-23T00:00:00Z"),
                new ConfirmedCase("Confirmed", 250000, "2020-04-24T00:00:00Z"),
                new ConfirmedCase("Confirmed", 260000, "2020-04-25T00:00:00Z"),
                new ConfirmedCase("Confirmed", 270000, "2020-04-26T00:00:00Z")
        ));

        // When
        int result = covidService.getSevenDayIncidenceForQuarantineDay(quarantineDay);

        // Then
        assertThat(result, is(72));
    }

    @Test
    @DisplayName("Get 7 day incidence should return 403 not found when API unavailable")
    public void getSevenDayIncidenceValueShouldReturnError() {
        // Given
        int quarantineDay = 42;
        String from = "2020-04-20T00:00:00Z";
        String to = "2020-04-26T00:00:00Z";
        when(covid19ApiService.getConfirmedCases(from, to)).thenReturn(List.of());

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            covidService.getSevenDayIncidenceForQuarantineDay(quarantineDay);
        });
    }

    @Test
    @DisplayName("Get 7 day incidence should return 403 not found when returned list is incomplete")
    public void getSevenDayIncidenceShouldReturnErrorWhenListIsIncomplete() {
        // Given
        int quarantineDay = 42;
        String from = "2020-04-20T00:00:00Z";
        String to = "2020-04-26T00:00:00Z";
        when(covid19ApiService.getConfirmedCases(from, to)).thenReturn(List.of(
                new ConfirmedCase("Confirmed", 260000, "2020-04-25T00:00:00Z"),
                new ConfirmedCase("Confirmed", 270000, "2020-04-26T00:00:00Z")
        ));

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            covidService.getSevenDayIncidenceForQuarantineDay(quarantineDay);
        });
    }

}