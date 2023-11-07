package com.projetoIntegradorII.HouseBarber.service.scheduling;

import com.projetoIntegradorII.HouseBarber.dto.scheduling.SchedulingDTO;

public interface SchedulingService {
    
    SchedulingDTO create(SchedulingDTO schedulingDTO);

    SchedulingDTO update(Long id, SchedulingDTO schedulingDTO);

    SchedulingDTO findById(Long id);

}
