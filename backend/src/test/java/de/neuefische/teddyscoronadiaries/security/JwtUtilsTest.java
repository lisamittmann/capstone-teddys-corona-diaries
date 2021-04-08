package de.neuefische.teddyscoronadiaries.security;

import de.neuefische.teddyscoronadiaries.utils.TimeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtUtilsTest {

    private TimeUtils timeUtils = mock(TimeUtils.class);
    private JwtConfig jwtConfig = mock(JwtConfig.class);
    private JwtUtils jwtUtils = new JwtUtils(timeUtils, jwtConfig);

    @Test
    public void generateJwtToken(){
        //GIVEN
        Instant now = Instant.ofEpochSecond(Instant.now().getEpochSecond());
        when(timeUtils.now()).thenReturn(now);
        String secret = "awesome-secret";
        when(jwtConfig.getJwtSecret()).thenReturn(secret);

        //WHEN
        String token = jwtUtils.createToken("teddy", new HashMap<>(Map.of("data", "value")));

        //THEN
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        assertThat(claims.getSubject(), is("teddy"));
        assertThat(claims.getIssuedAt(), is(Date.from(now)));
        assertThat(claims.getExpiration(), is(Date.from(now.plus(Duration.ofHours(1)))));
        assertThat(claims.get("data"), is("value"));
    }

}