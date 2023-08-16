package com.projetoIntegradorII.HouseBarber.controller.establishment;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.establishment.EstablishmentDTO;
import com.projetoIntegradorII.HouseBarber.service.establishment.EstablishmentService;

@RestController
@RequestMapping("/v1/establish")
@Api(tags = {"Establishment Controller"})
@RequiredArgsConstructor
@Slf4j
public class EstablishmentController {

    private final EstablishmentService establishmentService;

    @PostMapping(value = "creatEstablishment")
    @ApiOperation(value = "Criar Estabelecimento", notes = "" +
            "Recebe dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<EstablishmentDTO>> creatEstablishment(@RequestBody EstablishmentDTO establishmentDTO) {
        InfoDTO<EstablishmentDTO> infoDTO = establishmentService.creatEstablishment(establishmentDTO);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }
    
}
