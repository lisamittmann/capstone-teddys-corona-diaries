package de.neuefische.teddyscoronadiaries.utils;

import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class CurrentDateUtils {

    public String getCurrentDay(){
        return Instant.now()
                .toString()
                .split("T")[0];
    }

}
