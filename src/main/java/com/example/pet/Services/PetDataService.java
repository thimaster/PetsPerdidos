package com.example.pet.Services;

import com.example.pet.Repositories.*;
import com.example.pet.Data.*;
import com.example.pet.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serviço de exemplo para demonstrar o uso dos repositórios JPA
 * 
 * Este serviço encapsula lógica de negócio relacionada a pets e registros de perda/achado
 * Deve ser injetado em Controllers ou em outros Services
 */
@Service
public class PetDataService {
    
    @Autowired
    private PetDataRepository petDataRepository;
    
    @Autowired
    private EspecieRepository especieRepository;
    
    @Autowired
    private RegistroPerdaDataRepository registroPerdaDataRepository;
    
    @Autowired
    private RegistroAchadoDataRepository registroAchadoDataRepository;
    
    // ===== Operações CRUD de Pet =====
    
    /**
     * Registra um novo pet no sistema
     */
    @Transactional
    public PetData registrarNovoPet(String nome, LocalDate dataNascimento, Integer especieId, 
                                    String raca, String cor, Integer porte, String detalhes) {
        
        Optional<Especie> especie = especieRepository.findById(especieId);
        if (especie.isEmpty()) {
            throw new IllegalArgumentException("Espécie com ID " + especieId + " não encontrada");
        }
        
        PetData novoPet = new PetData(nome, dataNascimento, especie.get(), raca, cor, porte, detalhes);
        return petDataRepository.save(novoPet);
    }
    
    /**
     * Busca todos os pets do sistema
     */
    public List<PetData> buscarTodosPets() {
        return petDataRepository.findAll();
    }
    
    /**
     * Busca um pet específico por ID
     */
    public Optional<PetData> buscarPetPorId(Integer petId) {
        return petDataRepository.findById(petId);
    }
    
    /**
     * Busca pets por nome
     */
    public List<PetData> buscarPetsPorNome(String nome) {
        return petDataRepository.findByNome(nome);
    }
    
    /**
     * Busca pets por raça
     */
    public List<PetData> buscarPetsPorRaca(String raca) {
        return petDataRepository.findByRaca(raca);
    }
    
    /**
     * Busca pets de uma espécie específica
     */
    public List<PetData> buscarPetsDeEspecie(Integer especieId) {
        Optional<Especie> especie = especieRepository.findById(especieId);
        return especie.map(petDataRepository::findByEspecie).orElse(List.of());
    }
    
    /**
     * Atualiza informações de um pet
     */
    @Transactional
    public PetData atualizarPet(Integer petId, String nome, String raca, String cor, 
                                Integer porte, String detalhes) {
        
        Optional<PetData> petOptional = petDataRepository.findById(petId);
        if (petOptional.isEmpty()) {
            throw new IllegalArgumentException("Pet com ID " + petId + " não encontrado");
        }
        
        PetData pet = petOptional.get();
        pet.setNome(nome);
        pet.setRaca(raca);
        pet.setCor(cor);
        pet.setPorte(porte);
        pet.setDetalhes(detalhes);
        
        return petDataRepository.save(pet);
    }
    
    /**
     * Remove um pet do sistema
     */
    @Transactional
    public void deletarPet(Integer petId) {
        if (!petDataRepository.existsById(petId)) {
            throw new IllegalArgumentException("Pet com ID " + petId + " não encontrado");
        }
        petDataRepository.deleteById(petId);
    }
    
    // ===== Operações CRUD de RegistroPerda =====
    
    /**
     * Registra uma perda de pet
     */
    @Transactional
    public RegistroPerdaData registrarPerda(RegistroPerdaDTO perdaDTO) {
        
        if (perdaDTO == null) {
            throw new IllegalArgumentException("Registro perda não encontrado");
        }
        
        validarCampos(perdaDTO);
        
        RegistroPerdaData perda = perdaDTO.toEntity();
        
        //carrega o objeto do banco, pois não deve ser criado objeto novo para espécie
        if (perdaDTO.getEspecieId() > 0) {
            Especie especieDoBanco = especieRepository.findById(perdaDTO.getEspecieId()).get();         
            perda.getPet().setEspecie(especieDoBanco);
        }
        
        petDataRepository.save(perda.getPet());
        
        return registroPerdaDataRepository.save(perda);
    }
    
