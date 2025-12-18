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
    private Long companyId;

    @NotBlank
    private String letter;

    public Application(Long companyId, String letter) {
        this.companyId = companyId;
        this.letter = letter;
    }

    public Application() {

    }

    public Long getCompanyId(){
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    } 

    public String getLetter(){
        return letter;
    }

    public void setLetter(String letter){
        this.letter = letter;
    }
}
