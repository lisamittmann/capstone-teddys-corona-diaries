package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.covid19api.model.ConfirmedCase;
import de.neuefische.teddyscoronadiaries.covid19api.service.Covid19ApiService;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceDetails;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class CovidService {

    private final Covid19ApiService covid19ApiService;
    private final Instant quarantineStart = Instant.ofEpochSecond(1584230400L);
    private final float inhabitantsGermany = 83100000;

    @Autowired
    public CovidService(Covid19ApiService covid19ApiService) {
        this.covid19ApiService = covid19ApiService;
    }

    public IncidenceDetails getSevenDayIncidenceForQuarantineDay(int quarantineDay){
        String to = quarantineStart.plus(quarantineDay, ChronoUnit.DAYS).toString();
        String from = quarantineStart.plus(quarantineDay-6, ChronoUnit.DAYS).toString();
        List<ConfirmedCase> confirmedCases = covid19ApiService.getConfirmedCases(from, to);

        Optional<ConfirmedCase> startValue = getConfirmedCase(confirmedCases, from);
        Optional<ConfirmedCase> endValue = getConfirmedCase(confirmedCases, to);

        if(endValue.isEmpty() || startValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not retrieve data from CovidAPI");
        }

        float incidenceValue = (endValue.get().getCases()-startValue.get().getCases())/inhabitantsGermany*100000;


        return new IncidenceDetails((int) incidenceValue, getIncidenceLevel((int) incidenceValue));
    }

    private Optional<ConfirmedCase> getConfirmedCase(List<ConfirmedCase> confirmedCases, String filterCriteria){
        return confirmedCases.stream()
                .filter(item -> item.getDate().equals(filterCriteria))
                .findAny();
    }

    private IncidenceLevel getIncidenceLevel(int incidenceValue) {
        if(incidenceValue <= 35) {
            return IncidenceLevel.GREEN;
        } else if (incidenceValue <= 50) {
            return IncidenceLevel.YELLOW;
        } else if(incidenceValue <= 100) {
            return IncidenceLevel.ORANGE;
        } else {
            return IncidenceLevel.RED;
        }
    }
}
