package com.projetoIntegradorII.HouseBarber.service.roles;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.RolesDTO;

import java.util.List;

public interface RolesService {
    List<RolesDTO> getAllRoles();
}
