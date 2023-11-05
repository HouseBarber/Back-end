package com.projetoIntegradorII.HouseBarber.service.establishment;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.establishment.EstablishmentDTO;
import com.projetoIntegradorII.HouseBarber.entity.establishment.Establishment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EstablishmentService {
     InfoDTO<EstablishmentDTO> creatEstablishment(EstablishmentDTO establishmentDTO);

     InfoDTO<Page<EstablishmentDTO>> listEstablishment(Long userId, Pageable pageable);

     InfoDTO<EstablishmentDTO> findEstablishmentById(Long establishmentId);

}
