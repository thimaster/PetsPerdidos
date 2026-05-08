package com.example.pet.Data;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entidade JPA mapeada para a tabela ESPECIE do banco SQLite
 */
@Entity
@Table(name = "ESPECIE")
public class Especie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "especie_id")
    private Integer especieId;
    
    @Column(name = "descricao", length = 50)
    private String descricao;
    
    @Column(name = "ind_rastejante")
    private Integer indRastejante;
    
    @Column(name = "ind_voador")
    private Integer indVoador;
    
    @OneToMany(mappedBy = "especie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PetData> pets;
    
    // ===== Construtores =====
    
    public Especie() {
    }
    
    public Especie(String descricao, Integer indRastejante, Integer indVoador) {
        this.descricao = descricao;
        this.indRastejante = indRastejante;
        this.indVoador = indVoador;
    }
    
    // ===== Getters e Setters =====
    
    public Integer getEspecieId() {
        return especieId;
    }
    
    public void setEspecieId(Integer especieId) {
        this.especieId = especieId;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Integer getIndRastejante() {
        return indRastejante;
    }
    
    public void setIndRastejante(Integer indRastejante) {
        this.indRastejante = indRastejante;
    }
    
    public Integer getIndVoador() {
        return indVoador;
    }
    
    public void setIndVoador(Integer indVoador) {
        this.indVoador = indVoador;
    }
    
    public List<PetData> getPets() {
        return pets;
    }
    
    public void setPets(List<PetData> pets) {
        this.pets = pets;
    }
    
    // ===== Método toString =====
    
    @Override
    public String toString() {
        return "Especie{" +
                "especieId=" + especieId +
                ", descricao='" + descricao + '\'' +
                ", indRastejante=" + indRastejante +
                ", indVoador=" + indVoador +
                '}';
    }
}
