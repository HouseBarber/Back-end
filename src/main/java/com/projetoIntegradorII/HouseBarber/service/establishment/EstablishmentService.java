package com.projetoIntegradorII.HouseBarber.service.establishment;

import java.util.List;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.establishment.EstablishmentDTO;

public interface EstablishmentService {
     InfoDTO<EstablishmentDTO> createEstablishment(EstablishmentDTO establishmentDTO);
     InfoDTO<List<EstablishmentDTO>> findAllEstablishment(Long idUser);
}