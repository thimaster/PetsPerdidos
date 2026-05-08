package com.example.pet.Repositories;
import com.example.pet.Data.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório JPA para a entidade PetData
 */
@Repository
public interface PetDataRepository extends JpaRepository<PetData, Integer> {
    
    /**
     * Encontra todos os pets por nome
     * @param nome nome do pet
     * @return lista de pets encontrados
     */
    List<PetData> findByNome(String nome);
    
    /**
     * Encontra todos os pets por espécie
     * @param especie espécie do pet
     * @return lista de pets da espécie
     */
    List<PetData> findByEspecie(Especie especie);
    
    /**
     * Encontra todos os pets por raça
     * @param raca raça do pet
     * @return lista de pets da raça
     */
    List<PetData> findByRaca(String raca);
    
    /**
     * Encontra todos os pets por cor
     * @param cor cor do pet
     * @return lista de pets da cor
     */
    List<PetData> findByCor(String cor);
    
    /**
     * Encontra todos os pets por porte
     * @param porte porte do pet
     * @return lista de pets do porte
     */
    List<PetData> findByPorte(Integer porte);
    
     /**
     * Encontra pets por múltiplos critérios
     * @param nome nome do pet
     * @param especieId id da espécie
     * @return lista de pets encontrados
     */
    @Query("SELECT p FROM PetData p WHERE (:nome IS NULL OR p.nome LIKE CONCAT('%', :nome, '%')) " +
           "AND (:especieId IS NULL OR p.especie.especieId = :especieId)")
    List<PetData> findByNomeAndEspecie(@Param("nome") String nome, 
                                       @Param("especieId") Integer especieId);
}
