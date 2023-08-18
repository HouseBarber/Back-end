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
    @PutMapping(value = "/update/{id}")
    @ApiOperation(value = "Update user", notes = "" +
            "Returns update information for the user, along with the permissions attached to the user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error"),})
    public ResponseEntity<InfoDTO<UserAuthDTO>> update(@PathVariable Long id, @RequestBody UserAuthDTO user) {
        InfoDTO<UserAuthDTO> infoDTO = userService.update(id, user);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Busca todas as Roles", notes = "Encontra todos os roles sem filtragem alguma")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<UserAuthDTO> getUserById(@PathVariable Long id){
        UserAuthDTO userAuthDTO = userService.getUserById(id);
        return ResponseEntity.ok().body(userAuthDTO);
    }

}
