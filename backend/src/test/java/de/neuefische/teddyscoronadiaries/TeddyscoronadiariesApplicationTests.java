package de.neuefische.teddyscoronadiaries;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"security.jwt.secret=supertestsecret"})
class TeddyscoronadiariesApplicationTests {

    @Test
    void contextLoads() {
    }

}
