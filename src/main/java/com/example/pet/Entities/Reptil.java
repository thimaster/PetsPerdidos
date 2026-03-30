package com.example.pet.Entities;

import java.time.LocalDate;

public class Reptil extends Pet {
    private String especie;
    private boolean rastejante;

    public Reptil(String nome, LocalDate dataNascimento, String especie, boolean rastejante) {
        super(nome, dataNascimento);
        this.especie = especie;
        this.rastejante = rastejante;
    }  
}
