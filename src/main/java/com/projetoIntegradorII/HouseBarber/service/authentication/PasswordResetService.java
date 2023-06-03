package com.projetoIntegradorII.HouseBarber.service.authentication;

import org.hibernate.MappingException;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.TokenRecoveryDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.TokenRecovery;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;

public interface PasswordResetService {
    InfoDTO recoveryPassword(UserAuthDTO userDTO);

    InfoDTO changePassword(TokenRecoveryDTO tokenRecoveryDTO);

    InfoDTO validToken(String token);

    void createPasswordResetTokenForUser(UserAuth user, TokenRecovery token);

    void sendPasswordResetEmail(UserAuth user) throws MappingException;

    void changePassword(UserAuth user, TokenRecoveryDTO tokenRecoveryDTO);
}
