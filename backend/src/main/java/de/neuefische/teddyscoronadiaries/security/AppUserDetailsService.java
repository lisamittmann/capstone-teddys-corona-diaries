package de.neuefische.teddyscoronadiaries.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserMongoDb userDb;

    @Autowired
    public AppUserDetailsService(AppUserMongoDb userDb) {
        this.userDb = userDb;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = this.userDb.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist: " + username));

        return User.builder()
                .username(appUser.getId())
                .authorities("user")
                .build();
    }
}
