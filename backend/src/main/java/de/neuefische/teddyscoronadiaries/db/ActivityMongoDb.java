package de.neuefische.teddyscoronadiaries.db;

import de.neuefische.teddyscoronadiaries.model.activity.Activity;
import de.neuefische.teddyscoronadiaries.model.covid.IncidenceLevel;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ActivityMongoDb extends PagingAndSortingRepository<Activity, String> {

    List<Activity> findAll();

    List<Activity> findActivityByWeatherCategoryAndIncidenceLevel(String weatherCategory, IncidenceLevel incidenceLevel);
}
