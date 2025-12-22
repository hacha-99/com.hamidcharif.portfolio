package com.hamidcharif.portfolio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document
public class Application {
    @Id
    private Long id;

    @NotNull
    private Long userId;

    @NotBlank
    private String letter;

    public Application(Long userId, String letter) {
        this.userId = userId;
        this.letter = letter;
    }

    public Application() {

    }

    public Long getUserId(){
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    } 

    public String getLetter(){
        return letter;
    }

    public void setLetter(String letter){
        this.letter = letter;
    }
}
