package com.projetoIntegradorII.HouseBarber.entity.imagem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Data
@Entity
public class ImagemTeste {

    @Id
    @GeneratedValue(strategy = GenerationType .IDENTITY)
    private Long id;
    
    private String nome;
    
    @Lob
    private byte[] dados;
}


