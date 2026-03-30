package com.example.pet.Entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// --- CLASSE DE ASSOCIAÇÃO ---
public class RegistroPerda {
    private Pet pet; // Associação: Registro "tem um" Pet
    private String local;
    private LocalDate dataOcorrido;
    private String descricao;

    public RegistroPerda(Pet pet, String local, LocalDate data, String descricao) {
        this.pet = pet;
        this.local = local;
        this.dataOcorrido = data;
        this.descricao = descricao;
    }

	public String localFormatado() {
		return local.trim().replace(" ", "-");
	}
	
	public String dataFormatada() {
		return this.dataOcorrido.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}
    
    public void GerarAviso() {
        System.out.println("\n===============================");
        System.out.println("     PROCURA-SE: " + pet.getNome().toUpperCase());
        System.out.println("===============================");
        System.out.println("Visto em: " + local + " no dia " + dataOcorrido);
        System.out.println("Detalhes: " + descricao);
        
        // Verificação de interface em tempo de execução
        if (pet instanceof ISonoro) {
            System.out.print("Ele emite um som parecido com isso: ");
            ((ISonoro) pet).emitirSom();
        }
        // Verificação da especialização em tempo de execução
        if (pet instanceof Ave) {
            Ave myAve = (Ave)pet;
            System.out.print(myAve.voar());
        }
    }

}
