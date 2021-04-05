package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.ActivityMongoDb;
import de.neuefische.teddyscoronadiaries.model.activity.Activity;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    private final ActivityMongoDb activityMongoDb;

    @Autowired
    public ActivityService(ActivityMongoDb activityMongoDb) {
        this.activityMongoDb = activityMongoDb;
    }

    public List<Activity> getActivities(String weatherCategory, IncidenceLevel incidenceLevel) {
        return activityMongoDb.findActivityByWeatherCategoryAndIncidenceLevel(weatherCategory, incidenceLevel);
    }
}
