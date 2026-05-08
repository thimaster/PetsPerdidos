package com.example.pet.Repositories;
import com.example.pet.Data.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório JPA para a entidade RegistroAchadoData
 */
@Repository
public interface RegistroAchadoDataRepository extends JpaRepository<RegistroAchadoData, Integer> {
    
    /**
     * Encontra todos os registros de achado para um registro de perda
     * @param registroPerda registro de perda
     * @return lista de registros de achado
     */
    List<RegistroAchadoData> findByRegistroPerda(RegistroPerdaData registroPerda);
    
    /**
     * Encontra registros de achado por local
     * @param localAchado local do achado
     * @return lista de registros
     */
    List<RegistroAchadoData> findByLocalAchado(String localAchado);
    
    /**
     * Encontra registros de achado por data
     * @param dataAchado data do achado
     * @return lista de registros
     */
    List<RegistroAchadoData> findByDataAchado(LocalDate dataAchado);
    
    /**
     * Encontra registros de achado por status
     * @param statusOcorrencia status da ocorrência
     * @return lista de registros com o status
     */
    List<RegistroAchadoData> findByStatusOcorrencia(Integer statusOcorrencia);
    
    /**
     * Encontra registros de achado por múltiplos critérios
     * @param perdaId id do registro de perda
     * @param localAchado local do achado
     * @param dataInicio data inicial
     * @param dataFim data final
     * @param statusOcorrencia status da ocorrência
     * @return lista de registros encontrados
     */
    @Query("SELECT r FROM RegistroAchadoData r WHERE " +
           "(:perdaId IS NULL OR r.registroPerda.perdaId = :perdaId) AND " +
           "(:localAchado IS NULL OR r.localAchado LIKE CONCAT('%', :localAchado, '%')) AND " +
           "(:dataInicio IS NULL OR r.dataAchado >= :dataInicio) AND " +
           "(:dataFim IS NULL OR r.dataAchado <= :dataFim) AND " +
           "(:statusOcorrencia IS NULL OR r.statusOcorrencia = :statusOcorrencia)")
    List<RegistroAchadoData> findByMultipleCriteria(@Param("perdaId") Integer perdaId,
                                                    @Param("localAchado") String localAchado,
                                                    @Param("dataInicio") LocalDate dataInicio,
                                                    @Param("dataFim") LocalDate dataFim,
                                                    @Param("statusOcorrencia") Integer statusOcorrencia);
}
