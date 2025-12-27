package com.hamidcharif.portfolio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

/**
 * Application.java is used to map the application from the mongodb.
 */

@Document
public class Application {
    @Id
    private String id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String coverLetter;

    public Application(String username, String coverLetter) {
        this.username = username;
        this.coverLetter = coverLetter;
    }

    public Application() {

    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    } 

    public String getCoverLetter(){
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter){
        this.coverLetter = coverLetter;
    }
}
