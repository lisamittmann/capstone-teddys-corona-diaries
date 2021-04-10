package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.db.ActivityMongoDb;
import de.neuefische.teddyscoronadiaries.model.activity.Activity;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceLevel;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"security.jwt.secret=supertestsecret"})
class ActivityControllerTest {

    @LocalServerPort
    private int serverPort;

    private String getUrl() {return "http://localhost:" + serverPort + "api/activity";}

    @Autowired
    private ActivityMongoDb activityMongoDb;

    @BeforeEach
    public void setUp() {activityMongoDb.deleteAll();}

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Get activities should return list of activities")
    public void getActivitiesShouldReturnListOfActivities(){
        // Given
        String weatherCategory = "Clear";
        IncidenceLevel incidenceLevel = IncidenceLevel.ORANGE;
        String url = getUrl() + "/" + weatherCategory + "/" + incidenceLevel;

        activityMongoDb.save(new Activity("backen", "Einfach mal backen", List.of("Rain", "Atmosphere"), List.of(IncidenceLevel.RED, IncidenceLevel.ORANGE), "some-image-url"));
        activityMongoDb.save(new Activity("netflix", "Netflix and chill", List.of("Rain", "Atmosphere"), List.of(IncidenceLevel.RED, IncidenceLevel.ORANGE), "some-image-url2"));
        activityMongoDb.save(new Activity("pokemon", "Pokemon fangen", List.of("Clear", "Clouds"), List.of(IncidenceLevel.RED, IncidenceLevel.ORANGE), "some-image-url3"));

        // When
        ResponseEntity<Activity[]> response = testRestTemplate.getForEntity(url, Activity[].class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new Activity[]{
                new Activity("pokemon", "Pokemon fangen", List.of("Clear", "Clouds"), List.of(IncidenceLevel.RED, IncidenceLevel.ORANGE), "some-image-url3")
        }));
    }

    @Test
    @DisplayName("Get activities returns empty list when categories cannot be found")
    public void getActivitiesShouldReturnEmptyList() {
        // Given
        String weatherCategory = "Clear";
        IncidenceLevel incidenceLevel = IncidenceLevel.ORANGE;
        String url = getUrl() + "/" + weatherCategory + "/" + incidenceLevel;

        // When
        ResponseEntity<Activity[]> response = testRestTemplate.getForEntity(url, Activity[].class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new Activity[] {}));

    }

}