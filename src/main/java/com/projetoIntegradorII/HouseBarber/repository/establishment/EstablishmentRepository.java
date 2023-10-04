package com.projetoIntegradorII.HouseBarber.repository.establishment;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projetoIntegradorII.HouseBarber.entity.establishment.Establishment;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long>{
    
    Optional<List<Establishment>> findByUserAuthId(Long userId);
}