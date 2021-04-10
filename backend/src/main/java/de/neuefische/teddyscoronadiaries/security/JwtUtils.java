package de.neuefische.teddyscoronadiaries.security;

import de.neuefische.teddyscoronadiaries.utils.TimeUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtUtils {

    private final TimeUtils timeUtils;
    private final JwtConfig config;

    public JwtUtils(TimeUtils timeUtils, JwtConfig config) {
        this.timeUtils = timeUtils;
        this.config = config;
    }

    public String createToken(String subject, HashMap<String, Object> claims) {
        Instant now = timeUtils.now();
        return Jwts.builder()
                .addClaims(claims) // data
                .setSubject(subject) // username
                .setIssuedAt(Date.from(now)) // issued timestamp
                .setExpiration(Date.from(now.plus(Duration.ofHours(1)))) // valid for 1 hours
                .signWith(SignatureAlgorithm.HS256, config.getJwtSecret()) // sign token
                .compact();
    }
}
