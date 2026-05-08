package com.example.pet.Repositories;
import com.example.pet.Data.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para a entidade Especie
 */
@Repository
public interface EspecieRepository extends JpaRepository<Especie, Integer> {
    
    /**
     * Encontra uma espécie pela descrição
     * @param descricao descrição da espécie
     * @return Especie encontrada ou null
     */
    Especie findByDescricao(String descricao);
    
    /**
     * Encontra todas as espécies rastejantes
     * @return lista de espécies rastejantes
     */
    Iterable<Especie> findByIndRastejante(Boolean indRastejante);
    
    /**
     * Encontra todas as espécies voadoras
     * @return lista de espécies voadoras
     */
    Iterable<Especie> findByIndVoador(Integer indVoador);
}
