package com.example.pet.Transformation;

import com.example.pet.Entities.*;
import com.example.pet.DTO.*;
import java.time.LocalDate;

public class PetFactory {

    public static Pet criarPet(RegistroPerdaDTO dto) {
        LocalDate nascimento = LocalDate.parse(dto.getDataNascimento());
        String tipo = dto.getTipoPet().toUpperCase();

        return switch (tipo) {
            case "CAO" -> new Cao(
                dto.getNome(), 
                nascimento, 
                dto.getCor(), 
                dto.getRaca()
            );
            
            case "AVE" -> new Ave(
                dto.getNome(), 
                nascimento, 
                dto.getEspecie(), 
                dto.getTipoVoo() // Atributo vindo do DTO
            );
            
            case "REPTIL" -> new Reptil(
                dto.getNome(), 
                nascimento, 
                dto.getEspecie(), 
                dto.isRastejante()
            );
            
            default -> new OutrosPet(
                dto.getNome(), 
                nascimento, 
                dto.getEspecie()
            );
        };
    }
}
