package com.projetoIntegradorII.HouseBarber.controller.scheduling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.scheduling.SchedulingDTO;
import com.projetoIntegradorII.HouseBarber.service.scheduling.SchedulingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Api(tags = {"Scheduling Controller"})
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/v1/scheduling/")
public class SchedulingController {

    private final SchedulingService schedulingService;

    @PostMapping(value = "")
    @ApiOperation(value = "Criar agendamento", notes = "" + "Recebe dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<SchedulingDTO>> creatEstablishment(@RequestBody SchedulingDTO schedulingDTO) {
        InfoDTO<SchedulingDTO> infoDTO = schedulingService.created(schedulingDTO);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

    @PutMapping(value = "")
    @ApiOperation(value = "Atualizar agendamento", notes = "" + "Recebe dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<SchedulingDTO>> updateEstablishment(@RequestBody SchedulingDTO schedulingDTO) {
        InfoDTO<SchedulingDTO> infoDTO = schedulingService.updated(schedulingDTO);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }

    @GetMapping(value = "{id}")
    @ApiOperation(value = "Criar Estabelecimento", notes = "" + "Recebe dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InfoDTO.class),
            @ApiResponse(code = 401, message = "Não Autorizado"),
            @ApiResponse(code = 500, message = "Erro Interno de Servidor"),})
    public ResponseEntity<InfoDTO<SchedulingDTO>> findEstablishmentById(@PathVariable Long id) {
        InfoDTO<SchedulingDTO> infoDTO = schedulingService.findSchedulingByID(id);
        return ResponseEntity.status(infoDTO.getStatus()).body(infoDTO);
    }
}
