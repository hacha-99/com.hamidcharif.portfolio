package com.hamidcharif.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hamidcharif.portfolio.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByUsername(String username);

    boolean existsByUsername(String username);
}
