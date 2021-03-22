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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CovidService {

    private final Covid19ApiService covid19ApiService;
    //Start day March 15th 2020
    private final Instant quarantineStart = Instant.ofEpochSecond(1584230400L);
    private static final float INHABITANTS_GERMANY = 83100000;

    @Autowired
    public CovidService(Covid19ApiService covid19ApiService) {
        this.covid19ApiService = covid19ApiService;
    }

    public IncidenceDetails getSevenDayIncidenceForQuarantineDay(int quarantineDay){

        HashMap<String, Integer> startAndEndValue = getStartAndEndValue(quarantineDay);

        int incidenceValue = determineIncidenceValue(startAndEndValue.get("startValue"),
                startAndEndValue.get("endValue"));

        return new IncidenceDetails(incidenceValue, IncidenceLevel.determineIncidenceLevel(incidenceValue));
    }

    public HashMap<String, Integer> getStartAndEndValue(int quarantineDay) {
        String to = quarantineStart.plus(quarantineDay, ChronoUnit.DAYS).toString();
        String from = quarantineStart.plus(quarantineDay-6, ChronoUnit.DAYS).toString();
        List<ConfirmedCase> confirmedCases = covid19ApiService.getConfirmedCases(from, to);

        Optional<ConfirmedCase> startValue = getConfirmedCase(confirmedCases, from);
        Optional<ConfirmedCase> endValue = getConfirmedCase(confirmedCases, to);

        if(startValue.isEmpty() || endValue.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not retrieve data from CovidAPI");
        }

        return new HashMap<>() {{
            put("startValue", startValue.get().getCases());
            put("endValue", endValue.get().getCases());
        }};
    }

    private Optional<ConfirmedCase> getConfirmedCase(List<ConfirmedCase> confirmedCases, String filterCriteria){
        return confirmedCases.stream()
                .filter(item -> item.getDate().equals(filterCriteria))
                .findAny();
    }

    public int determineIncidenceValue(int startValue, int endValue) {
        float result = (endValue-startValue)/INHABITANTS_GERMANY*100000;
        return Math.round(result);
    }
}
