package com.hamidcharif.portfolio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document
public class Application {
    @Id
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String letter;

    public Application(String username, String letter) {
        this.username = username;
        this.letter = letter;
    }

    public Application() {

    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    } 

    public String getLetter(){
        return letter;
    }

    public void setLetter(String letter){
        this.letter = letter;
    }
}
