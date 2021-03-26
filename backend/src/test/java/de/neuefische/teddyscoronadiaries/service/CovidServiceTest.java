package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.covid19api.model.ConfirmedCase;
import de.neuefische.teddyscoronadiaries.covid19api.service.Covid19ApiService;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceDetails;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceDetailsProvince;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceLevel;
import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiIncidenceValue;
import de.neuefische.teddyscoronadiaries.rkiapi.service.RkiApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CovidServiceTest {

    private final Covid19ApiService covid19ApiService = mock(Covid19ApiService.class);
    private final RkiApiService rkiApiService = mock(RkiApiService.class);
    private final CovidService covidService = new CovidService(covid19ApiService, rkiApiService);
    private static final int quarantineDay = 42;

    @Test
    @DisplayName("Get 7 day incidence should return value for quarantine day")
    public void getSevenDayIncidenceShouldReturnIncidenceValue() {
        // Given
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
        IncidenceDetails result = covidService.getSevenDayIncidenceForQuarantineDay(quarantineDay);

        // Then
        assertThat(result, is(new IncidenceDetails(72, IncidenceLevel.ORANGE)));
    }

    @Test
    @DisplayName("Get 7 day incidence should return 403 not found when API unavailable")
    public void getSevenDayIncidenceValueShouldReturnError() {
        // Given
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

    @Test
    @DisplayName("Get start and end value should return a map of confirmed cases")
    public void getStartAndEndValueShouldReturnHashMap() {
        //Given
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
        HashMap<String, Integer> result = covidService.getStartAndEndValue(quarantineDay);

        // Then
        assertThat(result, is(new HashMap<>() {{
            put("startValue", 210000);
            put("endValue", 270000);
        }}));

    }

    @Test
    @DisplayName("get start and end value should return error when API returns empty list")
    public void getStartAndEndValueReturnsErrorWhenApiReturnsEmptyList() {
        // Given
        // Given
        String from = "2020-04-20T00:00:00Z";
        String to = "2020-04-26T00:00:00Z";
        when(covid19ApiService.getConfirmedCases(from, to)).thenReturn(List.of());

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            covidService.getStartAndEndValue(quarantineDay);
        });
    }

    @Test
    @DisplayName("get start and end value should return error when API returns incomplete list")
    public void getStartAndEndValueReturnsErrorsWhenApiReturnsIncompleteList() {
        // Given
        String from = "2020-04-20T00:00:00Z";
        String to = "2020-04-26T00:00:00Z";
        when(covid19ApiService.getConfirmedCases(from, to)).thenReturn(List.of(
                new ConfirmedCase("Confirmed", 260000, "2020-04-25T00:00:00Z"),
                new ConfirmedCase("Confirmed", 270000, "2020-04-26T00:00:00Z")
        ));

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            covidService.getStartAndEndValue(quarantineDay);
        });
    }

    @ParameterizedTest(name = "Calculate incidence value from start value {0} and end value {1} should return {2}")
    @MethodSource("getStartAndEndValues")
    public void determineIncidenceValueShouldReturnIncidenceValue(int startValue, int endValue, int expected) {
        // When
        int result = covidService.determineIncidenceValue(startValue, endValue);

        // Then
        assertThat(result, is(expected));
    }

    private static Stream<Arguments> getStartAndEndValues() {
        return Stream.of(
                Arguments.of(10, 100000, 120),
                Arguments.of(230000, 500000, 325),
                Arguments.of(210000, 340000, 156)
        );
    }

    @Test
    @DisplayName("Get seven day incidence value for province should return incidence details")
    public void getSevenIncidenceValueShouldReturnIncidenceValue() {
        // Given
        String province = "Hamburg";
        when(rkiApiService.getIncidenceValueForProvince("Hamburg"))
                .thenReturn(Optional.of(new RkiIncidenceValue("Hamburg", 50000, 103.7)));

        // When
        IncidenceDetailsProvince result = covidService.getSevenDayIncidenceValueForProvince(province);

        // Then
        assertThat(result, is(new IncidenceDetailsProvince("Hamburg", 50000, 104, IncidenceLevel.RED)));
    }

    @Test
    @DisplayName("Get seven day incidence for province should throw error when endpoint unavailable")
    public void getSevenDayIncidenceForProvinceShouldThrowException() {
        // Given
        String province = "Hamburg";
        when(rkiApiService.getIncidenceValueForProvince(province)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            covidService.getSevenDayIncidenceValueForProvince(province);
        });
    }

    @Test
    @DisplayName("Get provinces should return list of provinces")
    public void getProvincesShouldReturnListOfProvinces() {
        // When
        List<String> provinces = covidService.getProvinces();

        // Then
        assertThat(provinces, is(List.of(
                "Baden-Württemberg",
                "Bayern",
                "Berlin",
                "Brandenburg",
                "Bremen",
                "Hamburg",
                "Hessen",
                "Mecklenburg-Vorpommern",
                "Niedersachsen",
                "Nordrhein-Westfalen",
                "Rheinland-Pfalz",
                "Saarland",
                "Sachsen",
                "Sachsen-Anhalt",
                "Schleswig-Holstein",
                "Thüringen"
        )));
    }

}