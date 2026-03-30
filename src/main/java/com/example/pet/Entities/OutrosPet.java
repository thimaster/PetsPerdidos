package com.example.pet.Entities;

import java.time.LocalDate;

public class OutrosPet extends Pet {
    private String especie;

    public OutrosPet(String nome, LocalDate dataNascimento, String especie) {
        super(nome, dataNascimento);
        this.especie = especie;
    }
}

