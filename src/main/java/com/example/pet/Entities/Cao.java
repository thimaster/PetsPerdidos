package com.example.pet.Entities;

import java.time.LocalDate;

// --- CLASSES ESPECIALIZADAS ---
public class Cao extends Mamifero implements ISonoro {
    public Cao(String nome, LocalDate dataNascimento, String cor, String raca) {
        super(nome, dataNascimento, cor, raca);
    }

    @Override
    public String emitirSom() { return "Au Au!"; }

    public String fazerTruque() { return "deu a pata!"; }
}
