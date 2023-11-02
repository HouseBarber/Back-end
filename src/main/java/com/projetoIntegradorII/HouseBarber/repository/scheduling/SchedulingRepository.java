package com.projetoIntegradorII.HouseBarber.repository.scheduling;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegradorII.HouseBarber.entity.scheduling.Scheduling;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long>{
    
}
