package com.example.pet.DTO;

import com.example.pet.Data.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistroPerdaDTO {
    // Dados do Pet
    private String nome;
    private String dataNascimento; // Recebe como String do HTML
    private String cor;
    private String raca;
    private boolean rastejante;
    private boolean voador;
    private int especieId;
    private String detalhes;
    // Dados da Perda
    private int perdaId;
    private String local;
    private String dataOcorrido;
    private String descricao;
    private String urlImagem;
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getRaca() {
		return raca;
	}
	public void setRaca(String raca) {
		this.raca = raca;
	}
	public boolean isRastejante() {
		return rastejante;
	}
	public void setRastejante(boolean rastejante) {
		this.rastejante = rastejante;
	}

	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getDataOcorrido() {
		return dataOcorrido;
	}
	public void setDataOcorrido(String dataOcorrido) {
		this.dataOcorrido = dataOcorrido;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public boolean isVoador() {
		return voador;
	}
	public void setVoador(boolean voador) {
		this.voador = voador;
	}

	public String getDetalhes() {
		return detalhes;
	}
	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public String getUrlImagem() {
		return urlImagem;
	}
	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}
	
	public static RegistroPerdaDTO fromEntity(RegistroPerdaData entity) {
	    if (entity == null) return null;

	    RegistroPerdaDTO dto = new RegistroPerdaDTO();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    // Dados extraídos da parte "Pet" da Entity
	    if (entity.getPet() != null) {
	        dto.setNome(entity.getPet().getNome());
	        dto.setCor(entity.getPet().getCor());
	        dto.setRaca(entity.getPet().getRaca());
	        dto.setDetalhes(entity.getPet().getDetalhes()); // ou detalhes, conforme sua lógica
	        
	        // Conversão de data do Pet para String
	        if (entity.getPet().getDataNascimento() != null) {
	            dto.setDataNascimento(entity.getPet().getDataNascimento().format(formatter));
	        }

	        // Dados da Espécie
	        if (entity.getPet().getEspecie() != null) {
	            dto.setEspecieId(entity.getPet().getEspecie().getEspecieId());
	            dto.setVoador(entity.getPet().getEspecie().getIndVoador() == 1);
	            dto.setRastejante(entity.getPet().getEspecie().getIndRastejante() == 1);
	        }
	    }

	    // Dados da "Perda"
	    dto.setPerdaId(entity.getPerdaId());
	    dto.setLocal(entity.getLocalPerda());
	    dto.setDescricao(entity.getDetalhes());
	    
	    if (entity.getDataPerda() != null) {
	        dto.setDataOcorrido(entity.getDataPerda().format(formatter));
	    }

	    return dto;
	}
	
	public RegistroPerdaData toEntity() {

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    // 2. Criar e popular o novo Pet
	    PetData novoPet = new PetData();
	    novoPet.setNome(this.nome);
	    novoPet.setCor(this.cor);
	    novoPet.setRaca(this.raca);
	    novoPet.setDetalhes(this.detalhes); // detalhes do pet
	    
	    if (this.dataNascimento != null && !this.dataNascimento.isEmpty()) {
	        novoPet.setDataNascimento(LocalDate.parse(this.dataNascimento, formatter));
	    }

	    // 3. Popular a Espécie (dentro do Pet)
	    // faz a consulta usando o banco

	    // 4. Criar o Registro de Perda
	    RegistroPerdaData perda = new RegistroPerdaData();
	    perda.setPet(novoPet);
	    perda.setLocalPerda(this.local);
	    perda.setDetalhes(this.descricao); // descrição da perda
	    
	    if (this.dataOcorrido != null && !this.dataOcorrido.isEmpty()) {
	        perda.setDataPerda(LocalDate.parse(this.dataOcorrido, formatter));
	    }

	    return perda;
	}
	public int getEspecieId() {
		return especieId;
	}
	public void setEspecieId(int especieId) {
		this.especieId = especieId;
	}
	public int getPerdaId() {
		return perdaId;
	}
	public void setPerdaId(int perdaId) {
		this.perdaId = perdaId;
	}
}
