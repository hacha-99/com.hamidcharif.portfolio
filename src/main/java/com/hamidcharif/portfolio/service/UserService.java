package com.hamidcharif.portfolio.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hamidcharif.portfolio.DTO.RegisterDTO;
import com.hamidcharif.portfolio.exception.UserAlreadyExistsException;
import com.hamidcharif.portfolio.model.User;
import com.hamidcharif.portfolio.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(
            UserRepository repository,
            PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public String addUser(RegisterDTO registerDTO) {
        if(repository.existsByUsername(registerDTO.username())){
            throw new UserAlreadyExistsException();
        }
        User user = new User();
        user.setName(registerDTO.name());
        user.setUsername(registerDTO.username());
        user.setPassword(encoder.encode(registerDTO.password()));
        repository.save(user);
        return "User added successfully.";
    }
}