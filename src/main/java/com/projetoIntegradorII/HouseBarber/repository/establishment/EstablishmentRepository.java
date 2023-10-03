package com.projetoIntegradorII.HouseBarber.repository.establishment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegradorII.HouseBarber.entity.establishment.Establishment;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long>{
    
}