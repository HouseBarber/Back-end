package com.projetoIntegradorII.HouseBarber.dto.authentication;

import com.projetoIntegradorII.HouseBarber.security.Response.JwtResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginDTO {
    private JwtResponse jwt;
    private List<RolesDTO> roles;
}
