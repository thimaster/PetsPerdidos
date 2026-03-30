package com.example.pet.Entities;

import java.time.LocalDate;

// --- CLASSES BASE ---
public abstract class Pet {
    protected String nome;
    protected LocalDate dataNascimento;

    public Pet(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() { return nome; }
    
	public String nomeFormatado() {
		return this.nome.trim().replace(" ", "-");
	}

}
