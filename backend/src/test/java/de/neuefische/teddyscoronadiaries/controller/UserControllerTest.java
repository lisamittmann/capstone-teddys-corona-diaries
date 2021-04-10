package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.db.RecipeMongoDb;
import de.neuefische.teddyscoronadiaries.db.UserSavedRecipesMongoDb;
import de.neuefische.teddyscoronadiaries.googleoauth.model.VerifyTokenResponse;
import de.neuefische.teddyscoronadiaries.model.recipe.Recipe;
import de.neuefische.teddyscoronadiaries.model.recipe.RecipeCardDetails;
import de.neuefische.teddyscoronadiaries.model.user.GoogleOAuthUserDTO;
import de.neuefische.teddyscoronadiaries.model.user.GoogleProfileObject;
import de.neuefische.teddyscoronadiaries.model.user.UserSavedRecipes;
import de.neuefische.teddyscoronadiaries.security.AppUser;
import de.neuefische.teddyscoronadiaries.security.AppUserMongoDb;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"security.jwt.secret=supertestsecret", "google.auth.client-id=awesomeClientId"})
class UserControllerTest {

    @LocalServerPort
    private int serverpot;

    private String getUrl() {
        return "http://localhost:" + serverpot + "/auth/user";
    }

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserSavedRecipesMongoDb userSavedRecipesMongoDb;

    @Autowired
    private AppUserMongoDb appUserMongoDb;

    @Autowired
    private RecipeMongoDb recipeMongoDb;

    @BeforeEach
    public void setUp() {
        userSavedRecipesMongoDb.deleteAll();
        appUserMongoDb.deleteAll();
        recipeMongoDb.deleteAll();
    }

    @Test
    @DisplayName("Get recipe status should return recipe status")
    public void getRecipeStatusShouldReturnRecipeStatus(){
        // Given
        String recipeId = "awesomeId";
        userSavedRecipesMongoDb.save(new UserSavedRecipes("awesomeGoogleId", List.of(recipeId)));

        //WHEN
        String jwtToken = loginToApp();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = testRestTemplate.exchange(getUrl() + "/recipe/" + recipeId, HttpMethod.GET, entity, String.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is("saved"));

    }

    @Test
    @DisplayName("Get recipe status should return not-saved for new user")
    public void getRecipeStatusShouldReturnRecipeStatusForNewUser(){
        // Given
        String recipeId = "awesomeId";

        //WHEN
        String jwtToken = loginToApp();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = testRestTemplate.exchange(getUrl() + "/recipe/" + recipeId, HttpMethod.GET, entity, String.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is("not-saved"));

    }

    @Test
    @DisplayName("Get saved recipes should return list of saved recipe card details")
    public void getSavedRecipesShouldReturnListOfSavedRecipeCardDetails() {
        // Given
        userSavedRecipesMongoDb.save(new UserSavedRecipes("awesomeGoogleId", List.of("No1", "No2", "No3")));
        recipeMongoDb.save(getRecipe("No1"));
        recipeMongoDb.save(getRecipe("No3"));

        // When
        String jwtToken = loginToApp();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<RecipeCardDetails[]> response = testRestTemplate.exchange(getUrl() + "/recipes", HttpMethod.GET, entity, RecipeCardDetails[].class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new RecipeCardDetails[] {
                new RecipeCardDetails("No1", "awesomeNameNo1", "imageUrlNo1", 12),
                new RecipeCardDetails("No3", "awesomeNameNo3", "imageUrlNo3", 12)
        }));

    }

    private String loginToApp() {

        String verifyTokenUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=awesomeTokenId";

        when(restTemplate.getForEntity(verifyTokenUrl, VerifyTokenResponse.class)).thenReturn(
                ResponseEntity.ok(VerifyTokenResponse.builder()
                        .clientId("awesomeClientId")
                        .googleId("awesomeGoogleId")
                        .email("teddysAwesomeEmail")
                        .name("Teddy Teddyson")
                        .build())
        );

        appUserMongoDb.save(AppUser.builder().id("123").name("Teddy").emailSha256("teddysEmailSha").build());
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity("http://localhost:" + serverpot + "auth/login/google",
                GoogleOAuthUserDTO.builder()
                        .profile(GoogleProfileObject.builder()
                                .email("teddysAwesomeEmail")
                                .familyName("Teddyson")
                                .givenName("Teddy")
                                .googleId("awesomeGoogleId")
                                .imageUrl("awesomeImageUrl")
                                .name("Teddy Teddyson")
                                .build())
                        .token("awesomeTokenId")
                        .build(), String.class);
        return loginResponse.getBody();
    }

    private Recipe getRecipe(String id) {
        return Recipe.builder()
                .id(id)
                .name("awesomeName" + id)
                .imageUrl("imageUrl" + id)
                .diaryEntry("diaryEntry" + id)
                .intendedFor("intendedFor" + id)
                .quarantineDay(12)
                .build();

    }

}