package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.covid19api.model.ConfirmedCase;
import de.neuefische.teddyscoronadiaries.covid19api.service.Covid19ApiService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CovidService {

    private final Covid19ApiService covid19ApiService;
    private final Instant quarantineStart = Instant.ofEpochSecond(1584230400L);
    private final float inhabitantsGermany = 83100000;

    @Autowired
    public CovidService(Covid19ApiService covid19ApiService) {
        this.covid19ApiService = covid19ApiService;
    }

    public int getSevenDayIncidenceForQuarantineDay(int quarantineDay){
        String to = quarantineStart.plus(quarantineDay, ChronoUnit.DAYS).toString();
        String from = quarantineStart.plus(quarantineDay-6, ChronoUnit.DAYS).toString();
        List<ConfirmedCase> confirmedCases = covid19ApiService.getConfirmedCases(from, to);

        float startValue = confirmedCases.stream()
                .filter(confirmedCase -> confirmedCase.getDate().equals(from))
                .findAny().get().getCases();
        float endValue = confirmedCases.stream()
                .filter(confirmedCase -> confirmedCase.getDate().equals(to))
                .findAny().get().getCases();

        float incidenceValue = (endValue-startValue)/inhabitantsGermany*100000;
        return (int) incidenceValue;
    }
}
