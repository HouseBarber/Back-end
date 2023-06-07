package com.projetoIntegradorII.HouseBarber.service.authentication;


import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;

public interface AuthService {

    InfoDTO login(UserAuthDTO userDTO);

    InfoDTO register(UserAuthDTO userAuthDTO);

}
