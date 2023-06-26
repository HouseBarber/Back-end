package com.projetoIntegradorII.HouseBarber.service.authentication;


import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;

public interface AuthService {
    InfoDTO login(UserAuthDTO userDTO);

    InfoDTO register(UserAuthDTO userAuthDTO);

    InfoDTO<UserAuthDTO> update(Long id, UserAuthDTO userAuthDTO);

}

