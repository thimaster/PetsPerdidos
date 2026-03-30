package com.example.pet.DTO;

public class RegistroPerdaDTO {
    // Dados do Pet
    private String nome;
    private String dataNascimento; // Recebe como String do HTML
    private String tipoPet;        // "CAO", "AVE", "REPTIL", etc.
    private String cor;
    private String raca;
    private boolean rastejante;
    private String especie;
    private String tipoVoo;
    // Dados da Perda
    private String local;
    private String dataOcorrido;
    private String descricao;
    
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
	public String getTipoPet() {
		return tipoPet;
	}
	public void setTipoPet(String tipoPet) {
		this.tipoPet = tipoPet;
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
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
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
	public String getTipoVoo() {
		return tipoVoo;
	}
	public void setTipoVoo(String tipoVoo) {
		this.tipoVoo = tipoVoo;
	}
}
