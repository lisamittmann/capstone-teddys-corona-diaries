package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.ActivityMongoDb;
import de.neuefische.teddyscoronadiaries.model.activity.Activity;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActivityServiceTest {

    private final ActivityMongoDb activityMongoDb = mock(ActivityMongoDb.class);
    private final ActivityService activityService = new ActivityService(activityMongoDb);

    @Test
    @DisplayName("Get activities returns list of activities")
    public void getActivitiesReturnsListOfActivities(){
        // Given
        String weatherCategory = "Rain";
        IncidenceLevel incidenceLevel = IncidenceLevel.ORANGE;

        when(activityMongoDb.findActivityByWeatherCategoryAndIncidenceLevel(weatherCategory, incidenceLevel))
                .thenReturn(getTestActivities());

        // When
        List<Activity> activities = activityService.getActivities(weatherCategory, incidenceLevel);

        // Then
        assertThat(activities, is(getTestActivities()));

    }

    @Test
    @DisplayName("Get activities should return empty list when no activities for criteria were found")
    public void getActivitiesShouldReturnEmptyList() {
        // Given
        String weatherCategory = "Rain";
        IncidenceLevel incidenceLevel = IncidenceLevel.ORANGE;

        when(activityMongoDb.findActivityByWeatherCategoryAndIncidenceLevel(weatherCategory, incidenceLevel))
                .thenReturn(List.of());

        // When
        List<Activity> result = activityService.getActivities(weatherCategory, incidenceLevel);

        // Then
        assertThat(result, is(emptyIterable()));
    }


    private List<Activity> getTestActivities() {
        return List.of(
                Activity.builder()
                        .name("backen")
                        .description("Einfach mal backen")
                        .weatherCategory(List.of("Rain", "Atmosphere"))
                        .incidenceLevel(List.of(IncidenceLevel.RED, IncidenceLevel.ORANGE))
                        .imageUrl("some-image-url")
                        .build(),
                Activity.builder()
                        .name("netflix")
                        .description("Netflix and chill")
                        .weatherCategory(List.of("Rain", "Atmosphere"))
                        .incidenceLevel(List.of(IncidenceLevel.RED, IncidenceLevel.ORANGE))
                        .imageUrl("some-image-url2")
                        .build()
        );
    }

}