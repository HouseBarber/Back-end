package com.projetoIntegradorII.HouseBarber.service.establishment;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.establishment.EstablishmentDTO;

public interface EstablishmentService {
     InfoDTO<EstablishmentDTO> creatEstablishment(EstablishmentDTO establishmentDTO);
    
}
