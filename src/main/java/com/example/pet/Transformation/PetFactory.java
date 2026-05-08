package com.example.pet.Transformation;

import com.example.pet.Entities.*;
import com.example.pet.DTO.*;
import java.time.LocalDate;

public class PetFactory {

    public static Pet criarPet(RegistroPerdaDTO dto) {
        LocalDate nascimento = LocalDate.parse(dto.getDataNascimento());
        String tipo = "";
        for (EnumEspeciesConhecidas s : EnumEspeciesConhecidas.values()) {
            if (s.getCodigo() == dto.getEspecieId()) {
            	tipo =  s.name();
            }
        }
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
                String.valueOf(dto.getEspecieId()), 
                dto.isVoador() ? "voa" : "não voa" // Atributo vindo do DTO
            );
            
            case "REPTIL" -> new Reptil(
                dto.getNome(), 
                nascimento, 
                String.valueOf(dto.getEspecieId()), 
                dto.isRastejante()
            );
            
            default -> new OutrosPet(
                dto.getNome(), 
                nascimento, 
                String.valueOf(dto.getEspecieId())
            );
        };
    }
}
