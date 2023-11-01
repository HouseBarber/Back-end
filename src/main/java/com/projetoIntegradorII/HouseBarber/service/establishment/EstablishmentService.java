package com.projetoIntegradorII.HouseBarber.service.establishment;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.establishment.EstablishmentDTO;
import java.util.List;

public interface EstablishmentService {
     InfoDTO<EstablishmentDTO> creatEstablishment(EstablishmentDTO establishmentDTO);

     InfoDTO<List<EstablishmentDTO>> listEstablishment(Long userId);
}
