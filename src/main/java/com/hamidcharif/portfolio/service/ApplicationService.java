package com.hamidcharif.portfolio.service;

import com.hamidcharif.portfolio.DTO.ApplicationDTO;
import com.hamidcharif.portfolio.exception.ApplicationAlreadyExistsException;
import com.hamidcharif.portfolio.model.Application;
import com.hamidcharif.portfolio.repository.ApplicationRepository;

public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository){
        this.applicationRepository = applicationRepository;
    }

    public String addApplication(ApplicationDTO applicationDTO){
        if(applicationRepository.existsByUsername(applicationDTO.username())){
            throw new ApplicationAlreadyExistsException();
        }
        Application application = new Application();
        application.setUsername(applicationDTO.username());
        application.setCoverLetter(applicationDTO.coverLetter());

        applicationRepository.save(application);
        return "Application added successfully.";
    }
}
