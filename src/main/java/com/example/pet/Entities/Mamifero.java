package com.example.pet.Entities;

import java.time.LocalDate;

public abstract class Mamifero extends Pet {
    protected String corPelagem;
    protected String raca;

    public Mamifero(String nome, LocalDate dataNascimento, String cor, String raca) {
        super(nome, dataNascimento);
        this.corPelagem = cor;
        this.raca = raca;
    }
}
