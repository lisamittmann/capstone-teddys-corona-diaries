package de.neuefische.teddyscoronadiaries.model.weather;

import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class WeatherCategoriesTest {

    @ParameterizedTest(name = "Get weather category should return correct value")
    @MethodSource("getWeatherCategoryValues")
    public void getWeatherCategoryShouldReturnCorrectValue(String apiCategory, String result){
        // When
        String weatherCategory = WeatherCategories.getWeatherCategory(apiCategory);

        // Then
        assertThat(weatherCategory, is(result));

    }

    private static Stream<Arguments> getWeatherCategoryValues() {
        return Stream.of(
                Arguments.of("SN", "Schnee"),
                Arguments.of("SL", "Schneeregen"),
                Arguments.of("H", "Hagel"),
                Arguments.of("T", "Gewitter"),
                Arguments.of("HR", "Starkregen"),
                Arguments.of("LR", "Leichter Regen"),
                Arguments.of("S", "Regen"),
                Arguments.of("HC", "Bewölkt"),
                Arguments.of("LC", "Leicht bewölkt"),
                Arguments.of("C", "Sonnig")
        );
    }

}