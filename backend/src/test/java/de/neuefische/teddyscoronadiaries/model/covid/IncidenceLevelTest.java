package de.neuefische.teddyscoronadiaries.model.covid;

import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class IncidenceLevelTest {

    @ParameterizedTest(name = "Test incidence level for {0} expected {1}")
    @MethodSource("getEnumValuesAndExpectedValues")
    public void determineIncidenceLevelReturnsCorrectLabel(int incidenceValue, IncidenceLevel expectedResult){
        // When
        IncidenceLevel result = IncidenceLevel.determineIncidenceLevel(incidenceValue);

        // Then
        assertThat(result, is(expectedResult));

    }

    private static Stream<Arguments> getEnumValuesAndExpectedValues() {
        return Stream.of(
                Arguments.of(24, IncidenceLevel.GREEN),
                Arguments.of(35, IncidenceLevel.GREEN),
                Arguments.of(42, IncidenceLevel.YELLOW),
                Arguments.of(50, IncidenceLevel.YELLOW),
                Arguments.of(68, IncidenceLevel.ORANGE),
                Arguments.of(100, IncidenceLevel.ORANGE),
                Arguments.of(133, IncidenceLevel.RED)
        );
    }

}