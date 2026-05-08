package com.example.pet.Repositories;
import com.example.pet.Data.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório JPA para a entidade RegistroPerdaData
 */
@Repository
public interface RegistroPerdaDataRepository extends JpaRepository<RegistroPerdaData, Integer> {
    
   
	// Busca registros onde o campo valoresBusca contém o termo informado
	List<RegistroPerdaData> findByValoresBuscaContaining(String termo);
	
    /**
     * Encontra todos os registros de perda de um pet
     * @param pet pet em questão
     * @return lista de registros de perda
     */
    List<RegistroPerdaData> findByPet(PetData pet);
    
    /**
     * Encontra registros de perda por local
     * @param localPerda local da perda
     * @return lista de registros
     */
    List<RegistroPerdaData> findByLocalPerda(String localPerda);
    
    /**
     * Encontra registros de perda por data
     * @param dataPerda data da perda
     * @return lista de registros
     */
    List<RegistroPerdaData> findByDataPerda(LocalDate dataPerda);
    
    /**
     * Encontra registros de perda por múltiplos critérios
     * @param petId id do pet
     * @param localPerda local da perda
     * @param dataInicio data inicial
     * @param dataFim data final
     * @return lista de registros encontrados
     */
    @Query("SELECT r FROM RegistroPerdaData r WHERE " +
           "(:petId IS NULL OR r.pet.petId = :petId) AND " +
           "(:localPerda IS NULL OR r.localPerda LIKE CONCAT('%', :localPerda, '%')) AND " +
           "(:dataInicio IS NULL OR r.dataPerda >= :dataInicio) AND " +
           "(:dataFim IS NULL OR r.dataPerda <= :dataFim)")
    List<RegistroPerdaData> findByMultipleCriteria(@Param("petId") Integer petId,
                                                   @Param("localPerda") String localPerda,
                                                   @Param("dataInicio") LocalDate dataInicio,
                                                   @Param("dataFim") LocalDate dataFim);
}
