package de.neuefische.teddyscoronadiaries.security;

import de.neuefische.teddyscoronadiaries.security.AppUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppUserMongoDb extends PagingAndSortingRepository<AppUser, String> {

}
