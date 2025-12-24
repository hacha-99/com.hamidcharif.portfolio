package com.hamidcharif.portfolio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hamidcharif.portfolio.model.User;
import com.hamidcharif.portfolio.repository.UserRepository;

@Component
public class InitialAdminCreator implements ApplicationRunner {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final String adminPass;

    public InitialAdminCreator(UserRepository repository, PasswordEncoder encoder, @Value("${admin_pass}") String adminPass){
        this.repository = repository;
        this.encoder = encoder;
        this.adminPass = adminPass;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (repository.count() == 0) {
            User admin = new User();
            admin.setName("owner");
            admin.setUsername("admin");
            admin.setPassword(encoder.encode(adminPass));
            admin.setRoles("ROLE_ADMIN");
            repository.save(admin);
        }
    }
}