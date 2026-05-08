package com.example.pet.Data;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidade JPA mapeada para a tabela REGISTRO_ACHADO do banco SQLite
 */
@Entity
@Table(name = "REGISTRO_ACHADO")
public class RegistroAchadoData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achado_id")
    private Integer achadoId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "perda_id", nullable = false)
    private RegistroPerdaData registroPerda;
    
    @Column(name = "data_achado")
    private LocalDate dataAchado;
    
    @Column(name = "local_achado", length = 100)
    private String localAchado;
    
    @Column(name = "latitude", length = 50)
    private String latitude;
    
    @Column(name = "longitude", length = 50)
    private String longitude;
    
    @Column(name = "detalhes", length = 100)
    private String detalhes;
    
    @Column(name = "status_ocorrencia")
    private Integer statusOcorrencia;
    
    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;
    
    // ===== Construtores =====
    
    public RegistroAchadoData() {
    }
    
    public RegistroAchadoData(RegistroPerdaData registroPerda, LocalDate dataAchado, 
                             String localAchado, String latitude, String longitude, 
                             String detalhes, Integer statusOcorrencia) {
        this.registroPerda = registroPerda;
        this.dataAchado = dataAchado;
        this.localAchado = localAchado;
        this.latitude = latitude;
        this.longitude = longitude;
        this.detalhes = detalhes;
        this.statusOcorrencia = statusOcorrencia;
    }
    
    // ===== Getters e Setters =====
    
    public Integer getAchadoId() {
        return achadoId;
    }
    
    public void setAchadoId(Integer achadoId) {
        this.achadoId = achadoId;
    }
    
    public RegistroPerdaData getRegistroPerda() {
        return registroPerda;
    }
    
    public void setRegistroPerda(RegistroPerdaData registroPerda) {
        this.registroPerda = registroPerda;
    }
    
    public LocalDate getDataAchado() {
        return dataAchado;
    }
    
    public void setDataAchado(LocalDate dataAchado) {
        this.dataAchado = dataAchado;
    }
    
    public String getLocalAchado() {
        return localAchado;
    }
    
    public void setLocalAchado(String localAchado) {
        this.localAchado = localAchado;
    }
    
    public String getLatitude() {
        return latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getDetalhes() {
        return detalhes;
    }
    
    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
    
    public Integer getStatusOcorrencia() {
        return statusOcorrencia;
    }
    
    public void setStatusOcorrencia(Integer statusOcorrencia) {
        this.statusOcorrencia = statusOcorrencia;
    }
    
    public LocalDate getDataConclusao() {
        return dataConclusao;
    }
    
    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
    
    // ===== Método toString =====
    
    @Override
    public String toString() {
        return "RegistroAchadoData{" +
                "achadoId=" + achadoId +
                ", perdaId=" + (registroPerda != null ? registroPerda.getPerdaId() : null) +
                ", dataAchado=" + dataAchado +
                ", localAchado='" + localAchado + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", detalhes='" + detalhes + '\'' +
                ", statusOcorrencia=" + statusOcorrencia +
                ", dataConclusao=" + dataConclusao +
                '}';
    }
}
