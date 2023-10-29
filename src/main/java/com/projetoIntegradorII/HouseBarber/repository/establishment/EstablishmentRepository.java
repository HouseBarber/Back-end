package com.projetoIntegradorII.HouseBarber.repository.establishment;

import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegradorII.HouseBarber.entity.establishment.Establishment;

import java.util.List;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long>{

    Page<Establishment> findEstablishmentsByUser(UserAuth user);
}
