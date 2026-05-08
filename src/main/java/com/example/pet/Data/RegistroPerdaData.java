package com.example.pet.Data;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Entidade JPA mapeada para a tabela REGISTRO_PERDA do banco SQLite
 */
@Entity
@Table(name = "REGISTRO_PERDA")
public class RegistroPerdaData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perda_id")
    private Integer perdaId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id", nullable = false)
    private PetData pet;
    
    @Column(name = "data_perda")
    private LocalDate dataPerda;
    
    @Column(name = "local_perda", length = 100)
    private String localPerda;
    
    @Column(name = "latitude", length = 50)
    private String latitude;
    
    @Column(name = "longitude", length = 50)
    private String longitude;
    
    @Column(name = "detalhes", length = 500)
    private String detalhes;
    
    @Column(name = "valores_busca", length = 2000)
    private String valoresBusca;
    
    @OneToMany(mappedBy = "registroPerda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistroAchadoData> registrosAchados;
    
    // ===== Construtores =====
    
    public RegistroPerdaData() {
    }
    
    public RegistroPerdaData(PetData pet, LocalDate dataPerda, String localPerda, 
                            String latitude, String longitude, String detalhes) {
        this.pet = pet;
        this.dataPerda = dataPerda;
        this.localPerda = localPerda;
        this.latitude = latitude;
        this.longitude = longitude;
        this.detalhes = detalhes;
    }
    
    // ===== Getters e Setters =====
    
    public Integer getPerdaId() {
        return perdaId;
    }
    
    public void setPerdaId(Integer perdaId) {
        this.perdaId = perdaId;
    }
    
    public PetData getPet() {
        return pet;
    }
    
    public void setPet(PetData pet) {
        this.pet = pet;
    }
    
    public LocalDate getDataPerda() {
        return dataPerda;
    }
    
    public void setDataPerda(LocalDate dataPerda) {
        this.dataPerda = dataPerda;
    }
    
    public String getLocalPerda() {
        return localPerda;
    }
    
    public void setLocalPerda(String localPerda) {
        this.localPerda = localPerda;
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
    
    public String getValoresBusca() {
        return valoresBusca;
    }
    
    private void setValoresBusca(String valoresBusca) {
        this.valoresBusca = valoresBusca;
    }
    
    public List<RegistroAchadoData> getRegistrosAchados() {
        return registrosAchados;
    }
    
    public void setRegistrosAchados(List<RegistroAchadoData> registrosAchados) {
        this.registrosAchados = registrosAchados;
    }
    
    // ===== Tratamento prévio à gravação =====
    
    // Método auxiliar para normalizar e concatenar os campos
    @PrePersist
    @PreUpdate
    private void gerarValoresBusca() {
        StringBuilder sb = new StringBuilder();

        if (pet != null) {
        	appendSeNaoVazio(sb, pet.getNome());
            if (pet.getEspecie() != null) {
            	appendSeNaoVazio(sb, pet.getEspecie().getDescricao());
            }
            appendSeNaoVazio(sb, pet.getRaca());
            appendSeNaoVazio(sb, pet.getCor());
        }
        appendSeNaoVazio(sb, this.localPerda);

        this.valoresBusca = normalizar(sb.toString());
    }

    private void appendSeNaoVazio(StringBuilder sb, String valor) {
        if (valor != null && !valor.trim().isEmpty()) {
            sb.append(valor).append(" ");
        }
    }
    
    private String normalizar(String texto) {
        if (texto == null) return "";
        
        // Remove acentos, coloca em minúsculo e limpa espaços extras
        String nfdNormalizedString = java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD);
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        
        return pattern.matcher(nfdNormalizedString)
                      .replaceAll("")
                      .toLowerCase()
                      .trim()
                      .replaceAll("\\s+", " ");
    }

    
    // ===== Método toString =====
    
    @Override
    public String toString() {
        return "RegistroPerdaData{" +
                "perdaId=" + perdaId +
                ", petId=" + (pet != null ? pet.getPetId() : null) +
                ", dataPerda=" + dataPerda +
                ", localPerda='" + localPerda + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", detalhes='" + detalhes + '\'' +
                '}';
    }
}