    /**
     * Busca todas as perdas registradas
     */
    public List<RegistroPerdaDTO> buscarTodasAsPerdas() {
    	
        var perdas = registroPerdaDataRepository.findAll();

    	return ConverterListaEntityToDTO(perdas);
    	
    }
    
    /**
     * Busca todas as perdas registradas
     */
    public List<RegistroPerdaDTO> buscarPerdasPorTermos(String termos) {
    	      
    	var perdas = registroPerdaDataRepository.findByValoresBuscaContaining(normalizar(termos)); 

    	return ConverterListaEntityToDTO(perdas);
    	
    }  
    
    private List<RegistroPerdaDTO> ConverterListaEntityToDTO(List<RegistroPerdaData> listaPerdasData)
    {
        List<RegistroPerdaDTO> listaPerdasDTO = new ArrayList<RegistroPerdaDTO>();
        for (RegistroPerdaData registro : listaPerdasData) {
            RegistroPerdaDTO dto = RegistroPerdaDTO.fromEntity(registro);
            listaPerdasDTO.add(dto);
		}
        return listaPerdasDTO;
    }
    
    private String normalizar(String texto) {
        if (texto == null) return "";
        String nfd = java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD);
        return nfd.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                  .toLowerCase()
                  .trim();
    }   
    
    public RegistroPerdaDTO buscarPerdaPorId(int perdaId) {
	      
    	var perda = registroPerdaDataRepository.findById(perdaId);
    	if (perda.isEmpty())
    		return null;
    	
    	return RegistroPerdaDTO.fromEntity(perda.get());
    }  
    
    /**
     * Busca perdas de um pet específico
     */
    public List<RegistroPerdaData> buscarPerdasDoPet(Integer petId) {
        Optional<PetData> petOptional = petDataRepository.findById(petId);
        return petOptional.map(registroPerdaDataRepository::findByPet).orElse(List.of());
    }
    
    /**
     * Busca perdas por local
     */
    public List<RegistroPerdaData> buscarPerdasPorLocal(String localPerda) {
        return registroPerdaDataRepository.findByLocalPerda(localPerda);
    }
    
    /**
     * Busca perdas com múltiplos critérios
     */
    public List<RegistroPerdaData> buscarPerdasComFiltros(Integer petId, String localPerda,
                                                          LocalDate dataInicio, LocalDate dataFim) {
        return registroPerdaDataRepository.findByMultipleCriteria(petId, localPerda, dataInicio, dataFim);
    }
    
    // ===== Operações CRUD de RegistroAchado =====
    
    /**
     * Registra um achado relacionado a uma perda
     */
    @Transactional
    public RegistroAchadoData registrarAchado(Integer perdaId, LocalDate dataAchado,
                                             String localAchado, String latitude, String longitude,
                                             String detalhes, Integer statusOcorrencia) {
        
        Optional<RegistroPerdaData> perdaOptional = registroPerdaDataRepository.findById(perdaId);
        if (perdaOptional.isEmpty()) {
            throw new IllegalArgumentException("Perda com ID " + perdaId + " não encontrada");
        }
        
        RegistroAchadoData achado = new RegistroAchadoData(
            perdaOptional.get(), dataAchado, localAchado, latitude, longitude, detalhes, statusOcorrencia
        );
        
        return registroAchadoDataRepository.save(achado);
    }
    
    /**
     * Busca todos os achados registrados
     */
    public List<RegistroAchadoData> buscarTodosOsAchados() {
        return registroAchadoDataRepository.findAll();
    }
    
    /**
     * Busca achados de uma perda específica
     */
    public List<RegistroAchadoData> buscarAchadosDaPerda(Integer perdaId) {
        Optional<RegistroPerdaData> perdaOptional = registroPerdaDataRepository.findById(perdaId);
        return perdaOptional.map(registroAchadoDataRepository::findByRegistroPerda).orElse(List.of());
    }
    
    /**
     * Busca achados por local
     */
    public List<RegistroAchadoData> buscarAchadosPorLocal(String localAchado) {
        return registroAchadoDataRepository.findByLocalAchado(localAchado);
    }
    
    /**
     * Busca achados com múltiplos critérios
     */
    public List<RegistroAchadoData> buscarAchadosComFiltros(Integer perdaId, String localAchado,
                                                           LocalDate dataInicio, LocalDate dataFim,
                                                           Integer statusOcorrencia) {
        return registroAchadoDataRepository.findByMultipleCriteria(perdaId, localAchado, 
                                                                   dataInicio, dataFim, statusOcorrencia);
    }
    
    /**
     * Marca um achado como concluído
     */
    @Transactional
    public RegistroAchadoData concluirAchado(Integer achadoId, LocalDate dataConclusao) {
        Optional<RegistroAchadoData> achadoOptional = registroAchadoDataRepository.findById(achadoId);
        if (achadoOptional.isEmpty()) {
            throw new IllegalArgumentException("Achado com ID " + achadoId + " não encontrado");
        }
        
        RegistroAchadoData achado = achadoOptional.get();
        achado.setDataConclusao(dataConclusao);
        
        return registroAchadoDataRepository.save(achado);
    }
    
    // ===== Operações de Espécie =====
    
    /**
     * Busca todas as espécies
     */
    public List<Especie> buscarTodasAsEspecies() {
        return especieRepository.findAll();
    }
    
    /**
     * Busca uma espécie por ID
     */
    public Optional<Especie> buscarEspeciePorId(Integer especieId) {
        return especieRepository.findById(especieId);
    }
    
    /**
     * Busca uma espécie por descrição
     */
    public Especie buscarEspeciePorDescricao(String descricao) {
        return especieRepository.findByDescricao(descricao);
    }
    
    /**
     * Busca todas as espécies rastejantes
     */
    public Iterable<Especie> buscarEspeciesRastejantes() {
        return especieRepository.findByIndRastejante(true);
    }
    
    /**
     * Busca todas as espécies voadoras
     */
    public Iterable<Especie> buscarEspeciesVoadoras() {
        return especieRepository.findByIndVoador(1);
    }
    
    // ===== Operações Analíticas =====
    
    /**
     * Obtém estatísticas de um pet
     */
    public PetDataStatistics obterEstatisticasPet(Integer petId) {
        Optional<PetData> petOptional = petDataRepository.findById(petId);
        if (petOptional.isEmpty()) {
            return null;
        }
        
        PetData pet = petOptional.get();
        List<RegistroPerdaData> perdas = registroPerdaDataRepository.findByPet(pet);
        
        long achadosTotal = perdas.stream()
            .flatMap(p -> p.getRegistrosAchados().stream())
            .count();
        
        return new PetDataStatistics(pet.getPetId(), pet.getNome(), perdas.size(), (int) achadosTotal);
    }
    
    // ===== Classes Internas para Estatísticas =====
    
    public static class PetDataStatistics {
        public final Integer petId;
        public final String nome;
        public final Integer totalPerdas;
        public final Integer totalAchados;
        
        public PetDataStatistics(Integer petId, String nome, Integer totalPerdas, Integer totalAchados) {
            this.petId = petId;
            this.nome = nome;
            this.totalPerdas = totalPerdas;
            this.totalAchados = totalAchados;
        }
    }
    
	private void validarCampos(RegistroPerdaDTO dto) {
	    if (dto.getNome() == null || dto.getNome().isBlank()) throw new IllegalArgumentException("Nome do pet é obrigatório.");
	    if (dto.getLocal() == null || dto.getLocal().isBlank()) throw new IllegalArgumentException("Local da perda é obrigatório.");
	    if (dto.getDataOcorrido() == null || dto.getDataOcorrido().isBlank()) throw new IllegalArgumentException("Data da ocorrência é obrigatória.");
	    // Adicione outras validações conforme sua necessidade
	}

}
