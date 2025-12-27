package com.hamidcharif.portfolio.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hamidcharif.portfolio.DTO.ApplicationDTO;
import com.hamidcharif.portfolio.exception.ApplicationAlreadyExistsException;
import com.hamidcharif.portfolio.model.Application;
import com.hamidcharif.portfolio.repository.ApplicationRepository;

/**
 * ApplicationService.java provides functions to add and load applications from the corresponding repo.
 */

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public String addApplication(ApplicationDTO applicationDTO) {
        if (applicationRepository.existsByUsername(applicationDTO.username())) {
            throw new ApplicationAlreadyExistsException();
        }
        Application application = new Application();
        application.setUsername(applicationDTO.username());
        application.setCoverLetter(applicationDTO.coverLetter());

        applicationRepository.save(application);
        return "Application added successfully.";
    }

    public ApplicationDTO loadApplication(String username) {
        Application application = applicationRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        
        ApplicationDTO applicationDTO = new ApplicationDTO(application.getCoverLetter(), username);
        return applicationDTO; 
    }
}
