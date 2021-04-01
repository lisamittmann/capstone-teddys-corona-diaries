package de.neuefische.teddyscoronadiaries.db;

import de.neuefische.teddyscoronadiaries.model.province.ProvinceData;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProvinceMongoDb extends PagingAndSortingRepository<ProvinceData, String> {

    List<ProvinceData> findAll();
}
