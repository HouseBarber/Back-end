package com.projetoIntegradorII.HouseBarber.repository.imagem;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegradorII.HouseBarber.entity.imagem.ImagemTeste;

public interface ImagemRepository extends JpaRepository <ImagemTeste, Long> {
}
