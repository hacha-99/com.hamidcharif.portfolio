package com.hamidcharif.portfolio.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hamidcharif.portfolio.DTO.RegisterRequest;
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

    public String addUser(RegisterRequest userDTO) {
        if(repository.existsByUsername(userDTO.getUsername())){
            throw new UserAlreadyExistsException();
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        repository.save(user);
        return "User added successfully.";
    }
}