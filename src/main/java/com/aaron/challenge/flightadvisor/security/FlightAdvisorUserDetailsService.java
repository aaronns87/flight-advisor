package com.aaron.challenge.flightadvisor.security;

import com.aaron.challenge.flightadvisor.users.Role;
import com.aaron.challenge.flightadvisor.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class FlightAdvisorUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userService.findByUserName(userName)
                .map(this::toUserDetailsUser)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User with user name " + userName + " not found.");
                });
    }

    private User toUserDetailsUser(com.aaron.challenge.flightadvisor.users.User appUser) {
        return new User(
                appUser.getUserName(),
                appUser.getPassword(),
                AuthorityUtils.createAuthorityList(
                        getRoleName(appUser.getRole())
                )
        );
    }

    private String getRoleName(Role role) {
        return "ROLE_" + role.name();
    }
}
