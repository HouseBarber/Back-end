package com.projetoIntegradorII.HouseBarber.repository.establishment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegradorII.HouseBarber.entity.establishment.Establishment;

import java.util.List;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long>{

    List<Establishment> findEstablishmentsByUserAuthId(Long userId);
}
