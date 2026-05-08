package com.example.pet.Data;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Entidade JPA mapeada para a tabela PET do banco SQLite
 */
@Entity
@Table(name = "PET")
public class PetData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Integer petId;
    
    @Column(name = "nome", length = 50)
    private String nome;
    
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "especie_id", nullable = false)
    private Especie especie;
    
    @Column(name = "raca", length = 50)
    private String raca;
    
    @Column(name = "cor", length = 50)
    private String cor;
    
    @Column(name = "porte")
    private Integer porte;
    
    @Column(name = "detalhes", length = 500)
    private String detalhes;
    
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistroPerdaData> registrosPerdas;
    
    // ===== Construtores =====
    
    public PetData() {
    }
    
    public PetData(String nome, LocalDate dataNascimento, Especie especie, 
                   String raca, String cor, Integer porte, String detalhes) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.especie = especie;
        this.raca = raca;
        this.cor = cor;
        this.porte = porte;
        this.detalhes = detalhes;
    }
    
    // ===== Getters e Setters =====
    
    public Integer getPetId() {
        return petId;
    }
    
    public void setPetId(Integer petId) {
        this.petId = petId;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public Especie getEspecie() {
        return especie;
    }
    
    public void setEspecie(Especie especie) {
        this.especie = especie;
    }
    
    public String getRaca() {
        return raca;
    }
    
    public void setRaca(String raca) {
        this.raca = raca;
    }
    
    public String getCor() {
        return cor;
    }
    
    public void setCor(String cor) {
        this.cor = cor;
    }
    
    public Integer getPorte() {
        return porte;
    }
    
    public void setPorte(Integer porte) {
        this.porte = porte;
    }
    
    public String getDetalhes() {
        return detalhes;
    }
    
    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
    
    public List<RegistroPerdaData> getRegistrosPerdas() {
        return registrosPerdas;
    }
    
    public void setRegistrosPerdas(List<RegistroPerdaData> registrosPerdas) {
        this.registrosPerdas = registrosPerdas;
    }
    
    // ===== Método toString =====
    
    @Override
    public String toString() {
        return "PetData{" +
                "petId=" + petId +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", especieId=" + (especie != null ? especie.getEspecieId() : null) +
                ", raca='" + raca + '\'' +
                ", cor='" + cor + '\'' +
                ", porte=" + porte +
                ", detalhes='" + detalhes + '\'' +
                '}';
    }
}
