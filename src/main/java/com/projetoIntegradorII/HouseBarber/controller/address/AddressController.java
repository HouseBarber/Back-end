package com.projetoIntegradorII.HouseBarber.controller.address;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.RolesDTO;
import com.projetoIntegradorII.HouseBarber.service.address.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/v1/address")
@Api(tags = {"Address Controller"})
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final AddressService addressService;

    @GetMapping(value = "/{cep}")
    @ApiOperation(value = "Busca enderecos por cep em api externa", notes = "Encontra todos os roles sem filtragem alguma")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<List<RolesDTO>>> getAddressByCep(@PathVariable("cep") String cep) throws MalformedURLException {
        InfoDTO<List<RolesDTO>> infoDTO = addressService.getAddressByCep(cep);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }
}
