package com.projetoIntegradorII.HouseBarber.security.Auth;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();
}
