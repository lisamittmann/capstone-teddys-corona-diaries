package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.model.activity.Activity;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceLevel;
import de.neuefische.teddyscoronadiaries.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/activity")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("{weatherCategory}/{incidenceLevel}")
    public List<Activity> getActivities(@PathVariable String weatherCategory, @PathVariable IncidenceLevel incidenceLevel) {
        return activityService.getActivities(weatherCategory, incidenceLevel);
    }
}
