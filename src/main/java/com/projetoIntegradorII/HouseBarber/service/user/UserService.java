package com.projetoIntegradorII.HouseBarber.service.user;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;

public interface UserService {
    InfoDTO enrichUser(UserAuthDTO userAuthDTO);
}
