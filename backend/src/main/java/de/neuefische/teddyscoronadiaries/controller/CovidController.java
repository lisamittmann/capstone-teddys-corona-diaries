package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.model.covid.IncidenceDetails;
import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiIncidenceValue;
import de.neuefische.teddyscoronadiaries.rkiapi.model.RkiIncidenceWrapper;
import de.neuefische.teddyscoronadiaries.rkiapi.service.RkiApiService;
import de.neuefische.teddyscoronadiaries.service.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/covid")
public class CovidController {

    private final CovidService covidService;
    private final RkiApiService rkiApiService;

    @Autowired
    public CovidController(CovidService covidService, RkiApiService rkiApiService) {
        this.covidService = covidService;
        this.rkiApiService = rkiApiService;
    }

    @GetMapping("{quarantineDay}")
    public IncidenceDetails getCases(@PathVariable int quarantineDay){
        return covidService.getSevenDayIncidenceForQuarantineDay(quarantineDay);
    }

    @GetMapping("province/{province}")
    public RkiIncidenceWrapper getIncidenceValue(@PathVariable String province){
        return rkiApiService.getIncidenceValueForProvince(province);
    }
}
