package com.projetoIntegradorII.HouseBarber.controller.establishment;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.establishment.EstablishmentDTO;
import com.projetoIntegradorII.HouseBarber.service.establishment.EstablishmentService;

import java.util.List;

@RestController
@RequestMapping("/v1/establishment")
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

    @PutMapping(value = "updateEstablishment")
    @ApiOperation(value = "Atualizar Estabelecimento", notes = "" +
            "Recebe dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<EstablishmentDTO>> updateEstablishment(@RequestBody EstablishmentDTO establishmentDTO) {
        InfoDTO<EstablishmentDTO> infoDTO = establishmentService.updateEstablishment(establishmentDTO);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

    @GetMapping(value = "/user/{userId}")
    @ApiOperation(value = "Criar Estabelecimento", notes = "" +
            "Recebe dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<Page<EstablishmentDTO>>> listEstabelishment(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
        InfoDTO<Page<EstablishmentDTO>> infoDTO = establishmentService.listEstablishment(userId, pageable);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

    @GetMapping(value = "/{establishmentId}")
    @ApiOperation(value = "Criar Estabelecimento", notes = "" +
            "Recebe dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<EstablishmentDTO>> findEstablishmentById(@PathVariable Long establishmentId) {
        InfoDTO<EstablishmentDTO> infoDTO = establishmentService.findEstablishmentById(establishmentId);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

}
