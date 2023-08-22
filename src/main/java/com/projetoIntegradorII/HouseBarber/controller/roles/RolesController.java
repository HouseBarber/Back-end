package com.projetoIntegradorII.HouseBarber.controller.roles;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.RolesDTO;
import com.projetoIntegradorII.HouseBarber.service.roles.RolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/roles")
@Api(tags = {"Roles Controller"})
@RequiredArgsConstructor
@Slf4j
public class RolesController {

    private final RolesService rolesService;
    @GetMapping(value = "")
    @ApiOperation(value = "Busca todas as Roles", notes = "Encontra todos os roles sem filtragem alguma")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<List<RolesDTO>> getRoles(){
        List<RolesDTO> roles = rolesService.getAllRoles();
        return ResponseEntity.ok().body(roles);
    }

}
