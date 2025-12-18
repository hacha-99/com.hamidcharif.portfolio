package com.hamidcharif.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hamidcharif.portfolio.model.Application;

public interface ApplicationRepository extends MongoRepository<Application, Long>{
    Application findByCompanyId(Long companyId);

    boolean existsByCompanyId(Long companyId);
}
