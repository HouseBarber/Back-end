package com.projetoIntegradorII.HouseBarber.controller.user;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@Api(tags = {"User Controller"})
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Adiciona informações do usuário", notes = "" +
            "Recebe informações do usuário e salva")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<UserAuthDTO>> enrichUser(@RequestBody UserAuthDTO userAuthDTO) {
        InfoDTO<UserAuthDTO> infoDTO = userService.enrichUser(userAuthDTO);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

}
