package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.model.covid.IncidenceDetails;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceDetailsProvince;

import de.neuefische.teddyscoronadiaries.service.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("api/covid")
public class CovidController {

    private final CovidService covidService;

    @Autowired
    public CovidController(CovidService covidService) {
        this.covidService = covidService;
    }

    @GetMapping("{quarantineDay}")
    public IncidenceDetails getCases(@PathVariable int quarantineDay){
        return covidService.getSevenDayIncidenceForQuarantineDay(quarantineDay);
    }


    @GetMapping("provinces")
    public List<String> getProvinces() {
        return covidService.getProvinces();
    }

    @GetMapping("/province/{province}")
    public IncidenceDetailsProvince getIncidenceDetailsForProvince(@PathVariable String province){
        return covidService.getSevenDayIncidenceValueForProvince(province);
    }

}
