package com.projetoIntegradorII.HouseBarber.controller.authentication;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.TokenRecoveryDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.service.authentication.AuthService;
import com.projetoIntegradorII.HouseBarber.service.authentication.PasswordResetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Api(tags = {"Auth Controller"})
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final PasswordResetService passwordResetService;

    private final AuthService authService;

    @PostMapping(value = "login")
    @ApiOperation(value = "Realizar o login", notes = "Retorna informações de acesso do Usuário, junto com as permissões vinculadas a ele.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
        @ApiResponse(code = 401, message = "Não Autorizado"),
        @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<UserAuthDTO>> login(@RequestBody UserAuthDTO user) {
        InfoDTO<UserAuthDTO> infoDTO = authService.login(user);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

        @PostMapping(value = "recoveryPassword")
    @ApiOperation(value = "Envia email para alterar senha", notes = "" +
            "Altera a senha pelo token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<UserAuthDTO>> recoveryPassword(@RequestBody UserAuthDTO user) {
        InfoDTO<UserAuthDTO> infoDTO = passwordResetService.recoveryPassword(user);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

    @PostMapping(value = "changePassword")
    @ApiOperation(value = "Alterar senha", notes = "" +
            "Recebe senha enviada e a altera")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<UserAuthDTO>> changePassword(@RequestBody TokenRecoveryDTO tokenRecoveryDTO) {
        InfoDTO<UserAuthDTO> infoDTO = passwordResetService.changePassword(tokenRecoveryDTO);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

    @GetMapping(value = "changePassword/{token}")
    @ApiOperation(value = "Valida o token", notes = "" +
            "Recebe o token e o valida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<TokenRecoveryDTO>> validToken(@PathVariable String token){
        InfoDTO<TokenRecoveryDTO> infoDTO = passwordResetService.validToken(token);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

    @PostMapping(value = "register")
    @ApiOperation(value = "Register user", notes = "" +
            "Returns registration information for the user, along with the permissions attached to the user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error"),})
    public ResponseEntity<InfoDTO<UserAuthDTO>> register(@RequestBody UserAuthDTO user) {
        InfoDTO infoDTO = authService.register(user);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

    @PutMapping(value = "/update/{id}")
    @ApiOperation(value = "Update user", notes = "" +
            "Returns update information for the user, along with the permissions attached to the user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error"),})
    public ResponseEntity<InfoDTO<UserAuthDTO>> update(@PathVariable Long id, @RequestBody UserAuthDTO user) {
        InfoDTO<UserAuthDTO> infoDTO = authService.update(id, user);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

}
