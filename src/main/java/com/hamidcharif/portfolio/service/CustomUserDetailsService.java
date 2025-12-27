package com.hamidcharif.portfolio.service;

import com.hamidcharif.portfolio.model.User;
import com.hamidcharif.portfolio.repository.UserRepository;
import com.hamidcharif.portfolio.security.CustomUserDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService.java overrides loadByUsername to properly access the user repo.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method to load user details by username
     * 
     * @param username The username that is searched for in the database.
     * @return A CustomUserDetails object to extract user information from.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from the database by username
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetails(user);
    }
}
