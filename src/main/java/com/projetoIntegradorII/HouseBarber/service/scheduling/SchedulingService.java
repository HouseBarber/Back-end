package com.projetoIntegradorII.HouseBarber.service.scheduling;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.scheduling.SchedulingDTO;

public interface SchedulingService {

    InfoDTO<SchedulingDTO> created(SchedulingDTO schedulingDTO);

    InfoDTO<SchedulingDTO> findSchedulingByID(Long id);

    InfoDTO<SchedulingDTO> updated(SchedulingDTO schedulingDTO);

}
