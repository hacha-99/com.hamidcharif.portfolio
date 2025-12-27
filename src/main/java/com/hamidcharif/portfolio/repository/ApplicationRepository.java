package com.hamidcharif.portfolio.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hamidcharif.portfolio.model.Application;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String>{
    Optional<Application> findByUsername(String username);

    boolean existsByUsername(String username);
}
