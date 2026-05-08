
package com.example.pet.Transformation;

public enum EnumEspeciesConhecidas {
    CAO(3),
    AVE(2),
    REPTIL(5);

    private final int codigo;

    // O construtor de um enum é sempre privado
    EnumEspeciesConhecidas(int codigo) {
        this.codigo = codigo;
    }
    
    // Método para pegar o valor inteiro
    public int getCodigo() {
        return codigo;
    }
}