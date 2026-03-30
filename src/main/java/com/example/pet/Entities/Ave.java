package com.example.pet.Entities;

import java.time.LocalDate;

public class Ave extends Pet implements ISonoro {
    private String especie;
    private String tipoVoo;

    public Ave(String nome, LocalDate dataNascimento, String especie, String tipoVoo) {
        super(nome, dataNascimento);
        this.especie = especie;
        this.tipoVoo = tipoVoo;
    }

    @Override
    public String emitirSom() { return "Piu Piu!"; }

    public String voar() {
        if (tipoVoo != "")  return "ele voa como:" + tipoVoo;
        else  return "não consegue voar.";
    }
}
